package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.vajin.snakerpg.jsondatabeans.GameStartBean;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PacketHandlerDistribuer implements PacketHandler {

    private PacketCreator packetCreator;

    private PacketHandler connectionPacketHandler;
    private PacketHandler lobbyPacketHandler;
    private PacketHandler gameStartPacketHandler;
    private PacketHandler gamePacketHandler;
    private PacketHandler gameEndPacketHandler;

    public PacketHandlerDistribuer(PacketCreator packetCreator) {

        this.packetCreator = packetCreator;

        this.connectionPacketHandler = new ConnectionPacketHandler();

        this.lobbyPacketHandler = new LobbyPacketHandler();

        this.gameStartPacketHandler = new GameStartPacketHandler();
        this.gamePacketHandler = new GamePacketHandler();
        this.gameEndPacketHandler = new GameEndPacketHandler();
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
                case PacketCreator.GAMEROOM_DESC:
                    lobbyPacketHandler.handlePacket(packet);
                    break;
                case PacketCreator.GAME:
                    gamePacketHandler.handlePacket(packet);
                    break;
                case PacketCreator.GAME_START:
                    gameStartPacketHandler.handlePacket(packet);
                    break;
                case PacketCreator.GAME_END:
                    gameEndPacketHandler.handlePacket(packet);
                    break;
            }
        }//else discard packet
    }
}
