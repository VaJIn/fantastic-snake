package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;

public class GameEndState implements PlayerPacketCreator.PlayerPacketCreatorState{

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {
        byte[] data;

        stream.writeInt(PlayerPacketCreator.GAME_END);


        data = stream.toByteArray();

        return new DatagramPacket(data, data.length);
    }
}
