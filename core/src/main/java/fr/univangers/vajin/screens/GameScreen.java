package fr.univangers.vajin.screens;

import com.badlogic.gdx.InputProcessor;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.engine.GameEngineObserver;
import fr.univangers.vajin.engine.entities.Entity;

public class GameScreen implements GameEngineObserver, InputProcessor {

    SnakeRPG application;

    public GameScreen(SnakeRPG application) {
        this.application = application;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void notifyNewEntity(Entity entity) {

    }

    @Override
    public void notifyRemovedEntity(Entity entity) {

    }

    @Override
    public void notifyGameEnd() {

    }
}
