package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;

import java.net.InetAddress;

public class NetworkControllerImpl implements NetworkController {

    private int currentServerPort;
    private InetAddress currentServerAddress;

    private PacketHandler packetHandler;
    private PacketCreator packetCreator;
    private Receiver receiverThread;
    private TransmiterThread transmiterThread;

    @Override
    public int getCurrentServerPort() {
        return currentServerPort;
    }

    @Override
    public InetAddress getCurrentServerAddress() {
        return currentServerAddress;
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
            transmiterThread.isAlive();
        }
    }

    @Override
    public void stopNetwork() {
        transmiterThread.interrupt();
        receiverThread.interrupt();
    }
}
