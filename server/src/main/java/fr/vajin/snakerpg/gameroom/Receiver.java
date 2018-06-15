package fr.vajin.snakerpg.gameroom;

import java.net.DatagramPacket;

public interface Receiver extends Runnable {

    void managePacket(DatagramPacket packet);

    void setNewConnectionHandler(NewConnectionHandler newConnectionHandler);

    void addPlayerHandler(PlayerHandler handler);

    void removePlayerHandler(PlayerHandler handler);

}