package fr.univangers.vajin.gamemodel;

public interface GameEngineObserver {

    void notifyNewMutableObject(MutableObject object);

    void notifyGameEnd();

}
