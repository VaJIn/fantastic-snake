package fr.univangers.vajin.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import fr.univangers.vajin.GameConstants;

public class AliveSignal extends Thread {

    private DatagramSocket socket;

    private InetAddress address;
    private int port;

    private int idJoueur;
    private int tokenJoueur;

    private double frequency;

    public AliveSignal(DatagramSocket socket, InetAddress address, int port, int idJoueur, int tokenJoueur, double frequency) {
        this.socket = socket;

        this.address = address;
        this.port = port;

        this.idJoueur = idJoueur;
        this.tokenJoueur = tokenJoueur;

        this.frequency = frequency;
    }

    @Override
    public void run() {

        try {

            while (!interrupted()){

                byte data[] = new byte[16]; //4 ints

                ByteBuffer buffer = ByteBuffer.wrap(data);

                buffer.putInt(GameConstants.ID_PROTOCOL);

                buffer.putInt(idJoueur);

                buffer.putInt(tokenJoueur);

                buffer.putInt(0);

                DatagramPacket packet = new DatagramPacket(data,16);

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
