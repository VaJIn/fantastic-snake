package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.DistantEngine;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
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

    public NetworkControllerImpl(SnakeRPG snakeRPG, DatagramSocket socket){

        this.application = snakeRPG;

        this.lobbyBean = new LobbyBean();

        this.socket = socket;

        this.transmiterThread = new TransmiterThread(this.socket, this, TRANSMITER_FREQUENCY);
        this.packetCreator = new PacketCreatorImpl(PacketCreator.ID_PROTOCOL,0,0,transmiterThread);
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
            stream.writeInt(PacketCreator.JOIN);

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
}
