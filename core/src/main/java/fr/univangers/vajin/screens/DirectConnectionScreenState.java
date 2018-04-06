package fr.univangers.vajin.screens;

import java.net.InetAddress;

public interface DirectConnectionScreenState {

    void connectTo(InetAddress address, int port);

    void acceptedConnection(InetAddress address, int port);

    void refusedConnection(InetAddress address, int port);
}
