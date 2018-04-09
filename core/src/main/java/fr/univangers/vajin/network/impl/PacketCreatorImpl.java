package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.Transmiter;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;

public class PacketCreatorImpl implements PacketCreator{
    private int numSequence = 0;

    private int idProtocol;

    private int lastIdReceived = 0;
    private int ackBitfield = 0;

    private Transmiter transmiter;
    private NetworkController networkController;

    public PacketCreatorImpl(NetworkController networkController, int idProtocol, Transmiter transmiter) {

        this.idProtocol = idProtocol;

        this.networkController = networkController;

        this.transmiter = transmiter;
    }

    private CustomByteArrayOutputStream getPacketStream() throws IOException {
        CustomByteArrayOutputStream stream = new CustomByteArrayOutputStream();

        numSequence++;

        stream.writeInt(idProtocol);

        stream.writeInt(networkController.getIdPlayer());

        stream.writeInt(networkController.getTokenPlayer());

        stream.writeInt(numSequence);

        stream.writeInt(this.lastIdReceived);

        stream.writeInt(ackBitfield);

        return stream;
    }

    @Override
    public DatagramPacket getPacket() throws IOException {

        CustomByteArrayOutputStream stream = this.getPacketStream();

        stream.writeInt(LIFELINE);

        byte [] data = stream.toByteArray();


        return new DatagramPacket(data, data.length);
    }

    @Override
    public void setTransmiter(Transmiter transmiter) {
        this.transmiter = transmiter;
    }

    @Override
    public void acknowledgePacket(int idReceived) {

    }


    @Override
    public void sendPlayerAction(int action) {

        try {
            CustomByteArrayOutputStream stream = this.getPacketStream();

            stream.writeInt(PLAYER_ACTION);
            stream.writeInt(action);

            byte [] data = stream.toByteArray();
            this.transmiter.send(new DatagramPacket(data, data.length));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendPlayerReady() {

        try {
            CustomByteArrayOutputStream stream = this.getPacketStream();

            stream.writeInt(PLAYER_READY);

            byte [] data = stream.toByteArray();
            this.transmiter.send(new DatagramPacket(data, data.length));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
