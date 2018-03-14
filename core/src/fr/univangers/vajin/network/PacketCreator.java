package fr.univangers.vajin.network;

import java.net.DatagramPacket;

public interface PacketCreator {

    int JOIN = 1;
    int RESP_JOIN = 2;
    int LIFELINE = 3;
    int GAMEROOM_DESC = 4;
    int GAME_START = 5;
    int GAME = 6;
    int GAME_END = 7;
    int PLAYER_ACTION = 8;

    DatagramPacket getPacket(int type);

    void setLastIdReceived(int idReceived);

}
