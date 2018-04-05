package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.univangers.vajin.network.Transmiter;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkControllerImpl implements NetworkController {

    private SnakeRPG snakeRPG;

    private DatagramSocket socket;
    private int currentServerPort;
    private InetAddress currentServerAddress;

    private PacketHandlerDistribuer packetHandler;
    private PacketCreator packetCreator;
    private Receiver receiverThread;
    private TransmiterThread transmiterThread;

    public NetworkControllerImpl(SnakeRPG snakeRPG, DatagramSocket socket){

        this.snakeRPG = snakeRPG;

        this.socket = socket;

        this.transmiterThread = new TransmiterThread(this.socket,this,0.5);
        this.packetCreator = new PacketCreatorImpl(PacketCreator.ID_PROTOCOL,0,0,transmiterThread);
        this.packetHandler = new PacketHandlerDistribuer(this,this.packetCreator);
        this.transmiterThread.setPacketCreator(this.packetCreator);

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
        transmiterThread.interrupt();
        receiverThread.interrupt();
    }


    public SnakeRPG getSnakeRPG(){
        return snakeRPG;
    }
}
