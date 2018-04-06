package fr.vajin.snakerpg.gameroom.impl;

import fr.univangers.vajin.engine.GameEngine;

import java.time.Instant;

public class GameRun implements Runnable {

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

                Thread.sleep(1000/ticksPerSecond - end - start);


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
