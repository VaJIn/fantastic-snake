package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.DynamicEntity;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.entities.spawnables.Registry;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.*;

public class BonusSpawner extends DynamicEntity {

    private final Registry registry;
    private Map<Position, Bonus> spawnedBonuses;

    private final int maxSimultaneousBonuses;
    private final int minSimultaneousBonuses;

    private final static int MAX_TRIES = 10;

    private Random randomGenerator;
    private List<Position> newPositions;

    public BonusSpawner(int minBonus, int maxBonus, Registry registry) {
        this.maxSimultaneousBonuses = maxBonus;
        this.minSimultaneousBonuses = minBonus;

        this.spawnedBonuses = new HashMap<>();
        this.randomGenerator = new Random();
        this.registry = registry;
    }

    @Override
    public boolean computeTick(int tick) {
        boolean trySpawn = spawnedBonuses.size() < minSimultaneousBonuses || spawnedBonuses.size() < maxSimultaneousBonuses;
        this.newPositions = new ArrayList<>(1);
        if (trySpawn) {
            Position p = tryToGenerateBonus();
            if (p != null) {
                this.newPositions.add(p);
                return true;
            }
        }
        return false;
    }

    private Position tryToGenerateBonus() {
        Position pos = this.getPositionForBonus();
        if (pos == null) {
            return null;
        } else {
            Bonus newBonus = registry.getRandom();
            this.spawnedBonuses.put(pos, newBonus);
            this.notifyChangeAtPosition(pos, Entity.NEW_COVERED_POSITION);
            this.notifySpriteChange(hashCode(), pos, newBonus.getResourceKey());
            return pos;
        }
    }

    private Position getPositionForBonus() {
        int tries;
        for (int i = 0; i < MAX_TRIES; ++i) {
            int x = randomGenerator.nextInt(this.getEngine().getField().getWidth());
            int y = randomGenerator.nextInt(this.getEngine().getField().getHeight());

            Position pos = new Position(x, y);
//            System.out.println("Try food at " + pos);
            if (!this.getEngine().doesAnEntityCoverPosition(pos) && this.getEngine().getField().getFieldUnits(pos).isBonusSpawnArea()) {
//                System.out.println("Success !!");
                return pos;
            }
        }
        return null;
    }

    @Override
    public boolean isKiller() {
        return false;
    }

    @Override
    public List<Position> getNewPositions() {
        return newPositions;
    }

    @Override
    public boolean coversPosition(Position pos) {
        return spawnedBonuses.containsKey(pos);
    }

    @Override
    public void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean isInitater) {
        if (!isInitater) {
            Bonus struckBonus = spawnedBonuses.get(collisionPosition);
            if (otherObject instanceof Snake) {
                Snake snake = (Snake) otherObject;

                struckBonus.buildCommands(getEngine().getAffectedSnakes(snake, struckBonus.getBonusTarget()), getEngine());

                this.removeBonus(collisionPosition);
            } else {
                //Remove the bonus at this position
                this.removeBonus(collisionPosition);
            }
        }
    }

    private void removeBonus(Position pos) {
        Bonus removed = spawnedBonuses.remove(pos);
        notifySpriteChange(pos.hashCode(), pos, "");
        notifyChangeAtPosition(pos, Entity.ONE_LESS_COVER_ON_POSITION);
    }

    @Override
    public void inflictDamage(int damage) {
        //Should never happen
    }

    @Override
    public void destroy() {
        //Not to be destroyed
    }

    @Override
    public Iterator<EntityTileInfo> getEntityTilesInfosIterator() {
        return new Iterator<EntityTileInfo>() {

            Iterator<Map.Entry<Position, Bonus>> it = spawnedBonuses.entrySet().iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public EntityTileInfo next() {
                Map.Entry<Position, Bonus> entry = it.next();
                return new EntityTileInfo() {
                    Bonus b = entry.getValue();
                    Position p = entry.getKey();

                    @Override
                    public String getResourceKey() {
                        return b.getResourceKey();
                    }

                    @Override
                    public Position getPosition() {
                        return p;
                    }

                    @Override
                    public int getId() {
                        return p.hashCode();
                    }
                };
            }
        };
    }
}
