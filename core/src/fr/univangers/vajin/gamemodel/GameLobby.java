package fr.univangers.vajin.gamemodel;

import fr.univangers.vrjlpv.snakerpg.server.network.NetworkFacade;

import java.util.List;

public interface GameLobby {
    List<User> getUser();

    GameEngine getEngine();

    NetworkFacade getFacade();
}
