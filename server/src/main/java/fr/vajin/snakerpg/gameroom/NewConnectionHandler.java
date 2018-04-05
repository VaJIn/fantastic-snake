package fr.vajin.snakerpg.gameroom;

import java.net.DatagramPacket;

public interface NewConnectionHandler {

    boolean handleDatagramPacket(DatagramPacket datagramPacket);
    void setReceiver(Receiver receiver);
}
