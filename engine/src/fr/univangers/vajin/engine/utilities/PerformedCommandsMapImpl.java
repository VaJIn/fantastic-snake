package fr.univangers.vajin.engine.utilities;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommand;
import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommandImpl;

import java.sql.Time;
import java.util.*;


/**
 * Stores the performed commands depending on their tick
 * Has a maximum capacity, so forgets the older commands if the capacity is reached while adding a command to a new tick
 */
public class PerformedCommandsMapImpl implements PerformedCommandMap {

    private Multimap<Integer, TimedCommand> map;
    private Deque<Integer> containedKeys;
    private int capacity;


    public PerformedCommandsMapImpl(int capacity) {
        this.capacity = capacity;
        this.containedKeys = new LinkedList<>();
        this.map = ArrayListMultimap.create();
    }

    /**zdzdzzz
     * Adds the command to the list mapped on the tick
     * If the capacity is reached, forgetting the commands of the older tick
     *
     * @param command
     */
    public void put(TimedCommand command) {

        //Checking if a new key must be created and if the capacity has been reached
        if (!map.containsKey(command.getTick()) && map.keySet().size() == capacity) {

            int tickToRemove = containedKeys.pop();

            //Removing the older tick's values
            map.removeAll(tickToRemove);

            //Recording that a new key has been inserted
            containedKeys.add(command.getTick());
        }
        else if (!map.containsKey(command.getTick())){
            containedKeys.add(command.getTick());
        }
        map.put(command.getTick(), command);

    }

    /**
     * Returns a collection of the commands at the specified tick. Returning an empty list if no command has been found
     *
     * @param tick
     * @return
     */
    public List<TimedCommand> get(int tick) {
        return new ArrayList<>(map.get(tick));
    }

    /**
     * Returns true if the map contains the TimedBonus with the given ID
     *
     * @param timedBonusId
     * @return
     */
    public boolean containsTimedBonus(int timedBonusId) {

        for (TimedCommand cmd : map.values()){
            if (cmd.getId()==timedBonusId){
                return true;
            }
        }

        return false;
    }

    @Override
    public Collection<TimedCommand> allCommandsAsList(){
        return map.values();
    }

    @Override
    public int numberOfCommands(int id) {

        int val = 0;

        for (TimedCommand cmd : allCommandsAsList()){
            if (cmd.getId()==id){
                val++;
            }
        }

        return val;

    }

    @Override
    public int removeCommands(int id) {

        int removedCommands = 0;

        for (TimedCommand cmd : map.values()){
            if (cmd.getId()==id){
                map.remove(id, cmd);
                removedCommands++;
            }
        }

        return removedCommands;
    }

    @Override
    public void removeCommand(int id, boolean isReveringTimedBonus) {

        for (TimedCommand cmd : map.values()){

            if (cmd.getId()==id && cmd.isRevertingTimedBonus()==isReveringTimedBonus){
                map.remove(id, cmd);
            }
        }
    }
}
