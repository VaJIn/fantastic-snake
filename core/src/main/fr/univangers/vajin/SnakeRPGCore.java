package fr.univangers.vajin;

import fr.univangers.vajin.network.PacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.*;

public class SnakeRPGCore {

    public static void main(String [] args){

        try {
            DatagramSocket socket = new DatagramSocket(1534);

            CustomByteArrayOutputStream stream = new CustomByteArrayOutputStream();
            stream.writeInt(PacketCreator.ID_PROTOCOL);

            //id joueur
            stream.writeInt(0);

            byte[] data = stream.toByteArray();
            DatagramPacket packet = new DatagramPacket(data,data.length);

            packet.setPort(1534);
            packet.setAddress(InetAddress.getByName("HP4"));
            socket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
