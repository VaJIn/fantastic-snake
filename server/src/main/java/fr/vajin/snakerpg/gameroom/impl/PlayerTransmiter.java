package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.LoggingUtilities;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;

public class PlayerTransmiter extends Thread {

    private final static Logger logger = LogManager.getLogger(PlayerTransmiter.class);

    private DatagramSocket socket;
    private PlayerPacketCreator creator;
    private float packetsPerSecond;
    private final InetAddress inetAdress;
    private final int port;

    public PlayerTransmiter(DatagramSocket socket, PlayerPacketCreator playerPacketCreator, int idProtocol, float packetsPerSecond, InetAddress inetAdress, int port) {
        this.socket = socket;
        this.packetsPerSecond = packetsPerSecond;
        this.creator = playerPacketCreator;
        this.inetAdress = inetAdress;
        this.port = port;
    }

    public void send(DatagramPacket datagramPacket){
        try {
            datagramPacket.setAddress(inetAdress);
            datagramPacket.setPort(port);

            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            while (!this.isInterrupted()) {

                long start = Instant.now().toEpochMilli();

                DatagramPacket packet = creator.getNextPacket();

                packet.setAddress(this.inetAdress);
                packet.setPort(port);

                LoggingUtilities.logPacketDebug(logger, packet, "Sending packet");

                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long end = Instant.now().toEpochMilli();

                sleep((int) (1000.0 / packetsPerSecond) - (end - start));
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}