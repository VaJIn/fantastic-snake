package fr.vajin.snakerpg.gameroom;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Fait le nettoyage des PlayerHandler (vérifie connection toujours active, si pas active supprime toutes traces de leur existence.
 */
public class Cleaner extends Thread {

    Controller controller;
    int timeoutTimeMS;

    public Cleaner(Controller controller, int timeoutTimeMS) {
        this.controller = controller;
        this.timeoutTimeMS = timeoutTimeMS;
    }

    @Override
    public void run(){

        try {
            while(!this.isInterrupted()){

                synchronized (controller) {
                    List<PlayerHandler> toRemove = new ArrayList<>();
                    for (PlayerHandler playerHandler : controller.getPlayerHandlers()) {
                        long moment = Instant.now().toEpochMilli();

                        if ((moment - playerHandler.getLastAliveSignalReceived()) >= timeoutTimeMS) {
                            playerHandler.stopTransmiter();
                            toRemove.add(playerHandler);
                        }
                    }

                    for (PlayerHandler playerHandler : toRemove) {
                        controller.removePlayer(playerHandler);
                    }
                }
                sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
