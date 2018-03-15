package fr.vajin.snakerpg.gameroom.impl;

import fr.univangers.vajin.engine.GameEngine;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class ActionPacketHandler implements PlayerPacketHandler{

    private static int BUFFER_START_POS = 24;

    private GameEngine engine;
    private PlayerHandler playerHandler;

    @Override
    public boolean handleDatagramPacket(DatagramPacket datagramPacket) {

        ByteBuffer buffer = ByteBuffer.wrap(datagramPacket.getData());

        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if(type==PlayerPacketCreator.PLAYER_ACTION){

            int action = buffer.getInt();

            this.engine.sendInput(this.playerHandler.getUserId(),action);

            return true;
        }

        return false;
    }

    @Override
    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void setEngine(GameEngine engine){
        this.engine = engine;
    }
}
