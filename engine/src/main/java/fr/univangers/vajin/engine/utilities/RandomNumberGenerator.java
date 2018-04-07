package fr.univangers.vajin.engine.utilities;

import java.util.Random;

public class RandomNumberGenerator {

    Random random;

    public RandomNumberGenerator(){
        random = new Random();
    }

    public int inRange(int a, int b){
        return random.nextInt((b - a) + 1) + a;
    }






}
