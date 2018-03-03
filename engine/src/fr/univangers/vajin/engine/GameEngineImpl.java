package fr.univangers.vajin.engine;

import fr.univangers.vajin.engine.entities.DynamicEntity;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.EntityObserver;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommand;
import fr.univangers.vajin.engine.entities.spawnables.bonus.BonusTarget;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.utilities.Direction;
import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.engine.utilities.RandomNumberGenerator;

import java.util.*;

public class GameEngineImpl extends AbstractGameEngine implements EntityObserver {

    private int lastComputedTick;

    private Map<Integer, Snake> players;

    private Collection<Entity> entityCollection;

    private boolean ended;

    private Field field;

    private Collection<Entity> toDispose;

    private PriorityQueue<TimedCommand> timedCommands;

    public GameEngineImpl(Map<Integer, Snake> players, Collection<Entity> entityCollection, Field field) {
        this.players = new HashMap<>(players);
        this.field = field;

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

        this.timedCommands = new PriorityQueue<>();

        lastComputedTick = -1;
    }



    private void initSnakePositions(){

        List<Position> alreadyAssignedPositions = new ArrayList<>();
        RandomNumberGenerator randGen = new RandomNumberGenerator();

        for (int i=0; i<players.size(); i++){

            boolean currentSnakeAssigned = false;

            while (!currentSnakeAssigned){

                currentSnakeAssigned = true;


                //Drawing the direction
                int dir = randGen.inRange(0, 3);
                Direction d;

                System.out.println("dir :"+dir);

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
                        currentSnakeAssigned = false;
                        break;

                    }
                }

                if (!currentSnakeAssigned){
                    positions.clear();
                }
                else{
                    players.get(i).setInitialPosition(positions, d);
                }

                alreadyAssignedPositions.addAll(positions);
            }


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


            //Ending bonuses if necessary
            while (!timedCommands.isEmpty() && tick == timedCommands.peek().getTick()){
                System.out.println("Applying end of a bonus on tick : "+getCurrentTick());
                timedCommands.poll().apply();
            }


            List<DynamicEntity> updatedEntities = new ArrayList<>();

            //Call every entity to compute their moves
            for (Entity e : entityCollection) {
                if (e instanceof DynamicEntity) {
                    DynamicEntity de = (DynamicEntity) e;
                    if (de.computeTick(tick)) {
                        //If there is a change on the entity, we had it on the list
                        updatedEntities.add(de);
                    }
                }
            }

            //For each entity that changed, we check for collision with other entities
            for (DynamicEntity de : updatedEntities) {
                List<Position> newPosition = de.getNewPositions();
                for (Position p : newPosition) {
                    //For every new position we check for collision
                    for (Entity ce : entityCollection) {
                        if (de != ce) {
                            if (ce.coversPosition(p)) {
                                //If there is a collision we let the entity handle it
                                System.out.println("[Tick " + tick + "] Collision at " + p);
                                de.handleCollisionWith(ce, p, true);
                                ce.handleCollisionWith(de, p, false);
                            }
                        }
                    }
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
        return Collections.unmodifiableCollection(entityCollection);
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

    @Override
    public void addBonusTimedCommand(TimedCommand timedCommand) {

        if (timedCommand.getTick()>getCurrentTick()){
            timedCommands.add(timedCommand);
            System.out.println("Adding timedCommand for tick : "+ timedCommand.getTick()+ " current : "+getCurrentTick());
        }
        else{
            System.out.println("Impossible d'ajouter le bonus end");
        }

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
