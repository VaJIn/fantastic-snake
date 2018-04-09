package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.impl.handlers.PlayerReadyPacketHandlerImpl;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator {

    private final Logger logger = LogManager.getLogger(PlayerReadyPacketHandlerImpl.class);


    private final PlayerPacketCreatorState respJoinState;
    private final PlayerPacketCreatorState waitingForGameState;
    private final PlayerPacketCreatorState gameStartState;
    private final PlayerPacketCreatorState gameState;
    private final PlayerPacketCreatorState gameEndState;

    private PlayerPacketCreatorState currentState;

    private PlayerHandler playerHandler;

    private final int idProtocol;
    private int lastIdReceived = 0;
    private int ackBitfield = 0;

    private int numSequence = 0;

    public PlayerPacketCreatorImpl(int idProtocol, PlayerHandler playerHandler) {

        this.playerHandler = playerHandler;

        this.idProtocol = idProtocol;

        this.respJoinState = new RespJoinState(this);
        this.waitingForGameState = new WaitingForGameState(this);
        this.gameStartState = new GameStartState(this);
        this.gameState = new GameState(this);
        this.gameEndState = new GameEndState(this);

        this.currentState = respJoinState;

    }

    CustomByteArrayOutputStream getPacketStream() throws IOException {
        CustomByteArrayOutputStream stream = new CustomByteArrayOutputStream();

        numSequence++;

        stream.writeInt(idProtocol);

        stream.writeInt(numSequence);

        stream.writeInt(this.lastIdReceived);

        stream.writeInt(ackBitfield);

        return stream;
    }

    @Override
    public DatagramPacket getNextPacket() throws IOException {

        DatagramPacket packet = this.currentState.getNextPacket(getPacketStream());
        return packet;
    }

    @Override
    public void acknowledgePacket(int idReceived) {

        if (idReceived > this.lastIdReceived) {
            int predId = this.lastIdReceived;
            this.ackBitfield = this.ackBitfield >>> (idReceived - lastIdReceived);
            this.lastIdReceived = idReceived;
            this.acknowledgePacket(predId);
        } else if (idReceived < this.lastIdReceived && idReceived >= this.lastIdReceived - 32) {
            int mask = 0x80000000;
            mask = mask >>> (this.lastIdReceived - idReceived - 1);
            this.ackBitfield = this.ackBitfield | mask;
        }
    }

    @Override
    public void setState(int state) {
        logger.debug("Set state : " + state);
        switch(state){
            case PlayerPacketCreator.RESP_JOIN_STATE:
                logger.debug("Setting state of" + playerHandler.getUserId() + " to RESP_JOIN_STATE");
                this.currentState = respJoinState;
                break;
            case PlayerPacketCreator.WAITING_FOR_GAME_STATE:
                logger.debug("Setting state of" + playerHandler.getUserId() + "to WAITING_FOR_GAME_STATE");
                this.currentState = waitingForGameState;
                break;
            case PlayerPacketCreator.GAME_START_STATE:
                logger.debug("Setting state of" + playerHandler.getUserId() + " to GAME_START_STATE");
                this.currentState = gameStartState;
                break;
            case PlayerPacketCreator.GAME_END_STATE:
                logger.debug("Setting state of" + playerHandler.getUserId() + " to GAME_END_STATE");
                this.currentState = gameEndState;
                break;
            case PlayerPacketCreator.GAME_STATE:
                logger.debug("Setting state of" + playerHandler.getUserId() + " to GAME_STATE");
                this.currentState = gameState;
                break;
        }
    }

    @Override
    public void setPlayerHandler(PlayerHandler playerHandler){
        this.playerHandler = playerHandler;
    }

    @Override
    public PlayerHandler getPlayerHandler(){
        return this.playerHandler;
    }

    @Override
    public void startGame() {

        sendPacket(gameStartState);
    }

    @Override
    public void endGame() {

        sendPacket(gameEndState);

    }

    private void sendPacket(PlayerPacketCreatorState state){
        try{

            CustomByteArrayOutputStream stream = this.getPacketStream();
            DatagramPacket packet = state.getNextPacket(stream);

            this.playerHandler.getPlayerTransmitter().send(packet);


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Integer getState() {

        if (currentState==respJoinState){
            return PlayerPacketCreator.RESP_JOIN_STATE;
        }
        else if (currentState==waitingForGameState){
            return PlayerPacketCreator.WAITING_FOR_GAME_STATE;
        }
        else if (currentState==gameState){
            return PlayerPacketCreator.GAME_STATE;
        }
        else if (currentState==gameStartState){
            return PlayerPacketCreator.GAME_START_STATE;
        }
        else if (currentState==gameEndState){
            return PlayerPacketCreator.GAME_END_STATE;
        }
        else{
            return null;
        }

    }
}
