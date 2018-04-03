package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerTransmiter extends Thread {

    private final static Logger logger =  Logger.getLogger(PlayerTransmiter.class.toString());

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

    @Override
    public void run() {

        try {
            while (!this.isInterrupted()) {

                long start = Instant.now().toEpochMilli();

                DatagramPacket packet = creator.getNextPacket();

                packet.setAddress(this.inetAdress);
                packet.setPort(port);

                byte[] data = packet.getData();

                StringBuilder debugMessageBuilder = new StringBuilder("Sending packet to " + packet.getAddress() + ":" + port + "\n");
                for(int i = 0; i < data.length; i += 4){
                    debugMessageBuilder.append(String.format("%02X%02X%02X%02X\t", data[i], data[i + 1], data[i + 2], data[i + 3]));
                }

                logger.log(Level.INFO, debugMessageBuilder.toString());

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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}