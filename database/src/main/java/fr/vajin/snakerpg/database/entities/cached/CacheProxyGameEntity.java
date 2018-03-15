package fr.vajin.snakerpg.database.entities.cached;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameModeDAO;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class CacheProxyGameEntity extends GameEntity {

    transient private DAOFactory daoFactory;
    transient private boolean retrievedGameParticipation = false;
    transient private boolean retrievedGameMode = false;

    public CacheProxyGameEntity(DAOFactory daoFactory) {
        super();
        this.daoFactory = daoFactory;
    }

    @Override
    public Set<GameParticipationEntity> getGameParticipationEntities() {
        if (!retrievedGameParticipation) {
            GameParticipationDAO gameParticipationDAO = daoFactory.getGameParticipationDAO();
            Collection<GameParticipationEntity> gameParticipationEntities = gameParticipationDAO.getGameResultsByGame(this.getId(), DAOFactory.SORT_BY_SCORE_DESC);
            this.setGameParticipationEntities(gameParticipationEntities);
        }
        return super.getGameParticipationEntities();
    }

    @Override
    public void setGameParticipationEntities(Collection<GameParticipationEntity> gameParticipationEntities) {
        super.setGameParticipationEntities(gameParticipationEntities);
        this.retrievedGameParticipation = true;
    }

    @Override
    public GameModeEntity getGameMode() {
        if (!retrievedGameMode) {
            GameModeDAO gameModeDAO = daoFactory.getGameModeDAO();
            Optional<GameModeEntity> gameModeEntityOptional = gameModeDAO.getGameMode(this.getGameModeId());
            gameModeEntityOptional.ifPresent(this::setGameMode);
            retrievedGameMode = true;
        }
        return super.getGameMode();
    }

    @Override
    public void setGameMode(GameModeEntity gameMode) {
        super.setGameMode(gameMode);
        retrievedGameMode = true;
    }
}
