package fr.vajin.snakerpg.gameroom.impl;

import fr.univangers.vajin.engine.GameEngine;
import fr.vajin.snakerpg.gameroom.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class NewConnectionHandlerNoFilterImpl implements NewConnectionHandler {


    Controller controller;
    DatagramSocket datagramSocket;
    Receiver receiver;


    @Override
    public synchronized boolean handleDatagramPacket(DatagramPacket datagramPacket) {
        if (controller.getCurrentPlayerCount() < controller.getGameRoomSize()) {

            int userId = 0;
            byte[] token = new byte[4];
            //TODO retreive from packet (or add argument on the method ??? Since we read it in the receiver)


            int port = datagramPacket.getPort();
            InetAddress address = datagramPacket.getAddress();

            PlayerPacketCreator playerPacketCreator = new PlayerPacketCreatorImpl(Controller.ID_PROTOCOL);
            GameEngine gameEngine = controller.getCurrentEngine();


            if (gameEngine != null) { //Only if the is a game being played
                playerPacketCreator.setEngine(gameEngine);
            }

            PlayerTransmiter playerTransmiter = new PlayerTransmiter(datagramSocket, playerPacketCreator, Controller.ID_PROTOCOL, 2f, address, port);

            PlayerPacketHandler playerPacketHandler = new PlayerPacketHandlerImpl(playerPacketCreator, playerTransmiter);


            PlayerHandler playerHandler = new PlayerHandlerImpl(userId, token, playerPacketHandler, playerTransmiter, playerPacketCreator);

            playerTransmiter.start();

            receiver.addPlayerHandler(playerHandler);
            controller.addPlayerHandler(playerHandler);

            return true;
        } else {
            return false;
        }
    }
}
