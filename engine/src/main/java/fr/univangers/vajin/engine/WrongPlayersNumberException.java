package fr.univangers.vajin.engine;

public class WrongPlayersNumberException extends Exception{

    private int expectedMin;
    private int expectedMax;
    private int actualNumber;

    public WrongPlayersNumberException(int expectedMin, int expectedMax, int actualNumber){
        this.expectedMax = expectedMax;
        this.expectedMin = expectedMin;
        this.actualNumber = actualNumber;
    }

    @Override
    public String getMessage() {
        return "The engine cannot be launched because its number of players ("+actualNumber+") is not in the range ["+expectedMin+"" +
                ","+expectedMax+"]";
    }
}
