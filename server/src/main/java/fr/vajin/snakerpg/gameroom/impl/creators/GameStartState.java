package fr.vajin.snakerpg.gameroom.impl.creators;

import com.google.gson.Gson;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.jsondatabeans.GameStartBean;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;

public class GameStartState implements PlayerPacketCreator.PlayerPacketCreatorState {

    private PlayerPacketCreator creator;

    public GameStartState(PlayerPacketCreator playerPacketCreator){
        this.creator = playerPacketCreator;
    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {


        stream.writeInt(PlayerPacketCreator.GAME_START);

        GameStartBean gameStartBean = new GameStartBean();
        gameStartBean.setGameMode(creator.getPlayerHandler().getController().currentGameMode());
        gameStartBean.setMap(creator.getPlayerHandler().getController().getMapName());

        Gson gson = new Gson();

        String beanJson = gson.toJson(gameStartBean);

        stream.writeInt(beanJson.length());
        stream.write(beanJson.getBytes());

        byte [] data = stream.toByteArray();



        return new DatagramPacket(data, data.length);
    }
}
