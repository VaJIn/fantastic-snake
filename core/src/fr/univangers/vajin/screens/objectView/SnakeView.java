package fr.univangers.vajin.screens.objectView;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.univangers.vajin.GameConstants;
import fr.univangers.vajin.gamemodel.*;
import fr.univangers.vajin.gamemodel.utilities.Direction;
import fr.univangers.vajin.gamemodel.utilities.Position;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SnakeView extends Actor implements SnakeObserver, ObjectView {


    private AssetManager assetManager;

    private TextureAtlas textureAtlas;

    Map<Integer, Sprite> sprites;

    boolean dead;

    int tileWidth;
    int tileHeight;

    public SnakeView(TextureAtlas textureAtlas, Snake snake, int tileWidth, int tileHeight) {
        this.assetManager = assetManager;
        this.textureAtlas = textureAtlas;

        this.sprites = new HashMap<>();

        this.tileWidth = tileWidth;

        this.tileHeight = tileHeight;

        this.dead = false;

        Iterator<SnakeAtom> it = snake.activatedAtomIterator();

        while (it.hasNext()) {
            SnakeAtom atom = it.next();
            sprites.put(atom.getId(), this.getSpriteFromAtom(atom));
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {

        for (Map.Entry<Integer, Sprite> entry : sprites.entrySet()) {
            entry.getValue().draw(batch, alpha);
        }

    }

    private Sprite getSpriteFromAtom(SnakeAtom atom) {
        Sprite sprite;
        TextureRegion region;
        if (atom.getAtomTowardsHead() == null) {
            Direction fromDir = Direction.fromPosition(atom.getAtomTowardsTail().getPosition(), atom.getPosition());
            System.out.println("head_" + fromDir.toString().toLowerCase());
            region = textureAtlas.findRegion("head_" + fromDir.toString().toLowerCase());
        } else if (atom.getAtomTowardsTail() == null) {
            Direction toDir = Direction.fromPosition(atom.getPosition(), atom.getAtomTowardsHead().getPosition());
            System.out.println("tail_" + toDir.toString().toLowerCase());
            region = textureAtlas.findRegion("tail_" + toDir.toString().toLowerCase());
        } else {
            Direction toDir = Direction.fromPosition(atom.getPosition(), atom.getAtomTowardsTail().getPosition());
            Direction fromDir = Direction.fromPosition(atom.getPosition(), atom.getAtomTowardsHead().getPosition());
            System.out.println(fromDir.toString().toLowerCase() + "_" + toDir.toString().toLowerCase());
            region = textureAtlas.findRegion(fromDir.toString().toLowerCase() + "_" + toDir.toString().toLowerCase());
        }
        sprite = new Sprite(region);
        sprite.setPosition(atom.getPosition().getX() * tileWidth, atom.getPosition().getY() * tileHeight);
        sprite.setSize(tileWidth, tileHeight);
        return sprite;
    }


    @Override
    public void notifyNewAtom(SnakeAtom atom) {
        sprites.put(atom.getId(), getSpriteFromAtom(atom));
    }

    @Override
    public void notifyRemovedAtom(SnakeAtom atom) {
        sprites.remove(atom.getId());
    }

    @Override
    public void notifyAtomUpdate(SnakeAtom atom) {
        sprites.put(atom.getId(), getSpriteFromAtom(atom));
    }

    @Override
    public void notifyDestroyed(MutableObject obj) {
        dead = true;
    }

    @Override
    public void notifyStateChange(MutableObject obj, int what) {

    }

    @Override
    public void notifyPositionChange(MutableObject obj) {

    }
}
