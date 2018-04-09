package fr.vajin.snakerpg.gameroom.impl;

import fr.univangers.vajin.engine.GameEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;

public class GameRun implements Runnable {

    private final static Logger logger = LogManager.getLogger(GameRun.class);

    private GameEngine engine;
    private int ticksPerSecond;

    public GameRun(GameEngine engine, int ticksPerSecond){

        this.engine = engine;
        this.ticksPerSecond = ticksPerSecond;

    }

    @Override
    public void run() {
        try {

            while (!engine.isGameOver()){

                long start = Instant.now().toEpochMilli();

                synchronized (engine){
                    engine.computeTick();
                }

                long end = Instant.now().toEpochMilli();

                long sleepTime = 1000 / ticksPerSecond - (end - start);

                logger.debug("Sleeping for " + sleepTime);

                Thread.sleep(sleepTime);



            }
            logger.debug("Game is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
