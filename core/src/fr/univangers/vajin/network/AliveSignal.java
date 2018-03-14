package fr.univangers.vajin.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import fr.univangers.vajin.GameConstants;

public class AliveSignal extends Thread {

    private PacketCreator packetCreator;

    private DatagramSocket socket;

    private InetAddress address;
    private int port;

    private double frequency;

    public AliveSignal(PacketCreator packetCreator, DatagramSocket socket, InetAddress address, int port, double frequency) {
        this.packetCreator = packetCreator;

        this.socket = socket;

        this.address = address;
        this.port = port;

        this.frequency = frequency;
    }

    @Override
    public void run() {

        try {

            while (!interrupted()){

                DatagramPacket packet = this.packetCreator.getPacket(PacketCreator.LIFELINE);

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
}
