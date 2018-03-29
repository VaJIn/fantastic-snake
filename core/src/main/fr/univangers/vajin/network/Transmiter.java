package fr.univangers.vajin.network;

import java.net.DatagramPacket;

public interface Transmiter {


    void setPacketCreator(PacketCreator packetCreator);
    void send(DatagramPacket packet);
}
