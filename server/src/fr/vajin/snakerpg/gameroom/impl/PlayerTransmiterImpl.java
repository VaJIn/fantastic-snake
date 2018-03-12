package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerTransmiter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;

public class PlayerTransmiterImpl extends Thread implements PlayerTransmiter {

    private DatagramSocket socket;
    private PlayerPacketCreator creator;
    private float packetsPerSecond;
    private final InetAddress inetAdress;
    private final int port;

    public PlayerTransmiterImpl(DatagramSocket socket, PlayerPacketCreator playerPacketCreator, int idProtocol, float packetsPerSecond, InetAddress inetAdress, int port) {
        this.socket = socket;
        this.packetsPerSecond = packetsPerSecond;
        this.creator = playerPacketCreator;
        this.inetAdress = inetAdress;
        this.port = port;
    }

    @Override
    public void run() {

        try {
            while (!this.isInterrupted()) {

                System.out.println("Sending packet");

                long start = Instant.now().toEpochMilli();

                DatagramPacket packet = creator.getNextPacket();


                packet.setAddress(this.inetAdress);
                packet.setPort(port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long end = Instant.now().toEpochMilli();

                sleep((int) (1000.0 / packetsPerSecond) - (end - start));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}