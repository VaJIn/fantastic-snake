package fr.vajin.snakerpg.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class NetworkTest {


    public static void main(String [] args){

        try {
            DatagramSocket socket = new DatagramSocket(6969);

            while(true){

                byte [] data = new byte[8192];
                DatagramPacket packet = new DatagramPacket(data,data.length);

                System.out.println("==================================================================");

                socket.receive(packet);

                ByteBuffer buffer = ByteBuffer.wrap(packet.getData());


                int idProtocol = buffer.getInt();
                System.out.format("idProtocol : 0x%08X", idProtocol);
                System.out.println();
                int lastIdReceived = buffer.getInt();
                System.out.println("lastIdReceived : "+lastIdReceived);
                int ackbitfield = buffer.getInt();
                System.out.format("ackbitfield : 0x%08X", ackbitfield);

                System.out.println();


                while(buffer.hasRemaining()){

                    int  idEntity = buffer.getInt();
                    System.out.println("idEntity : " + idEntity);
                    if(idEntity==-1){
                        break;
                    }
                    int idTile;
                    while( (idTile = buffer.getInt()) != -1){
                        System.out.println("idTile : "+idTile);
                        int posX = buffer.getInt();
                        System.out.println("posXTile : "+posX);
                        int posY = buffer.getInt();
                        System.out.println("posYTile : "+posY);
                        int sizeRessourceKeyBytes = buffer.getInt();
                        System.out.println("sizeRessourceKeyBytes : "+sizeRessourceKeyBytes);
                        byte [] ressourceKey = new byte[sizeRessourceKeyBytes];

                        buffer.get(ressourceKey);

                        System.out.println("ressourceKey : "+new String(ressourceKey));

                    }

                }

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

