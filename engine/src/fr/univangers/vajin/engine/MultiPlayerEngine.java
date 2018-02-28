package fr.univangers.vajin.engine;

import com.google.common.collect.ImmutableList;
import fr.univangers.vajin.engine.entities.DynamicEntity;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.EntityObserver;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.utilities.Direction;
import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.engine.utilities.RandomNumberGenerator;

import java.util.*;

public class MultiPlayerEngine extends AbstractGameEngine implements EntityObserver {

    private int lastComputedTick;

    private Map<Integer, Snake> players;

    private List<Entity> entityList;

    private boolean ended;

    private Field field;
    private List<Entity> toDispose;

    public MultiPlayerEngine(Map<Integer, Snake> players, List<Entity> entityList, Field field) {
        this.players = new HashMap<>(players);
        this.field = field;

        initSnakePositions();

        //Setting a reference to the engine in each Snake
        players.forEach( (id, player) -> player.setEngine(this));

        this.entityList = new ArrayList<>(entityList);
        this.toDispose = new LinkedList<>();
        this.entityList.addAll(players.values());
        this.ended = false;

        for (Entity e : entityList) {
            e.setEngine(this);
        }

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

                    System.out.println("xpos : "+pos.getX());
                    System.out.println("ypos : "+pos.getY());

                    if ( !field.containsPos(pos) || !field.getFieldUnits(pos).isWalkable() || alreadyAssignedPositions.contains(pos)){
                        currentSnakeAssigned = false;
                        break;

                    }

                }

                System.out.println("w : "+field.getWidth());
                System.out.println("h : "+field.getHeight());

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
            entityList.removeAll(toDispose);
            toDispose = new LinkedList<>();
        }

        if (!this.ended) {

            int tick = lastComputedTick + 1;
            List<DynamicEntity> updatedEntities = new ArrayList<>();

            //Call every entity to compute their moves
            for (Entity e : entityList) {
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
                    for (Entity ce : entityList) {
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
        for (Entity e : entityList) {
            if (e.coversPosition(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Entity> getEntityList() {
        return entityList;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public void notifyDestroyed(Entity entity) {
        entityList.remove(entity);
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
    public int getPlayerScore(int playerId) {
        if (!players.containsKey(playerId)) {
            throw new IllegalArgumentException("Player " + playerId + " does not exist");
        }

        return players.get(playerId).getSize();
    }
}
