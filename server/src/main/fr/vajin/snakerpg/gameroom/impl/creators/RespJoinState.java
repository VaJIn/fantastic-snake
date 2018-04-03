package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Logger;

public class RespJoinState implements PlayerPacketCreator.PlayerPacketCreatorState {

    private final static Logger logger = Logger.getLogger(RespJoinState.class.toString());

    private PlayerPacketCreator creator;

    public RespJoinState(PlayerPacketCreator creator){
        this.creator = creator;
    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {


        stream.writeInt(PlayerPacketCreator.RESP_JOIN);

        stream.writeInt(1);

        stream.writeInt(this.creator.getPlayerHandler().getUserId());
        stream.writeInt(this.creator.getPlayerHandler().getUserToken());

        byte [] data = stream.toByteArray();

        StringBuilder stringBuilderInfo = new StringBuilder();
        for(int i = 0; i < data.length; i += 4){
            stringBuilderInfo.append(String.format("%02X%02X%02X%02X ", data[i], data[i + 1], data[i + 2], data[i + 3]));
        }

        logger.info(stringBuilderInfo.toString());


        return new DatagramPacket(data, data.length);
    }
}
