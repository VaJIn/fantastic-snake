package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketCreator;

import java.net.DatagramPacket;

public class PacketCreatorImpl implements PacketCreator{

    private int idProtocol;
    private int idPlayer;
    private int tokenPlayer;

    public PacketCreatorImpl(int idProtocol, int idPlayer, int tokenPlayer){

        this.idProtocol = idProtocol;
        this.idPlayer = idPlayer;
        this.tokenPlayer = tokenPlayer;
    }

    @Override
    public DatagramPacket getPacket(int type) {

        switch (type){
        }

        return null;
    }
}
