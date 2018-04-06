package fr.univangers.vajin.network.impl;

import com.google.gson.Gson;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.vajin.snakerpg.jsondatabeans.GameStartBean;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class GameStartPacketHandler implements PacketHandler{

    private static int BUFFER_START_POS = 16;

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

        Gson gson = new Gson();

        GameStartBean gameStartBean = gson.fromJson(String.valueOf(jsonData), GameStartBean.class);


        //TODO
    }
}
