package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.DistantEngine;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkControllerImpl implements NetworkController {

    private static final double TRANSMITER_FREQUENCY = 0.5;
    private static final Logger logger = LogManager.getLogger(NetworkControllerImpl.class);

    private SnakeRPG application;

    private DatagramSocket socket;
    private int currentServerPort;
    private InetAddress currentServerAddress;

    private PacketHandlerDistribuer packetHandler;
    private PacketCreator packetCreator;
    private Receiver receiverThread;
    private TransmiterThread transmiterThread;

    private DistantEngine distantEngine;

    private LobbyBean lobbyBean;
    private GameEndBean gameEndBean;

    private int idPlayer;
    private int tokenPlayer;


    public NetworkControllerImpl(SnakeRPG snakeRPG, DatagramSocket socket){

        this.application = snakeRPG;

        this.lobbyBean = new LobbyBean();

        this.socket = socket;

        this.transmiterThread = new TransmiterThread(this.socket, this, TRANSMITER_FREQUENCY);
        this.packetCreator = new PacketCreatorImpl(this, PacketCreator.ID_PROTOCOL, transmiterThread);
        this.packetHandler = new PacketHandlerDistribuer(this,this.packetCreator);

        this.receiverThread = new Receiver(this.socket,PacketCreator.ID_PROTOCOL,this.packetHandler);
    }

    @Override
    public int getCurrentServerPort() {
        return currentServerPort;
    }

    @Override
    public InetAddress getCurrentServerAddress() {
        return currentServerAddress;
    }

    @Override
    public void connect(InetAddress address, int port) {

        logger.debug("Connecting to server " + address + ":" + port);

        CustomByteArrayOutputStream stream = new CustomByteArrayOutputStream();
        try {
            stream.writeInt(PacketCreator.ID_PROTOCOL);

            //id joueur
            stream.writeInt(0);
            //token
            stream.writeInt(0);

            stream.writeInt(PacketCreator.JOIN);

            String userAlias = this.getApplication().getAppPreferences().getLocalAlias();
            if (userAlias == null) {
                userAlias = "";
            }

            byte[] userAliasBytes = userAlias.getBytes();

            stream.writeInt(userAliasBytes.length);
            stream.write(userAliasBytes);

            byte[] data = stream.toByteArray();
            DatagramPacket packet = new DatagramPacket(data,data.length);

            packet.setPort(port);
            packet.setAddress(address);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void setCurrentServer(InetAddress address, int port) {
        this.currentServerAddress = address;
        this.currentServerPort = port;
    }

    @Override
    public PacketCreator getPacketCreator() {
        return packetCreator;
    }

    @Override
    public void startReceiver() {
        if (!receiverThread.isAlive()) {
            receiverThread.start();
        }
    }

    @Override
    public void startTransmiter() {
        if (!transmiterThread.isAlive()) {
            transmiterThread.start();
        }
    }

    @Override
    public void stopNetwork() {
        logger.debug("Stopped network");
        transmiterThread.interrupt();
        receiverThread.interrupt();

        this.receiverThread = new Receiver(this.socket, PacketCreator.ID_PROTOCOL, this.packetHandler);
        this.transmiterThread = new TransmiterThread(socket, this, TRANSMITER_FREQUENCY);
    }

    @Override
    public void disconnect() {
        this.stopNetwork();
        this.lobbyBean = new LobbyBean();
    }

    @Override
    public LobbyBean getLobbyBean() {
        return lobbyBean;
    }

    @Override
    public void setLobbyBean(LobbyBean lobbyBean) {
        if (this.lobbyBean != lobbyBean) {
            this.lobbyBean = lobbyBean;
            this.application.getDistantLobbyScreen().updateTable();
        }
    }

    public SnakeRPG getApplication() {
        return application;
    }

    @Override
    public DistantEngine getDistantEngine() {
        if (this.distantEngine == null) {
            this.distantEngine = new DistantEngine();
        }
        return distantEngine;
    }

    @Override
    public void setPlayerInfos(int idPlayer, int tokenPlayer) {
        this.idPlayer = idPlayer;
        this.tokenPlayer = tokenPlayer;
    }

    @Override
    public int getIdPlayer() {
        return idPlayer;
    }

    @Override
    public int getTokenPlayer() {
        return tokenPlayer;
    }

    @Override
    public GameEndBean getGameEndBean() {
        return gameEndBean;
    }

    @Override
    public void setGameEndBean(GameEndBean gameEndBean) {
        this.gameEndBean = gameEndBean;
        this.getApplication().getEndGameScreen().updateTable();
    }

    @Override
    public void sendInput(int input) {

        this.packetCreator.sendPlayerAction(input);

    }
}
