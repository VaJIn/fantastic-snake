package fr.univangers.vajin.network.impl;

import com.google.gson.Gson;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class GameEndPacketHandler implements PacketHandler{

    private static int BUFFER_START_POS = 16;

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

        GameEndBean gameEndBean = gson.fromJson(String.valueOf(new String(jsonData)), GameEndBean.class);

        //TODO
    }
}
