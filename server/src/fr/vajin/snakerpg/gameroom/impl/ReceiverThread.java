package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.Receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiverThread extends Thread{

    private Receiver receiver;
    private DatagramSocket socket;

    public ReceiverThread(Receiver receiver, DatagramSocket socket){
        this.receiver = receiver;
        this.socket = socket;
    }

    @Override
    public void run() {

        try{


            while (!this.isInterrupted()){

                byte[] data = new byte[8192];
                DatagramPacket packet = new DatagramPacket(data, data.length);

                socket.receive(packet);
                receiver.managePacket(packet);

            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }


        super.run();
    }
}
