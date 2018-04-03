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

        try {
            while (this.isInterrupted()) {
                byte[] data = new byte[8192];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                this.socket.receive(packet);

                ByteBuffer buffer = ByteBuffer.wrap(packet.getData());

                int idProtocol = buffer.getInt();
                if (idProtocol == this.idProtocol) {
                    this.packetHandler.handlePacket(packet);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
