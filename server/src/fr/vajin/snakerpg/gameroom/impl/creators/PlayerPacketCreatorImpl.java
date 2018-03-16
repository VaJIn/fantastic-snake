package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator {

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

    public PlayerPacketCreatorImpl(int idProtocol) {

        this.idProtocol = idProtocol;

        this.respJoinState = new RespJoinState();
        this.waitingForGameState = new WaitingForGameState();
        this.gameStartState = new GameStartState();
        this.gameState = new GameState();
        this.gameEndState = new GameEndState();


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

        return this.currentState.getNextPacket(getPacketStream());

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
    public void setState(PlayerPacketCreatorState playerPacketCreatorState) {
        this.currentState = playerPacketCreatorState;
    }

    public PlayerPacketCreatorState getRespJoinState() {
        return respJoinState;
    }

    public PlayerPacketCreatorState getWaitingForGameState() {
        return waitingForGameState;
    }

    public PlayerPacketCreatorState getGameStartState() {
        return gameStartState;
    }

    public PlayerPacketCreatorState getGameState() {
        return gameState;
    }

    public PlayerPacketCreatorState getGameEndState() {
        return gameEndState;
    }

    @Override
    public void setPlayerHandler(PlayerHandler playerHandler){
        this.playerHandler = playerHandler;
    }

    @Override
    public PlayerHandler getPlayerHandler(){
        return this.playerHandler;
    }
}
