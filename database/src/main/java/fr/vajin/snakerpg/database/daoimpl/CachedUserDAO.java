package fr.vajin.snakerpg.database.daoimpl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class CachedUserDAO implements UserDAO {

    private LoadingCache<Integer, Optional<UserEntity>> strongCache;
    private Cache<Integer, UserEntity> weakReferencesCache;

    private UserDAO userDAO;
    private DAOFactory daoFactory;

    public CachedUserDAO(UserDAO userDAO, DAOFactory daoFactory, int cacheSize, int maxTime, TimeUnit maxTimeUnit) {
        this.userDAO = userDAO;
        this.daoFactory = daoFactory;

        this.weakReferencesCache = CacheBuilder.newBuilder().weakValues().build();

        this.strongCache =
                CacheBuilder.newBuilder()
                        .maximumSize(cacheSize)
                        .expireAfterWrite(maxTime, maxTimeUnit)
                        .recordStats()
                        .build(new CacheLoader<Integer, Optional<UserEntity>>() {
                            @Override
                            public Optional<UserEntity> load(Integer key) throws Exception {
                                UserEntity userEntity = weakReferencesCache.getIfPresent(key);
                                if (userEntity != null) {
                                    return Optional.of(userEntity);
                                } else {
                                    Optional<UserEntity> userEntityOptional = userDAO.getUser(key);
                                    userEntityOptional.ifPresent(userEntity1 -> weakReferencesCache.put(key, userEntity1));
                                    return userEntityOptional;
                                }
                            }
                        });
    }

    private Optional<UserEntity> findFirstInCaches(Predicate<UserEntity> predicate) {
        Optional<UserEntity> userEntityOptional =
                strongCache.asMap()
                        .entrySet()
                        .parallelStream()
                        .filter(entry -> entry.getValue().isPresent() && predicate.test(entry.getValue().get()))
                        .map(integerOptionalEntry -> integerOptionalEntry.getValue().get())
                        .findFirst();
        if (userEntityOptional.isPresent()) {
            return userEntityOptional;
        } else {
            userEntityOptional = weakReferencesCache.asMap().entrySet()
                    .parallelStream()
                    .filter(entry -> predicate.test(entry.getValue()))
                    .map(Map.Entry::getValue)
                    .findFirst();
            return userEntityOptional;
        }
    }

    @Override
    public int addUser(UserEntity userEntity) throws SQLException {
        int id = userDAO.addUser(userEntity);
        this.strongCache.put(id, Optional.of(userEntity));
        this.weakReferencesCache.put(id, userEntity);
        return id;
    }

    @Override
    public Optional<UserEntity> getUser(int id) {
        try {
            return strongCache.get(id);
        } catch (ExecutionException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> getUser(String accountName, String hash) {
        Optional<UserEntity> optional = findFirstInCaches(userEntity -> userEntity.getAccountName().equals(accountName) && userEntity.getPassword().equals(hash));
        if (optional.isPresent()) {
            return optional;
        } else {
            optional = userDAO.getUser(accountName, hash);
            optional.ifPresent(userEntity -> {
                weakReferencesCache.put(userEntity.getId(), userEntity);
                strongCache.put(userEntity.getId(), Optional.of(userEntity));
            });
            return optional;
        }
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias) {
        Collection<UserEntity> found = userDAO.getUserByAlias(alias);
        Collection<UserEntity> results = Sets.newTreeSet(Comparator.comparingInt(UserEntity::getId));
        for (UserEntity userEntity : found) {
            try {
                Optional<UserEntity> inCache = strongCache.get(
                        userEntity.getId(), () -> Optional.of(
                                weakReferencesCache.get(userEntity.getId(), () -> userEntity
                                )
                        )
                );
                results.add(inCache.orElse(userEntity));
            } catch (ExecutionException e) {
                e.printStackTrace();
                results.add(userEntity);
            }
        }
        return results;
    }

    @Override
    public Optional<UserEntity> getUserByAccountName(String accountName) {
        Optional<UserEntity> optional = findFirstInCaches(userEntity -> userEntity.getAccountName().equals(accountName));
        if (optional.isPresent()) {
            return optional;
        } else {
            optional = userDAO.getUserByAccountName(accountName);
            optional.ifPresent(userEntity -> {
                weakReferencesCache.put(userEntity.getId(), userEntity);
                strongCache.put(userEntity.getId(), Optional.of(userEntity));
            });
            return optional;
        }
    }
}
