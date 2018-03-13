package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;

import java.net.DatagramPacket;

public class PlayerPacketHandlerImpl implements PlayerPacketHandler {

    private PlayerPacketCreator packetCreator;
    private PlayerTransmiter playerTransmiter;

    public PlayerPacketHandlerImpl(PlayerPacketCreator packetCreator, PlayerTransmiter playerTransmiter) {
        this.packetCreator = packetCreator;
        this.playerTransmiter = playerTransmiter;
    }

    @Override
    public boolean handleDatagramPacket(DatagramPacket datagramPacket) {
        return false;
    }
}
