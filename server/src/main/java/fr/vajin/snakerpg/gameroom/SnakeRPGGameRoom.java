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
                System.out.println("Please provide the port as unique argument of the program. Exiting");
                System.exit(1);
            }

            try{
                port = Integer.valueOf(args[0]);
            }
            catch (NumberFormatException e){
                System.out.println("The given port ("+args[0]+") is not an integer");
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
