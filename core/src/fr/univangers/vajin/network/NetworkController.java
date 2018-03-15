package fr.univangers.vajin.network;

import java.net.InetAddress;

public interface NetworkController {

    PacketCreator getPacketCreator();

    int getCurrentServerPort();

    InetAddress getCurrentServerAddress();

    void setCurrentServer(InetAddress address, int port);

    void startReceiver();

    void startTransmiter();

    void stopNetwork();

}
