package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.Transmiter;
import fr.vajin.snakerpg.gameroom.Controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;

public class TransmiterThread extends Thread implements Transmiter {

    private PacketCreator packetCreator;

    private DatagramSocket socket;

    private NetworkController controller;
    private InetAddress address;
    private int port;

    private double frequency;

    public TransmiterThread(DatagramSocket socket, NetworkController controller, double frequency) {
        this.socket = socket;

        this.controller = controller;
        this.frequency = frequency;
    }

    @Override
    public void run(){
        try {
            while (!interrupted()){
                long start = Instant.now().toEpochMilli();

                DatagramPacket packet = this.packetCreator.getPacket();

                packet.setAddress(controller.getCurrentServerAddress());
                packet.setPort(controller.getCurrentServerPort());

                socket.send(packet);

                long end = Instant.now().toEpochMilli();

                sleep((int) (1000.0 / frequency) - (end - start));
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
