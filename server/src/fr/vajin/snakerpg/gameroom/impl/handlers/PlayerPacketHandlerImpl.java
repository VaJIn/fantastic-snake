package fr.vajin.snakerpg.gameroom.impl.handlers;

import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;
import fr.vajin.snakerpg.gameroom.impl.PlayerTransmiter;
import fr.vajin.snakerpg.gameroom.impl.handlers.ActionPacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PlayerPacketHandlerImpl implements PlayerPacketHandler {

    private Controller controller;
    private PlayerPacketCreator packetCreator;
    private PlayerTransmiter playerTransmiter;
    private PlayerHandler playerHandler;

    private ActionPacketHandler actionPacketHandler;

    public PlayerPacketHandlerImpl(PlayerPacketCreator packetCreator, PlayerTransmiter playerTransmiter, Controller controller) {
        this.controller = controller;
        this.packetCreator = packetCreator;
        this.playerTransmiter = playerTransmiter;

        this.actionPacketHandler = new ActionPacketHandler();
        this.actionPacketHandler.setEngine(this.controller.getCurrentEngine());
    }

    @Override
    public boolean handleDatagramPacket(DatagramPacket datagramPacket) {

        ByteBuffer buffer = ByteBuffer.wrap(datagramPacket.getData());

        int idProtocol = buffer.getInt();

        if(idProtocol==PlayerPacketCreator.ID_PROTOCOL){

            int idPlayer = buffer.getInt();
            int tokenPlayer = buffer.getInt();

            if((idPlayer==this.playerHandler.getUserId())&&(tokenPlayer==this.playerHandler.getUserToken())){
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
                        return true;
                    case PlayerPacketCreator.PLAYER_ACTION:
                        return this.actionPacketHandler.handleDatagramPacket(datagramPacket);
                }
            }


        }

        return false;
    }

    @Override
    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }
}
