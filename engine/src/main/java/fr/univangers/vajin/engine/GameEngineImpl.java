package fr.univangers.vajin.engine;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import fr.univangers.vajin.engine.entities.DynamicEntity;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.EntityObserver;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.entities.spawnables.bonus.EmptyTimedCommandImpl;
import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommand;
import fr.univangers.vajin.engine.entities.spawnables.bonus.BonusTarget;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.utilities.Direction;
import fr.univangers.vajin.engine.utilities.PerformedCommandsMapImpl;
import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.engine.utilities.RandomNumberGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class GameEngineImpl extends AbstractGameEngine implements EntityObserver {


    private final static Logger logger = LogManager.getLogger(GameEngineImpl.class);
    /**
     * Number of ticks the time machine goes in the past
     */
    private final int TIME_MACHINE_DURATION = 150;

    private int lastComputedTick;

    private Map<Integer, Snake> players;

    private Collection<Entity> entityCollection;

    private boolean ended;

    private Field field;

    private Collection<Entity> toDispose;

    private boolean isTimeMachineActive;

    private PerformedCommandsMapImpl performedCommandsMap;


    /**
     * Contains the commands concerning the effects of the bonuses sorted by priority (application tick)
     */
    private PriorityQueue<TimedCommand> timedBonusCommands;

    /**
     * Contains the commands concerning the movement of a snake sorted by priority (application tick)
     */
    private PriorityQueue<TimedCommand> timedMovementCommands;

    /**
     * Integer corresponding to the last tick the time machine has been in the past
     */
    private int lastTickTimeMachine;

    /**
     * Integer corresponding to the tick the time machine must stop going in the past
     */
    private int stoppingTickTimeMachine;



    public GameEngineImpl(Map<Integer, Snake> players, Collection<Entity> entityCollection, Field field) {
        this.players = new HashMap<>(players);
        this.field = field;
        logger.debug(players.size() + " players");

        initSnakePositions();

        //Setting a reference to the engine in each Snake
        players.forEach( (id, player) -> player.setEngine(this));


        this.entityCollection = new ArrayList<>(entityCollection);
        this.toDispose = new LinkedList<>();
        this.entityCollection.addAll(players.values());
        this.ended = false;

        for (Entity e : entityCollection) {
            e.setEngine(this);
        }

        this.timedBonusCommands = new PriorityQueue<>();

        this.timedMovementCommands = new PriorityQueue<>();

        this.lastComputedTick = -1;

        this.isTimeMachineActive = false;

        this.performedCommandsMap = new PerformedCommandsMapImpl(TIME_MACHINE_DURATION);

    }



    private void initSnakePositions(){

        logger.debug("Init snake position");

        List<Position> alreadyAssignedPositions = new ArrayList<>();
        RandomNumberGenerator randGen = new RandomNumberGenerator();

        for (int i=0; i<players.size(); i++){
            boolean currentSnakeAssigned = false;
            int k = 0;
            while (!currentSnakeAssigned){
                logger.debug("Player" + i + " - Iteration " + k++);
                currentSnakeAssigned = true;
                //Drawing the direction
                int dir = randGen.inRange(0, 3);
                Direction d;
//                System.out.println("dir :"+dir);
                switch (dir){
                    case 0:
                        d = Direction.NORTH;
                        break;
                    case 1:
                        d = Direction.EAST;
                        break;
                    case 2:
                        d = Direction.SOUTH;
                        break;
                    case 3:
                        d = Direction.WEST;
                        break;
                    default:
                        d = null; //Should never happen
                        break;
                }
                //Drawing the first position
                List<Position> positions = new ArrayList<>();

                positions.add(new Position(randGen.inRange(0, field.getWidth()-1), randGen.inRange(0, field.getHeight()-1)));

                //Deducting the others positions
                for (int j=1; j<3; j++){
                    positions.add(positions.get(j-1).nextPosition(d, 1));
                }

                //Checking that the position is actually playable
                for (int j = 0; j < 5; j++) {

                    Position pos = positions.get(0).nextPosition(d, j);
                    if ( !field.containsPos(pos) || !field.getFieldUnits(pos).isWalkable() || alreadyAssignedPositions.contains(pos)){
                        logger.debug("Position " + pos + " not playable");
                        currentSnakeAssigned = false;
                        break;
                    }
                }
                if (!currentSnakeAssigned){
                    logger.debug("Position cleared");
                    positions.clear();
                }
                else{
                    logger.debug("Setting initial pos for player " + i);
                    Snake snake = players.get(i);
                    logger.debug("lol");
                    snake.setInitialPosition(positions, d);
                    logger.debug("Done");
                }
                alreadyAssignedPositions.addAll(positions);
                logger.debug("Done");
            }
            logger.debug("Player " + i + " assigned");
        }
    }

    @Override
    public boolean isGameOver() {

        int nbSnakeAlive = 0;

        for (Map.Entry<Integer, Snake> e : players.entrySet()){
            if (e.getValue().getLifePoint()>0){
                nbSnakeAlive++;
            }
        }

        return nbSnakeAlive<1;

    }

    @Override
    public void computeTick() {

        if (!toDispose.isEmpty()) {
            entityCollection.removeAll(toDispose);
            toDispose = new LinkedList<>();
        }

        if (!this.ended) {

            int tick = lastComputedTick + 1;

            List<DynamicEntity> updatedEntities = new ArrayList<>();


            if (isTimeMachineActive){


                //Reaching the commands of the past at the currently performed tick
                List<TimedCommand> commands = performedCommandsMap.get(lastTickTimeMachine);

                //Canceling all the commands
                for (TimedCommand command : Lists.reverse(commands)){
                    command.cancel();
                }

                //If the last tick in the past has been reached, aborting the time machine
                if (lastTickTimeMachine==stoppingTickTimeMachine){
                    endTimeMachine();
                }

                lastTickTimeMachine--;
            }
            else {


                //Adding a empty timed command to each tick, so that there is at least one command for each tick in the
                //performed commands map
                TimedCommand emptyCommand = new EmptyTimedCommandImpl(tick);
                performedCommandsMap.put(emptyCommand);

                //Call every entity to compute their moves
                for (Entity e : entityCollection) {
                    if (e instanceof DynamicEntity) {
                        DynamicEntity de = (DynamicEntity) e;
                        if (de.computeTick(tick)) {
                            //If there is a change on the entity, we add it on the list
                            updatedEntities.add(de);
                        }
                    }
                }


                //Applying all the commands coming from the snake to perform their movements
                while (!timedMovementCommands.isEmpty() && tick == timedMovementCommands.peek().getTick()) {
                    TimedCommand command = timedMovementCommands.poll();
                    command.apply();
                    performedCommandsMap.put(command);
                }

                //For each entity that changed, we check for collision with other entities
                //No collision search if the time machine is on
                for (DynamicEntity de : updatedEntities) {
                    List<Position> newPosition = de.getNewPositions();
                    if (!newPosition.isEmpty())
                        for (Position p : newPosition) {
                            //For every new position we check for collision
                            for (Entity ce : entityCollection) {
                                if (de != ce) {
                                    if (ce.coversPosition(p)) {
                                        //If there is a collision we let the entity handle it
                                        de.handleCollisionWith(ce, p, true);
                                        ce.handleCollisionWith(de, p, false);
                                    }
                                }
                            }
                        }
                }

                //Applying all the commands coming from the bonuses
                while (!timedBonusCommands.isEmpty() && tick == timedBonusCommands.peek().getTick()) {
                    TimedCommand command = timedBonusCommands.poll();
                    command.apply();
                    performedCommandsMap.put(command);
                }
            }

            //Recording that a snake just died
            players.forEach( ((id, snake) -> {
                if (snake.getLifePoint() <= 0){
                    notifyOfRemovedEntity(snake);
                }
            }));


            if (isGameOver()){
                this.ended = true;
                this.notifyOfGameEnd();
                System.out.println("Game is over");
            }

            lastComputedTick = tick;
        }
    }

    @Override
    public boolean doesAnEntityCoverPosition(Position position) {
        for (Entity e : entityCollection) {
            if (e.coversPosition(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Snake> getAffectedSnakes(Snake taker, BonusTarget target) {

        Collection<Snake> out = new ArrayList<>();


        switch(target){
            case TAKER:
                out.add(taker); //Only the taker is affected
                break;
            case EVERYONE:
                out.addAll(players.values()); //All the snakes are affected
                break;
            case EVERYONE_BUT_TAKER:
                players.forEach( (id, snake) -> { //All the snakes but the taker are affected
                    if (!taker.equals(snake)) {
                        out.add(snake);
                    }
                });
                break;
            case ANYONE_BUT_TAKER:
                List<Snake> allSnakesCopy = new ArrayList<>(players.values()); //Copying all the snakes
                allSnakesCopy.remove(taker); //Removing the taker from the list
                Collections.shuffle(allSnakesCopy); //Shuffling the list
                if (!allSnakesCopy.isEmpty()){
                    out.add(allSnakesCopy.get(0)); //Selecting the first element if the list isn't empty
                }
        }

        return out;

    }

    @Override
    public Collection<Entity> getEntityCollection() {
        return ImmutableList.copyOf(entityCollection);
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public void notifyDestroyed(Entity entity) {
        entityCollection.remove(entity);
        this.toDispose.add(entity);
    }

    @Override
    public void notifyStateChange(Entity entity, int what) {
        //Do nothing
    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {
        //Do nothing
    }

    @Override
    public void notifySpriteChange(int id, Position newPosition, String newResource) {
        //Do nothing
    }

    @Override
    public void sendInput(int playerId, int input) {
        if (!players.containsKey(playerId)) {
            throw new IllegalArgumentException("Player " + playerId + " does not exist");
        }

        this.players.get(playerId).sendAction(input);

    }


    /**
     * Adding a timed command concerning the movement of a snake at the specified tick
     * @param timedCommand
     */
    @Override
    public void addMovementTimedCommand(TimedCommand timedCommand) {

        if (timedCommand.getTick()>=getCurrentTick()){
            timedMovementCommands.add(timedCommand);
        }

    }

    /**
     * Adding a timed command concerning the effect of a bonus at the specified tick
     * @param timedCommand
     */
    @Override
    public void addBonusTimedCommand(TimedCommand timedCommand) {

        if (timedCommand.getTick()>=getCurrentTick()){
            timedBonusCommands.add(timedCommand);
            System.out.println("Adding timedCommand for tick : "+ timedCommand.getTick()+ " current : "+getCurrentTick());
            System.out.println("ID : "+timedCommand.getId());
        }
        else{
            System.out.println("Impossible to add the command because it is in the past");
        }

    }

    @Override
    public void launchTimeMachine() {

        System.out.println("Launching time machine");

        isTimeMachineActive = true;

        //Recording the tick the time machine must stop to go in the past
        stoppingTickTimeMachine = lastComputedTick+1-TIME_MACHINE_DURATION;

        System.out.println("Stopping tick time machine = "+stoppingTickTimeMachine);


        //Current tick the time machine is in the past
        lastTickTimeMachine = lastComputedTick+1;

        System.out.println("Starting tick time machine = "+lastTickTimeMachine);


        //Canceling the next movements the snake wants to make and stop listening to the user input
        for (Entity e : entityCollection){
            if (e instanceof Snake){
                ((Snake) e).cancelNextMovements();
                ((Snake) e).setAcceptUserActions(false);
            }
        }


        //Removing all the reverting bonuses that have been taken by the snake after the state the time machine is going back to
        //Delaying the others by the time machine duration (cases ¬B5 and ¬B3 and B3)
        for (TimedCommand command : timedBonusCommands){

            if (performedCommandsMap.containsTimedBonus(command.getId()) //Case ¬B5
                    && command.isRevertingTimedBonus()
                    && performedCommandsMap.numberOfCommands(command.getId())==1){
                System.out.println("Removed command : id="+command.getId());
                timedBonusCommands.remove(command);

            }
            else{ //Case B3 and ¬B3
                System.out.println("Command before time machine found");
                timedBonusCommands.remove(command);
                command.delayOf(TIME_MACHINE_DURATION);
                System.out.println("delayed *1");
                addBonusTimedCommand(command);
            }

        }


        //Managing the bonuses who have been taken before the state the time machine is going back to, but whose associated
        //reverting command belongs to the performed commands map
        //The reverting command needs to stay so that it can be canceled, but also to be delayed of twice the time of
        //the time machine duration (Cases B2 and ¬B2)
        for (TimedCommand cmd : performedCommandsMap.allCommandsAsList()){


            if (cmd.isRevertingTimedBonus() && performedCommandsMap.numberOfCommands(cmd.getId())==1){
                TimedCommand cmdClone = cmd.clone();
                cmdClone.delayOf(2*TIME_MACHINE_DURATION);
                System.out.println("delayed command *2");
                timedBonusCommands.add(cmdClone);
            }

        }


    }

    private void endTimeMachine(){

        System.out.println("ENDING TIME MACHINE");

        //Listening again to the user input
        for (Entity e : entityCollection) {
            if (e instanceof Snake) {
                ((Snake) e).setAcceptUserActions(true);
            }
        }

        //updating the boolean
        isTimeMachineActive = false;

    }


    @Override
    public int getCurrentTick() {
        return lastComputedTick+1;
    }

    @Override
    public int getPlayerScore(int playerId) {
        if (!players.containsKey(playerId)) {
            throw new IllegalArgumentException("Player " + playerId + " does not exist");
        }

        return players.get(playerId).getSize();
    }
}
