package fr.univangers.vajin.network;

import java.net.DatagramPacket;

public interface PacketHandler {
    
    

    void handlePacket(DatagramPacket packet);

}
