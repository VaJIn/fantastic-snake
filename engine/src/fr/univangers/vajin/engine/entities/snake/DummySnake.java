package fr.univangers.vajin.engine.entities.snake;

import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.EntityObserver;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.*;

public class DummySnake extends Snake {

    @Override
    public boolean isKiller() {
        return true;
    }

    List<SnakeAtom> atoms;

    List<EntityObserver> observers;

    private class DummySnakeAtom extends SnakeAtom {

        private DummySnakeAtom(Position position, int id) {
            super(position, id);
            atomTowardsTail = null;
            atomTowardsHead = null;
        }

        private DummySnakeAtom(Position position, SnakeAtom towardsTail) {
            super(position, towardsTail.getId() + 1);
            this.atomTowardsTail = towardsTail;
            towardsTail.atomTowardsHead = this;
        }
    }


    public DummySnake(int maxLifePoint, int lifePoint, int resistance, int luckFactor, int speed, Position spawn) {
        super(maxLifePoint, lifePoint, resistance, luckFactor, speed);
        this.observers = new ArrayList<>();

        this.atoms = new LinkedList<>();


        atoms.add(new DummySnakeAtom(new Position(spawn.getX() + 2, spawn.getY() + 1), 0));
        atoms.add(new DummySnakeAtom(new Position(spawn.getX() + 2, spawn.getY()), atoms.get(0)));
        atoms.add(new DummySnakeAtom(new Position(spawn.getX() + 1, spawn.getY()), atoms.get(1)));
        atoms.add(new DummySnakeAtom(spawn, atoms.get(2)));

    }


    @Override
    public void move(WalkDirection walkDirection) {

    }

    @Override
    public void moveGrowing(WalkDirection walkDirection) {

    }

    @Override
    public void moveShrinking(WalkDirection walkDirection) {

    }

    @Override
    public int getSize() {
        return atoms.size();
    }

    @Override
    public int changeSize(int howMuch) {
        return 0;
    }

    @Override
    public void cancelNextMovements() {

    }

    @Override
    public int changeSpeed(int howMuch) {
        return 0;
    }




    @Override
    public void sendAction(int action) {
        //This is a dummy snake, it can't even receive action !
        /*
                       .,,: ,.
           .-:++++XXXXXX
         .,:+++XXXXXXXXXXX
        .++:++XXXXXXXXXX@XX
       .++:+++XXX+++X+XXXXXXX
       ,+::+:::.-----++++XXXX
      .-::+ .-'       .XX@++X@
      .,:::|      .-.  |+@X+XXM
       ,:-:| .-'       |+XX+XXM
       ==-::\    _.    '.X+XXX@
       -:+:::\          |XXX@X@
       ..:+++:\  --'   .XXXX@X@M
         .:+:++'.____.|+XXX@@@@MMM
           ,-:+:+\     \+X++:+++XXM
       ____=:++++/      |-,,/     :\
      . /-.              /,/       ::
     - |-|              /-/        |;
    '  |-|             /-:'        |:
    |  |-|     '      /==/         |:
   /"\ |/     ;     ,,--/         /.
  /\  : \__..'    .-'-:/         /:
 /\   '.--=-'----'.,-:/         /:.
 |    -,.-==-=----=-=/         /:.
 |-_     '_-====-=--/         /+|
|   .      "-,___--'.-       /++.
|    '.            / /      /:+.
 .     '..       .'  '._--'\++.
  '.      '-----'         -/X@|
    '._   .              -/X@M|
       '''------._____..-'+MMM|
        |...,,-,,, '-====:+@MM|
        */
    }

    @Override
    public Iterator<SnakeAtom> activatedAtomIterator() {
        return atoms.iterator();
    }

    @Override
    public boolean computeTick(int tick) {
        //This is a dummy snake, it does nothing
        return false;
    }

    @Override
    public List<Position> getNewPositions() {
        return new LinkedList<>();
    }



    @Override
    public void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean initiater) {
        //This is a dummy snake
    }

    @Override
    public boolean coversPosition(Position pos) {
        ListIterator<SnakeAtom> it = atoms.listIterator();
        SnakeAtom next = it.next();
        while (it.hasNext() && next.isActivated()) {
            if (next.getPosition().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public SnakeAtom getAtomAt(Position pos) {
        ListIterator<SnakeAtom> it = atoms.listIterator();
        SnakeAtom next = it.next();
        while (it.hasNext() && next.isActivated()) {
            if (next.getPosition().equals(pos)) {
                return next;
            }
        }
        return null;
    }

    @Override
    public void inflictDamage(int damage) {
        this.setLifePoint(this.getLifePoint() - damage * getResistance() / 100);
    }

    @Override
    public void destroy() {
        this.setLifePoint(0);
        ListIterator<SnakeAtom> it = atoms.listIterator();
        SnakeAtom next = it.next();
        while (it.hasNext() && next.isActivated()) {
            next.setActivated(false);
        }
    }


    @Override
    public void registerObserver(EntityObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(EntityObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public String getGraphicRessourceKeyForPosition(Position pos) {
        //TODO
        return null;
    }

    @Override
    public Iterator<Entity.EntityTileInfo> getEntityTilesInfosIterator() {
        return new Iterator<Entity.EntityTileInfo>() {

            Iterator<SnakeAtom> it = atoms.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Entity.EntityTileInfo next() {
                SnakeAtom atom = it.next();
                return new Entity.EntityTileInfo() {
                    @Override
                    public String getRessourceKey() {
                        return atom.getGraphicKey();
                    }

                    @Override
                    public Position getPosition() {
                        return atom.getPosition();
                    }

                    @Override
                    public int getId() {
                        return atom.getId();
                    }
                };
            }
        };
    }
}
