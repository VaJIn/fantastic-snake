package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;

public class RespJoinState implements PlayerPacketCreator.PlayerPacketCreatorState {

    private PlayerPacketCreator creator;

    public RespJoinState(PlayerPacketCreator creator){
        this.creator = creator;
    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {


        stream.writeInt(PlayerPacketCreator.RESP_JOIN);

        stream.writeInt(1);

        byte [] data = stream.toByteArray();

        return new DatagramPacket(data, data.length);
    }
}
