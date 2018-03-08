package fr.vajin.snakerpg.gameroom;

import java.net.DatagramPacket;

public interface PlayerPacketHandler {

    boolean handleDatagramPacket(DatagramPacket datagramPacket);
}
