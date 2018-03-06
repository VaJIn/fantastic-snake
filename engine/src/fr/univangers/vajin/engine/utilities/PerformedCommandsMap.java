package fr.univangers.vajin.engine.utilities;

import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommand;

import java.util.*;


/**
 * Stores the performed commands depending on their tick
 * Has a maximum capacity, so forgets the older commands if the capacity is reached while adding a command to a new tick
 */
public class PerformedCommandsMap {

    private Map<Integer, List<TimedCommand>> map;
    private Deque<Integer> containedKeys;
    private int capacity;


    public PerformedCommandsMap(int capacity){
        this.capacity = capacity;
        this.containedKeys = new LinkedList<>();
        this.map = new HashMap<>();
    }

    /**
     * Adds the command to the list mapped on the tick
     * If the capacity is reached, forgetting the commands of the older tick
     * @param command
     */
    public void put(TimedCommand command){

        //Checking if the map already contains the key
        if (map.containsKey(command.getTick())){
            map.get(command.getTick()).add(0 ,command); //Adding the command at first position of the proper list
        }
        else{
            //No list has been created to store the TimedCommand at the specified tick

            //If the capacity is reached, deleting the first inserted pair (tick, List<TimedCommand))
            if (map.size()==capacity){
                map.remove(containedKeys.pop());
            }

            //Creating the list and adding the TimedCommand
            ArrayList<TimedCommand> newList = new ArrayList<>();
            newList.add(command);

            //Adding the list to the map
            map.put(command.getTick(), newList);

            //Recording that the key has been creating
            containedKeys.add(command.getTick());
        }
    }

    /**
     * Returns the list of commands at the specified tick. Returning an empty list if no command has been found
     * @param tick
     * @return
     */
    public List<TimedCommand> get(int tick){

        if (map.containsKey(tick)){
            return map.get(tick);
        }
        else{
            return new ArrayList<>();
        }

    }



}
