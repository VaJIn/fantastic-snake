package fr.vajin.snakerpg.gameroom.impl.creators;

import com.google.gson.Gson;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;
import fr.vajin.snakerpg.jsondatabeans.GameParticipationBean;
import fr.vajin.snakerpg.jsondatabeans.PlayerBean;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Collection;

public class GameEndState implements PlayerPacketCreator.PlayerPacketCreatorState{


    private final Logger logger = LogManager.getLogger(GameState.class);


    private PlayerPacketCreator creator;

    public GameEndState(PlayerPacketCreator creator){
        this.creator = creator;
    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {

        logger.debug("GameEndPacket");

        stream.writeInt(PlayerPacketCreator.GAME_END);

        GameEndBean gameEndBean = new GameEndBean();

        Collection<GameParticipationEntity> gameResults = this.creator.getPlayerHandler().getController().getLastGameResults();

        Collection<GameParticipationBean> gameParticipationBeans = new ArrayList<>();

        for(GameParticipationEntity gameParticipationEntity : gameResults){

            GameParticipationBean gameParticipationBean = new GameParticipationBean();
            PlayerBean playerBean = new PlayerBean();

            playerBean.setLocalId(gameParticipationEntity.getIdUser());
            playerBean.setAlias(gameParticipationEntity.getUser().getAlias());

            gameParticipationBean.setPlayer(playerBean);
            gameParticipationBean.setDeathCount(gameParticipationEntity.getDeathCount());
            gameParticipationBean.setKillCount(gameParticipationEntity.getKillCount());

            gameParticipationBeans.add(gameParticipationBean);
        }

        gameEndBean.setGameParticipations(gameParticipationBeans);

        Gson gson = new Gson();

        String beanJson = gson.toJson(gameEndBean);

        stream.writeInt(beanJson.length());

        stream.write(beanJson.getBytes());

        byte [] data = stream.toByteArray();

        return new DatagramPacket(data, data.length);
    }
}
