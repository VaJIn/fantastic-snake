package fr.univangers.vajin.network;

import fr.univangers.vajin.SnakeRPG;
import java.net.InetAddress;

public interface NetworkController {

    PacketCreator getPacketCreator();

    int getCurrentServerPort();

    InetAddress getCurrentServerAddress();

    void connect(InetAddress address, int port);

    void setCurrentServer(InetAddress address, int port);

    SnakeRPG getSnakeRPG();

    void startReceiver();

    void startTransmiter();

    void stopNetwork();


}
