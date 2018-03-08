package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerTransmiter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.Instant;

public class PlayerTransmiterImpl extends Thread implements PlayerTransmiter {

    private DatagramSocket socket;
    private PlayerPacketCreator creator;
    private int packetsPerSecond;

    public PlayerTransmiterImpl(DatagramSocket socket, int idProtocol, int packetsPerSecond) {
        this.socket = socket;
        this.packetsPerSecond = packetsPerSecond;
        this.creator = new PlayerPacketCreatorImpl(idProtocol);
    }

    @Override
    public void run() {

        try {
            while (!this.isInterrupted()) {

                long start = Instant.now().toEpochMilli();

                DatagramPacket packet = creator.getNextPacket();

                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long end = Instant.now().toEpochMilli();

                sleep(1000 / packetsPerSecond - (end - start));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}