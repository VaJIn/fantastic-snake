package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.Transmiter;
import fr.vajin.snakerpg.LoggingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;

public class TransmiterThread extends Thread implements Transmiter {

    private final static Logger logger = LogManager.getLogger(TransmiterThread.class);

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

                DatagramPacket packet = this.controller.getPacketCreator().getPacket();

                packet.setAddress(controller.getCurrentServerAddress());
                packet.setPort(controller.getCurrentServerPort());

                socket.send(packet);
                LoggingUtilities.logPacketDebug(logger, packet, "Sending packet");

                long end = Instant.now().toEpochMilli();

                sleep((int) (1000.0 / frequency) - (end - start));
            }
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void send(DatagramPacket packet){

        this.address = controller.getCurrentServerAddress();
        this.port = controller.getCurrentServerPort();

        try {
            packet.setAddress(this.address);
            packet.setPort(this.port);

            LoggingUtilities.logPacketDebug(logger, packet, "Sending packet");
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
