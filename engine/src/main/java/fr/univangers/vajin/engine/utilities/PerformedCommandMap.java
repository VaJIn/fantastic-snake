package fr.univangers.vajin.engine.utilities;

import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommand;

import java.util.Collection;
import java.util.List;

public interface PerformedCommandMap {

    /**
     * Adds the command to the map on the key given by the inner T
     *
     * @param command
     */
    void put(TimedCommand command);

    /**
     * Returns a collection of the Timed commands at the specified tick
     *
     * @param tick
     * @return
     */
    List<TimedCommand> get(int tick);

    /**
     * Returns true if the map contains the Timed command with the given ID
     *
     * @param timedBonusId
     * @return
     */
    boolean containsTimedBonus(int timedBonusId);


    /**
     * Returns all the contained command as a list
     * @return
     */
    Collection<TimedCommand> allCommandsAsList();


    /**
     * Returns the number of contained commands with the given ID
     * @param id
     * @return
     */
    int numberOfCommands(int id);


    /**
     * Deletes all the commands with the given ID
     * @param id
     * @return the number of removed commands
     */
    int removeCommands(int id);

    /**
     * Removes a single command with the given ID
     * The boolean indicates if that command that must be removed is a bonus or its cancelling timed command
     * @param id
     * @param isReveringTimedBonus
     */
    void removeCommand(int id, boolean isReveringTimedBonus);


}
