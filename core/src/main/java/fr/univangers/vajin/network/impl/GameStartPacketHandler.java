package fr.univangers.vajin.network.impl;

import com.google.gson.Gson;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.vajin.snakerpg.jsondatabeans.GameStartBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class GameStartPacketHandler implements PacketHandler{

    private static int BUFFER_START_POS = 16;

    private static final Logger logger = LogManager.getLogger(GameStartPacketHandler.class);

    NetworkController controller;

    public GameStartPacketHandler(NetworkController controller) {
        this.controller = controller;
    }

    @Override
    public void handlePacket(DatagramPacket packet) {
        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());

        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if(type!= PacketCreator.GAME_START){
            throw new IllegalArgumentException("Error packet type");
        }

        int jsonSize = buffer.getInt();

        byte [] jsonData = new byte[jsonSize];

        buffer.get(jsonData);

        String jsonString = new String(jsonData);

        Gson gson = new Gson();

        GameStartBean gameStartBean = gson.fromJson(String.valueOf(new String(jsonData)), GameStartBean.class);

        logger.debug("Received game start packet \n" +
                jsonString);

        controller.getApplication().getGameLoadingScreen().setMapFileName(gameStartBean.getMap());

        controller.getApplication().getDistantLobbyScreen().setGameStarting(true);

        controller.getApplication().getGameLoadingScreen().setLocalGame(false);

//        controller.getApplication().changeScreen(SnakeRPG.GAME_LOADING_SCREEN);
    }
}
