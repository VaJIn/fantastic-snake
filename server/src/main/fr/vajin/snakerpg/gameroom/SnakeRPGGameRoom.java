package fr.vajin.snakerpg.gameroom;

import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.gameroom.impl.ControllerNoFilterImpl;
import fr.vajin.snakerpg.gameroom.impl.NewConnectionHandlerNoFilterImpl;
import fr.vajin.snakerpg.gameroom.impl.ReceiverImpl;
import fr.vajin.snakerpg.gameroom.impl.ReceiverThread;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SnakeRPGGameRoom {

    public final static Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {


        try {

            Integer port = null;

            try{
                port = Integer.valueOf(args[0]);
            }
            catch (NumberFormatException e){
                System.exit(1);
            }



            GameModeEntity gameMode = new GameModeEntity();
            gameMode.setId(1);
            gameMode.setMinPlayer(1);
            gameMode.setMaxPlayer(4);
            gameMode.setName("classic");

            Controller controller = new ControllerNoFilterImpl(gameMode, "");

            DatagramSocket socket = new DatagramSocket(port);


            NewConnectionHandler newConnectionHandler = new NewConnectionHandlerNoFilterImpl(controller, socket);
            Receiver receiver = new ReceiverImpl(PlayerPacketCreator.ID_PROTOCOL, controller, newConnectionHandler);
            newConnectionHandler.setReceiver(receiver);

            ReceiverThread receiverThread = new ReceiverThread(receiver, socket);
            receiverThread.start();

            logger.info("Server started on port " + port);

        } catch (SocketException e) {
            e.printStackTrace();
        }



    }
}