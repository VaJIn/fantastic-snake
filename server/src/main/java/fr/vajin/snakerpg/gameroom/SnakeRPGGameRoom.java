package fr.vajin.snakerpg.gameroom;

import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.gameroom.impl.ControllerNoFilterImpl;
import fr.vajin.snakerpg.gameroom.impl.NewConnectionHandlerNoFilterImpl;
import fr.vajin.snakerpg.gameroom.impl.ReceiverImpl;
import fr.vajin.snakerpg.gameroom.impl.ReceiverThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramSocket;
import java.net.SocketException;

public class SnakeRPGGameRoom {

    final static Logger logger = LogManager.getLogger(SnakeRPGGameRoom.class);

    public static void main(String[] args) {


        try {

            Integer port = null;

            if (args.length!=1){
                logger.info("No port provided - launching server on available port");
            } else {
                try {
                    port = Integer.valueOf(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("The given port (" + args[0] + ") is not an integer");
                    System.exit(1);
                }
            }

            DatagramSocket socket;
            if (port == null) {
                socket = new DatagramSocket();
            } else {
                socket = new DatagramSocket(port);
            }




            GameModeEntity gameMode = new GameModeEntity();
            gameMode.setId(1);
            gameMode.setMinPlayer(1);
            gameMode.setMaxPlayer(4);
            gameMode.setName("classic");

            Controller controller = new ControllerNoFilterImpl(gameMode, "simple_map.tmx");



            NewConnectionHandler newConnectionHandler = new NewConnectionHandlerNoFilterImpl(controller, socket);
            Receiver receiver = new ReceiverImpl(PlayerPacketCreator.ID_PROTOCOL, controller, newConnectionHandler);
            newConnectionHandler.setReceiver(receiver);

            ReceiverThread receiverThread = new ReceiverThread(receiver, socket);
            receiverThread.start();

            logger.info("Server started on port " + socket.getLocalPort());

        } catch (SocketException e) {
            e.printStackTrace();
        }



    }
}
