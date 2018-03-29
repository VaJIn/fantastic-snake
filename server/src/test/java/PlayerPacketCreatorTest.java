import com.google.gson.Gson;
import fr.univangers.vajin.engine.entities.Entity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.impl.creators.PlayerPacketCreatorImpl;
import fr.vajin.snakerpg.jsondatabeans.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PlayerPacketCreatorTest {


    @Test
    void testRespJoinState(){
        PlayerPacketCreatorImpl creator = new PlayerPacketCreatorImpl(PlayerPacketCreator.ID_PROTOCOL);


        creator.setState(creator.getRespJoinState());

        final DatagramPacket[] packet = new DatagramPacket[1];
        Assertions.assertAll(() -> packet[0] = creator.getNextPacket());

        ByteBuffer buffer = ByteBuffer.wrap(packet[0].getData());

        int idProtocol = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.ID_PROTOCOL,idProtocol);

        int numSequence = buffer.getInt();
        Assertions.assertEquals(1,numSequence);
        int lastIdReceived = buffer.getInt();
        Assertions.assertEquals(0,lastIdReceived);
        int ackBitFiels = buffer.getInt();
        Assertions.assertEquals(0,ackBitFiels);

        int id_state = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.RESP_JOIN,id_state);

        int join_success = buffer.getInt();
        Assertions.assertEquals(1,join_success);


    }

    @Test
    void testGameState() {
        PlayerPacketCreatorImpl creator = new PlayerPacketCreatorImpl(PlayerPacketCreator.ID_PROTOCOL);

        creator.setState(creator.getGameState());
        final DatagramPacket[] packet = new DatagramPacket[1];
        Assertions.assertAll(() -> packet[0] = creator.getNextPacket());

        ByteBuffer buffer = ByteBuffer.wrap(packet[0].getData());

        int idProtocol = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.ID_PROTOCOL,idProtocol);

        int numSequence = buffer.getInt();
        Assertions.assertEquals(1,numSequence);
        int lastIdReceived = buffer.getInt();
        Assertions.assertEquals(0,lastIdReceived);
        int ackBitFiels = buffer.getInt();
        Assertions.assertEquals(0,ackBitFiels);

        int idState = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.GAME,idState);

        for(Entity entity : creator.getPlayerHandler().getController().getCurrentEngine().getEntities()){

            Iterator<? extends Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();
            int entityId = buffer.getInt();
            Assertions.assertEquals(entity.getEntityId(),entityId);
            while (it.hasNext()){
                int tileInfoId = buffer.getInt();
                int tileInfoPosX = buffer.getInt();
                int tileInfoPosY = buffer.getInt();

                Entity.EntityTileInfo tileInfo = it.next();

                Assertions.assertEquals(tileInfo.getId(),tileInfoId);
                Assertions.assertEquals(tileInfo.getPosition().getX(),tileInfoPosX);
                Assertions.assertEquals(tileInfo.getPosition().getY(),tileInfoPosY);

                int byteLength = buffer.getInt();
                byte [] resourceKeyBytes = new byte[byteLength];

                buffer.get(resourceKeyBytes);

                Assertions.assertEquals(tileInfo.getResourceKey(),String.valueOf(resourceKeyBytes));
            }

            Assertions.assertEquals(-1,buffer.getInt());

        }

        Assertions.assertEquals(-1,buffer.getInt());
        
    }

    @Test
    void testGameStartState(){

        PlayerPacketCreatorImpl creator = new PlayerPacketCreatorImpl(PlayerPacketCreator.ID_PROTOCOL);

        creator.setState(creator.getGameStartState());

        final DatagramPacket[] packet = new DatagramPacket[1];
        Assertions.assertAll(() -> packet[0] = creator.getNextPacket());

        ByteBuffer buffer = ByteBuffer.wrap(packet[0].getData());

        int idProtocol = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.ID_PROTOCOL,idProtocol);

        int numSequence = buffer.getInt();
        Assertions.assertEquals(1,numSequence);
        int lastIdReceived = buffer.getInt();
        Assertions.assertEquals(0,lastIdReceived);
        int ackBitFiels = buffer.getInt();
        Assertions.assertEquals(0,ackBitFiels);

        int idState = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.GAME_START,idState);

        int lengthJSON = buffer.getInt();

        byte [] bytesJSON = new byte[lengthJSON];

        buffer.get(bytesJSON);

        Gson gson = new Gson();

        GameStartBean bean = gson.fromJson(String.valueOf(bytesJSON),GameStartBean.class);

        Assertions.assertNotNull(bean);

        Assertions.assertEquals(creator.getPlayerHandler().getController().currentGameMode().getId(),bean.getGameMode().getId());
        Assertions.assertEquals(creator.getPlayerHandler().getController().getMapRessource(),bean.getMap());

    }

    @Test
    void testGameEndState(){
        PlayerPacketCreatorImpl creator = new PlayerPacketCreatorImpl(PlayerPacketCreator.ID_PROTOCOL);

        creator.setState(creator.getGameEndState());

        final DatagramPacket[] packet = new DatagramPacket[1];
        Assertions.assertAll(() -> packet[0] = creator.getNextPacket());

        ByteBuffer buffer = ByteBuffer.wrap(packet[0].getData());

        int idProtocol = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.ID_PROTOCOL,idProtocol);

        int numSequence = buffer.getInt();
        Assertions.assertEquals(1,numSequence);
        int lastIdReceived = buffer.getInt();
        Assertions.assertEquals(0,lastIdReceived);
        int ackBitFiels = buffer.getInt();
        Assertions.assertEquals(0,ackBitFiels);

        int idState = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.GAME_END,idState);

        int jsonSize = buffer.getInt();

        byte [] jsonData = new byte[jsonSize];

        buffer.get(jsonData);

        Gson gson = new Gson();

        GameEndBean gameEndBean = gson.fromJson(String.valueOf(jsonData),GameEndBean.class);

        Assertions.assertNotNull(gameEndBean);

        Collection<GameParticipationEntity> gameResults = creator.getPlayerHandler().getController().getLastGameResults();
        Assertions.assertEquals(creator.getPlayerHandler().getController().getLastGameResults().size(),gameEndBean.getGameParticipations().size());

        for(int i=0;i<gameEndBean.getGameParticipations().size();i++){

            GameParticipationEntity entity = ((ArrayList<GameParticipationEntity>) gameResults).get(i);
            GameParticipationBean bean = ((ArrayList<GameParticipationBean>) gameEndBean.getGameParticipations()).get(i);

            Assertions.assertEquals(entity.getDeathCount(),bean.getDeathCount());
            Assertions.assertEquals(entity.getKillCount(),bean.getKillCount());
            Assertions.assertEquals(entity.getUser().getAlias(),bean.getPlayer().getAlias());
            Assertions.assertEquals(entity.getUser().getId(),bean.getPlayer().getLocalId());

            //TODO test sur le snake id

        }
    }

    @Test
    void testWaitingForGameState(){
        PlayerPacketCreatorImpl creator = new PlayerPacketCreatorImpl(PlayerPacketCreator.ID_PROTOCOL);

        creator.setState(creator.getWaitingForGameState());

        final DatagramPacket[] packet = new DatagramPacket[1];
        Assertions.assertAll(() -> packet[0] = creator.getNextPacket());

        ByteBuffer buffer = ByteBuffer.wrap(packet[0].getData());

        int idProtocol = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.ID_PROTOCOL,idProtocol);

        int numSequence = buffer.getInt();
        Assertions.assertEquals(1,numSequence);
        int lastIdReceived = buffer.getInt();
        Assertions.assertEquals(0,lastIdReceived);
        int ackBitFiels = buffer.getInt();
        Assertions.assertEquals(0,ackBitFiels);

        int idState = buffer.getInt();
        Assertions.assertEquals(PlayerPacketCreator.GAMEROOM_DESC,idState);

        int jsonSize = buffer.getInt();

        byte [] jsonData = new byte[jsonSize];

        buffer.get(jsonData);

        Gson gson = new Gson();

        LobbyBean bean = gson.fromJson(String.valueOf(jsonData),LobbyBean.class);

        Assertions.assertEquals(creator.getPlayerHandler().getController().currentGameMode().getId(),bean.getGameMode().getId());
        Assertions.assertEquals(creator.getPlayerHandler().getController().getMapRessource(),bean.getMap());

        Collection<PlayerBean> players = bean.getPlayers();
        Collection<PlayerHandler> users = creator.getPlayerHandler().getController().getPlayerHandlers();

        Assertions.assertEquals(users.size(),players.size());

        for(int i=0;i<players.size();i++){

            PlayerBean player = ((ArrayList<PlayerBean>)players).get(i);
            UserEntity entity= ((ArrayList<PlayerHandler>)users).get(i).getUserEntity();

            Assertions.assertEquals(entity.getId(),player.getLocalId());
            Assertions.assertEquals(entity.getAlias(),player.getAlias());

            //TODO test sur le snake id

        }


    }

}
