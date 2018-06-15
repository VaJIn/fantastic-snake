package fr.univangers.vajin.network;

import java.net.DatagramPacket;

public interface Transmiter {

    void send(DatagramPacket packet);

}
