package fr.univangers.vajin.network;

import fr.univangers.vajin.SnakeRPG;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;

import java.net.InetAddress;

public interface NetworkController {

    PacketCreator getPacketCreator();

    int getCurrentServerPort();

    InetAddress getCurrentServerAddress();

    void connect(InetAddress address, int port);

    void setCurrentServer(InetAddress address, int port);

    SnakeRPG getApplication();

    void startReceiver();

    void startTransmiter();

    void stopNetwork();

    LobbyBean getLobbyBean();

    void setLobbyBean(LobbyBean lobbyBean);

    DistantEngine getDistantEngine();
}
