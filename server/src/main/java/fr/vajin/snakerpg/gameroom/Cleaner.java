package fr.vajin.snakerpg.gameroom;

import java.time.Instant;
import java.util.Collection;

/**
 * Fait le nettoyage des PlayerHandler (vérifie connection toujours active, si pas active supprime toutes traces de leur existence.
 */
public class Cleaner extends Thread {

    private Collection<PlayerHandler> playerHandlers;

    @Override
    public void run(){



        try {
            while(!this.isInterrupted()){

                synchronized (playerHandlers) {

                    for(PlayerHandler playerHandler : playerHandlers){

                        long moment = Instant.now().toEpochMilli();

                        if((moment-playerHandler.getLastAliveSignalReceived()/1000)>=30){

                            //TODO supprimer connection


                        }
                    }

                }

                sleep(1);


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

}
