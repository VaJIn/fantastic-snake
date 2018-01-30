package fr.univangers.vajin.gamemodel;

import java.util.List;

public interface GameEngine {


    List<Entity> getEntityList();

    Field getField();

    void computeTick();

    void sendInput(int player, int input);

    void addGameEngineObserver(GameEngineObserver observer);

    void removeGameEngineObserver(GameEngineObserver observer);



}
