package fr.vajin.snakerpg.servlet.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.database.entities.cached.CacheProxyGameEntity;
import fr.vajin.snakerpg.database.entities.cached.CacheProxyUserEntity;

import java.util.List;
import java.util.Map;

public class LeaderBoardData {

    private final List<GameParticipationEntity> gameParticipations;
    private final Map<Integer, GameEntity> games;
    private final Map<Integer, UserEntity> users;


    public LeaderBoardData(List<GameParticipationEntity> gameParticipations) {
        this.gameParticipations = ImmutableList.copyOf(gameParticipations);

        Map<Integer, GameEntity> gameEntityMap = Maps.newHashMap();
        Map<Integer, UserEntity> userEntityMap = Maps.newHashMap();

        for (GameParticipationEntity gameParticipationEntity : this.gameParticipations) {
            if (!gameEntityMap.containsKey(gameParticipationEntity.getIdGame())) {
                GameEntity gameEntity = gameParticipationEntity.getGame();

                if (gameEntity instanceof CacheProxyGameEntity) {
                    //So object are loaded
                    gameEntity.getGameMode();
//                    gameEntity.getGameParticipationEntities();
                }

                gameEntityMap.put(gameEntity.getId(), gameEntity);
            }
            if (!userEntityMap.containsKey(gameParticipationEntity.getIdUser())) {

                UserEntity userEntity = gameParticipationEntity.getUser();

                if (userEntity instanceof CacheProxyUserEntity) {
                    //So object are loaded
                    userEntity.getSnakes();
                }

                userEntityMap.put(userEntity.getId(), userEntity);
            }
        }

        this.games = ImmutableMap.copyOf(gameEntityMap);
        this.users = ImmutableMap.copyOf(userEntityMap);
    }

    public List<GameParticipationEntity> getGameParticipations() {
        return gameParticipations;
    }

    public Map<Integer, GameEntity> getGames() {
        return games;
    }

    public Map<Integer, UserEntity> getUsers() {
        return users;
    }
}
