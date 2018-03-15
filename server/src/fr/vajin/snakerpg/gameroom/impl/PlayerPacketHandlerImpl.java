package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PlayerPacketHandlerImpl implements PlayerPacketHandler {

    private PlayerPacketCreator packetCreator;
    private PlayerTransmiter playerTransmiter;
    private PlayerHandler playerHandler;

    public PlayerPacketHandlerImpl(PlayerPacketCreator packetCreator, PlayerTransmiter playerTransmiter) {
        this.packetCreator = packetCreator;
        this.playerTransmiter = playerTransmiter;
    }

    @Override
    public boolean handleDatagramPacket(DatagramPacket datagramPacket) {

        ByteBuffer buffer = ByteBuffer.wrap(datagramPacket.getData());

        int idProtocol = buffer.getInt();

        if(idProtocol==PlayerPacketCreator.ID_PROTOCOL){
            int idPlayer = buffer.getInt();
            int tokenPlayer = buffer.getInt();

            int numSequence = buffer.getInt();

            int ack = buffer.getInt();
            this.packetCreator.acknowledgePacket(ack);

            int ackbitfield = buffer.getInt();

            int type = buffer.getInt();

            switch (type){
                case PlayerPacketCreator.JOIN:
                    break;
                case PlayerPacketCreator.LIFELINE:
                    this.playerHandler.aliveSignalReceive();
                    break;
                case PlayerPacketCreator.PLAYER_ACTION:
                    break;
            }
        }

        return false;
    }



    @Override
    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    @Override
    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }
}
