package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class Receiver extends Thread {

    private DatagramSocket socket;
    private int idProtocol;
    private PacketHandler packetHandler;

    public Receiver(DatagramSocket socket, int idProtocol, PacketHandler packetHandler) {
        this.socket = socket;
        this.idProtocol = idProtocol;
        this.packetHandler = packetHandler;
    }

    @Override
    public void run() {
        System.out.println("receiver start");
        try {
            while (!this.isInterrupted()) {

                byte[] data = new byte[8192];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                this.socket.receive(packet);
                System.out.println("packet recu");

                ByteBuffer buffer = ByteBuffer.wrap(packet.getData());

                int idProtocol = buffer.getInt();
                if (idProtocol == this.idProtocol) {
                    Runnable runnable = () -> packetHandler.handlePacket(packet);
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
