package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.gameroom.*;
import fr.vajin.snakerpg.gameroom.impl.handlers.PlayerHandlerImpl;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NewConnectionHandlerNoFilterImpl implements NewConnectionHandler {

    final static Logger logger = Logger.getLogger(NewConnectionHandler.class.toString());

    Controller controller;
    DatagramSocket datagramSocket;
    Receiver receiver;

    int nextId = 1;

    public NewConnectionHandlerNoFilterImpl(Controller controller, DatagramSocket socket){

        this.controller = controller;
        this.datagramSocket = socket;
    }

    @Override
    public synchronized boolean handleDatagramPacket(DatagramPacket datagramPacket) {
        if (controller.getCurrentPlayerCount() < controller.getGameRoomSize()) {

            UserEntity userEntity = controller.acceptConnection(0,new byte[4], datagramPacket.getAddress() , datagramPacket.getPort() );

            if(userEntity == null){
                logger.log(Level.INFO, "[Thread - " + Thread.currentThread().getName() + "] Already waiting for response from " + datagramPacket.getAddress() + ":" + datagramPacket.getPort());
                return true; // TODO delegate
            }

            logger.log(Level.INFO, "[Thread - " + Thread.currentThread().getName() + "] New connection for " + datagramPacket.getAddress() + ":" + datagramPacket.getPort() + "\n" +
                    "User id : " + userEntity.getId());

            int userId = userEntity.getId();
            byte[] token = userEntity.getToken();
            //TODO retrieve from packet (or add argument on the method ??? Since we read it in the receiver)


            int port = datagramPacket.getPort();
            InetAddress address = datagramPacket.getAddress();
            PlayerHandler playerHandler = new PlayerHandlerImpl(datagramSocket,address, port, controller,userId, token);

            receiver.addPlayerHandler(playerHandler);
            controller.addPlayerHandler(playerHandler);
        } else {

            CustomByteArrayOutputStream stream = new CustomByteArrayOutputStream();
            //TODO créer packet à envoyer (connexion refusée)
            try {

                stream.writeInt(PlayerPacketCreator.ID_PROTOCOL);
                stream.writeInt(0);
                stream.writeInt(0);
                stream.writeInt(0);
                stream.writeInt(PlayerPacketCreator.RESP_JOIN);
                stream.writeInt(0);

                byte [] data = stream.toByteArray();

                DatagramPacket packetResponse = new DatagramPacket(data,data.length);
                packetResponse.setAddress(datagramPacket.getAddress());
                packetResponse.setPort(datagramPacket.getPort());

                datagramSocket.send(packetResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        return true;
    }

    @Override
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
