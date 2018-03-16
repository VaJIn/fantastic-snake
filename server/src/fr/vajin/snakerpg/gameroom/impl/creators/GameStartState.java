package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.univangers.vajin.engine.entities.Entity;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Iterator;

public class GameStartState implements PlayerPacketCreator.PlayerPacketCreatorState {

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {
        byte[] data;

        stream.writeInt(PlayerPacketCreator.GAME_START);


        data = stream.toByteArray();



        return new DatagramPacket(data, data.length);
    }
}
