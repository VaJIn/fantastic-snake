package fr.univangers.vajin.network.impl;

import com.google.gson.Gson;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.univangers.vajin.screens.HostLobbyScreen;
import fr.univangers.vajin.screens.LobbyScreen;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class LobbyPacketHandler implements PacketHandler {

    private static int BUFFER_START_POS = 16;
    private LobbyScreen lobbyScreen;

    public LobbyPacketHandler(NetworkController controller){
        this.lobbyScreen = controller.getSnakeRPG().getDistantLobbyScreen();
    }

    @Override
    public void handlePacket(DatagramPacket packet) {

        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());

        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if(type!= PacketCreator.GAMEROOM_DESC){
            throw new IllegalArgumentException("Error packet type");
        }

        int jsonSize = buffer.getInt();

        byte [] jsonData = new byte[jsonSize];

        buffer.get(jsonData);

        Gson gson = new Gson();

        String jsonString = new String(jsonData);

        System.out.println(jsonString);

        LobbyBean lobbyBean = gson.fromJson(jsonString,LobbyBean.class);

        this.lobbyScreen.setLobbyBean(lobbyBean);

    }
}
