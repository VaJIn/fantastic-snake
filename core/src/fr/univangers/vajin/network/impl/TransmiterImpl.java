package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.Transmiter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TransmiterImpl extends Thread implements Transmiter{

    private PacketCreator packetCreator;

    private DatagramSocket socket;

    private InetAddress address;
    private int port;

    private double frequency;

    public TransmiterImpl(PacketCreator packetCreator, DatagramSocket socket, InetAddress address, int port, double frequency){
        this.packetCreator = packetCreator;
        this.socket = socket;

        this.address = address;
        this.port = port;

        this.frequency = frequency;
    }

    @Override
    public void run(){

        try {

            while (!interrupted()){

                DatagramPacket packet = this.packetCreator.getPacket();

                packet.setAddress(address);
                packet.setPort(port);

                socket.send(packet);

                sleep((long) (1000/frequency));

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPacketCreator(PacketCreator packetCreator) {
        this.packetCreator = packetCreator;
    }

    public synchronized void send(DatagramPacket packet){

        try {

            packet.setAddress(this.address);
            packet.setPort(this.port);

            this.socket.send(packet);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
