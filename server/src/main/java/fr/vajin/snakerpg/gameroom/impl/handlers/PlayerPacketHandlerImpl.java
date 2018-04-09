package fr.vajin.snakerpg.gameroom.impl.handlers;

import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;
import fr.vajin.snakerpg.gameroom.impl.PlayerTransmiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PlayerPacketHandlerImpl implements PlayerPacketHandler {

    private static final Logger logger = LogManager.getLogger();

    private Controller controller;
    private PlayerPacketCreator packetCreator;
    private PlayerTransmiter playerTransmiter;
    private PlayerHandler playerHandler;

    private ActionPacketHandler actionPacketHandler;
    private PlayerReadyPacketHandlerImpl playerReadyPacketHandler;

    public PlayerPacketHandlerImpl(PlayerPacketCreator packetCreator, PlayerTransmiter playerTransmiter, Controller controller) {
        this.controller = controller;
        this.packetCreator = packetCreator;
        this.playerTransmiter = playerTransmiter;
        this.actionPacketHandler = new ActionPacketHandler();
        this.playerReadyPacketHandler = new PlayerReadyPacketHandlerImpl();
    }

    @Override
    public boolean handleDatagramPacket(DatagramPacket datagramPacket) {

        ByteBuffer buffer = ByteBuffer.wrap(datagramPacket.getData());

        int idProtocol = buffer.getInt();

        if(idProtocol==PlayerPacketCreator.ID_PROTOCOL){

            int idPlayer = buffer.getInt();
            byte tokenPlayer[] = new byte[4];
            buffer.get(tokenPlayer);

            if((idPlayer==this.playerHandler.getUserId()) && ( Arrays.equals( tokenPlayer,this.playerHandler.getUserToken() ) ) ){
                int numSequence = buffer.getInt();

                int ack = buffer.getInt();
                this.packetCreator.acknowledgePacket(ack);

                int ackbitfield = buffer.getInt();

                int type = buffer.getInt();

                switch (type){
                    case PlayerPacketCreator.JOIN:
                        logger.debug("Join Packet");
                        break;
                    case PlayerPacketCreator.LIFELINE:
                        logger.debug("Lifeline packet");
                        this.playerHandler.aliveSignalReceive();
                        return true;
                    case PlayerPacketCreator.PLAYER_ACTION:
                        logger.debug("Action Packet");
                        return this.actionPacketHandler.handleDatagramPacket(datagramPacket);
                    case PlayerPacketCreator.PLAYER_READY:
                        logger.debug("Player Ready");
                        return this.playerReadyPacketHandler.handleDatagramPacket(datagramPacket);
                }
            }


        }

        return false;
    }

    @Override
    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;

        this.actionPacketHandler.setPlayerHandler(playerHandler);
        this.playerReadyPacketHandler.setPlayerHandler(playerHandler);
    }
}
