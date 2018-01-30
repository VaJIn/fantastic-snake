package fr.univangers.vajin.gamemodel;

import fr.univangers.vajin.gamemodel.utilities.Position;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayerEngine extends AbstractGameEngine implements EntityObserver {

    int lastComputedTick;

    Snake player;

    List<Entity> entityList;

    boolean ended;

    Field field;

    public SinglePlayerEngine(Snake player, List<Entity> entityList, boolean ended, Field field) {
        this.player = player;
        this.entityList = entityList;
        this.ended = ended;
        this.field = field;

        lastComputedTick = -1;
    }

    @Override
    public void computeTick() {
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

            //For every entity that changed, we check for collision with other entities
            for (DynamicEntity de : updatedEntities) {
                List<Position> newPosition = de.getNewPositions();
                for (Position p : newPosition) {
                    //For every new position we check for collision
                    for (Entity ce : entityList) {
                        if (de != ce) {
                            if (ce.coverPosition(p)) {
                                //If there is a collision we let the entity handle it
                                de.handleCollisionWith(ce, p, true);
                                ce.handleCollisionWith(de, p, false);
                            }
                        }
                    }
                }
            }

            if (player.getLifePoint() <= 0) {
                this.notifyOfRemovedEntity(player);
                this.ended = true;
                this.notifyOfGameEnd();
            }
        }
    }

    boolean doesAnEntityCoverPosition(Position position) {
        for (Entity e : entityList) {
            if (e.coverPosition(position)) {
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
    public void sendInput(int player, int input) {
        if (player != 0) {
            throw new IllegalArgumentException("Player " + player + " does not exist");
        }

        this.player.sendAction(input);

    }
}
