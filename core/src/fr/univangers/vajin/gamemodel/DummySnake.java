package fr.univangers.vajin.gamemodel;

import fr.univangers.vajin.gamemodel.utilities.Position;

import java.util.*;

public class DummySnake extends Snake {

    List<SnakeAtom> atoms;

    List<MutableObjectObserver> observers;

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
    public int size() {
        return atoms.size();
    }

    @Override
    public int grow(int howMuch) {
        return 0;
    }

    @Override
    public int shrink(int howMuch) {
        return 0;
    }

    @Override
    public Iterator<SnakeAtom> activatedAtomIterator() {
        return atoms.iterator();
    }

    @Override
    public void computeTick(int tick) {
        //This is a dummy snake, it does nothing
    }

    @Override
    public void handleCollisionWith(MutableObject otherObject, Position collisionPosition) {
        //This is a dummy snake
    }

    @Override
    public boolean coverPosition(Position pos) {
        ListIterator<SnakeAtom> it = atoms.listIterator();
        SnakeAtom next = it.next();
        while (it.hasNext() && next.isActivated()) {
            if (next.getPosition().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MutableObjectAtom getAtomAt(Position pos) {
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
    public void registerObserver(MutableObjectObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(MutableObjectObserver observer) {
        this.observers.remove(observer);
    }
}
