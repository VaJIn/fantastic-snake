package fr.univangers.vajin.network;

import fr.univangers.vajin.SnakeRPG;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;
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

    void disconnect();

    LobbyBean getLobbyBean();

    void setLobbyBean(LobbyBean lobbyBean);

    void setPlayerInfos(int idPlayer, int tokenPlayer);

    DistantEngine getDistantEngine();

    int getIdPlayer();

    int getTokenPlayer();

    void sendInput(int input);

    GameEndBean getGameEndBean();

    void setGameEndBean(GameEndBean gameEndBean);
}
