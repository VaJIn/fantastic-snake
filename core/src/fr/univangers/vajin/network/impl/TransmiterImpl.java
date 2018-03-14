package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.Transmiter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TransmiterImpl implements Transmiter{

    private PacketCreator packetCreator;

    private DatagramSocket socket;

    private InetAddress address;
    private int port;


    public TransmiterImpl(PacketCreator packetCreator, DatagramSocket socket, InetAddress address, int port){
        this.packetCreator = packetCreator;
        this.socket = socket;

        this.address = address;
        this.port = port;
    }

    public void send(){

        try {

            DatagramPacket packet = this.packetCreator.getPacket(PacketCreator.PLAYER_ACTION);

            packet.setAddress(this.address);
            packet.setPort(this.port);

            this.socket.send(packet);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
