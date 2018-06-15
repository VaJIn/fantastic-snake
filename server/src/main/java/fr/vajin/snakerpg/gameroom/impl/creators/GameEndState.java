package fr.vajin.snakerpg.gameroom.impl.creators;

import com.google.gson.Gson;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;

public class GameEndState implements PlayerPacketCreator.PlayerPacketCreatorState{

    private final static Logger logger = LogManager.getLogger();

    private PlayerPacketCreator creator;

    public GameEndState(PlayerPacketCreator creator){
        this.creator = creator;
    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {


        stream.writeInt(PlayerPacketCreator.GAME_END);

        GameEndBean gameEndBean = this.creator.getPlayerHandler().getController().getLastGameResults();

        Gson gson = new Gson();

        String beanJson = gson.toJson(gameEndBean);

        stream.writeInt(beanJson.length());

        stream.write(beanJson.getBytes());

        byte [] data = stream.toByteArray();

        logger.debug("End game packet built");

        return new DatagramPacket(data, data.length);
    }
}
