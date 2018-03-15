package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PacketHandlerDistribuer implements PacketHandler {

    private PacketCreator packetCreator;

    private GamePacketHandler gamePacketHandler;
    private ConnectionPacketHandler connectionPacketHandler;


    public PacketHandlerDistribuer(PacketCreator packetCreator) {

        this.packetCreator = packetCreator;

        this.gamePacketHandler = new GamePacketHandler();
        this.connectionPacketHandler = new ConnectionPacketHandler();
    }


    @Override
    public void handlePacket(DatagramPacket packet) {


        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());


        int idProtocol = buffer.getInt();
        if (idProtocol == PacketCreator.ID_PROTOCOL) {
            int num_sequence = buffer.getInt();
            int lastIdReceived = buffer.getInt();

            this.packetCreator.acknowledgePacket(lastIdReceived);

            int ackbitfield = buffer.getInt();

            int type = buffer.getInt();

            switch (type) {
                case PacketCreator.RESP_JOIN:
                    connectionPacketHandler.handlePacket(packet);
                    break;
                case PacketCreator.GAME:
                    gamePacketHandler.handlePacket(packet);
                    break;
            }
        }//else discard packet
    }
}
