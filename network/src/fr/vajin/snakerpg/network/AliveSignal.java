package fr.vajin.snakerpg.network;

import java.io.ByteArrayOutputStream;
import java.net.DatagramSocket;

public class AliveSignal extends Thread {

    private DatagramSocket socket;

    public AliveSignal(DatagramSocket socket){
        this.socket = socket;
    }


    @Override
    public void run() {

        while (!interrupted()){

            ByteArrayOutputStream stream = new ByteArrayOutputStream();





        }


    }
}
