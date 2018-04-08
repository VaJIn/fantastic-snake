package fr.vajin.snakerpg.gameroom.impl.creators;

import com.google.gson.Gson;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;
import fr.vajin.snakerpg.jsondatabeans.PlayerBean;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Collection;

public class WaitingForGameState implements PlayerPacketCreator.PlayerPacketCreatorState {

    private static final Logger logger = LogManager.getLogger(WaitingForGameState.class);

    private PlayerPacketCreator creator;

    public WaitingForGameState(PlayerPacketCreator creator){
        this.creator = creator;
    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {
        byte[] data;

        logger.debug("Building game packet");


        stream.writeInt(PlayerPacketCreator.GAMEROOM_DESC);

        LobbyBean lobbyBean = new LobbyBean();

        lobbyBean.setGameMode(this.creator.getPlayerHandler().getController().currentGameMode());
        lobbyBean.setMap(this.creator.getPlayerHandler().getController().getMapName());

        Collection<PlayerBean> players = new ArrayList<>();

        for(PlayerHandler playerHandler : this.creator.getPlayerHandler().getController().getPlayerHandlers()){

            PlayerBean bean = new PlayerBean();

            bean.setLocalId(playerHandler.getUserId());
            bean.setAlias(playerHandler.getUserEntity().getAlias());

            players.add(bean);

        }

        lobbyBean.setPlayers(players);

        Gson gson = new Gson();

        String beanJson = gson.toJson(lobbyBean);

        stream.writeInt(beanJson.length());

        stream.write(beanJson.getBytes());


        data = stream.toByteArray();

        return new DatagramPacket(data, data.length);
    }
}
