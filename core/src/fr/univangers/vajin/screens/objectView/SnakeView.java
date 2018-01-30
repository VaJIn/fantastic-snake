package fr.univangers.vajin.screens.objectView;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import fr.univangers.vajin.gamemodel.Entity;
import fr.univangers.vajin.gamemodel.utilities.Position;

public class SnakeView extends EntityView {

    public SnakeView(Entity entity, TextureAtlas atlas, int tileWidth, int tileHeight) {
        super(entity, atlas, tileWidth, tileHeight);
    }

    @Override
    protected Sprite createSprite(Position pos, String resource) {
        return null;
    }
}
