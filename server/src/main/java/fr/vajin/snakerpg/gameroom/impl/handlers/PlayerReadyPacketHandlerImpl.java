package fr.vajin.snakerpg.gameroom.impl.handlers;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PlayerReadyPacketHandlerImpl implements PlayerPacketHandler{


    private static int BUFFER_START_POS = 24;

    private PlayerHandler playerHandler;

    @Override
    public boolean handleDatagramPacket(DatagramPacket datagramPacket) {

        ByteBuffer buffer = ByteBuffer.wrap(datagramPacket.getData());

        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if(type==PlayerPacketCreator.PLAYER_READY){

            playerHandler.getController().setPlayerReady(playerHandler.getUserId());

            return true;
        }

        return false;


    }

    @Override
    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

}
