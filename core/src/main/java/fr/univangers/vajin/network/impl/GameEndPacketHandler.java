package fr.univangers.vajin.network.impl;

import com.google.gson.Gson;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class GameEndPacketHandler implements PacketHandler{

    private static int BUFFER_START_POS = 16;

    private static final Logger logger = LogManager.getLogger(GameEndPacketHandler.class);

    private NetworkController controller;

    public GameEndPacketHandler(NetworkController controller){
        this.controller = controller;
    }

    @Override
    public void handlePacket(DatagramPacket packet) {

        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());
        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if(type!= PacketCreator.GAME_END){
            throw new IllegalArgumentException("Error packet type");
        }

        int jsonSize = buffer.getInt();
        byte [] jsonData = new byte[jsonSize];
        buffer.get(jsonData);

        Gson gson = new Gson();

        String jsonString = new String(jsonData);

        GameEndBean gameEndBean = gson.fromJson(jsonString, GameEndBean.class);

        logger.debug("Received game end packet \n" + jsonString);

        this.controller.setGameEndBean(gameEndBean);
    }
}
