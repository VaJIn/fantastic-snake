package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fr.univangers.vajin.SnakeRPG;

public class OptionScreen extends AbstractMenuScreen {

    public OptionScreen(SnakeRPG parent) {
        super(parent);
    }

    @Override
    public void show() {
        super.show();

        Skin skin = this.getApplication().getUISkin();

        final Table layout = new Table(skin);
        layout.setFillParent(true);

        final CheckBox debugModeCheckBox = new CheckBox(null, skin);

        debugModeCheckBox.setChecked(
                this.getApplication().getAppPreferences().isDebugModeActivated()
        );

        layout.add(new Label("Debug mode", skin)).fillX();
        layout.add(debugModeCheckBox);
        layout.row().pad(10, 0, 0, 0);

        final TextButton validateButton = new TextButton("Save", skin);
        final TextButton cancelButton = new TextButton("Cancel", skin);

        validateButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().getAppPreferences().setDebugMode(debugModeCheckBox.isChecked());
                getApplication().getAppPreferences().flush();

                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        layout.add(cancelButton).fillX();
        layout.add(validateButton).fillX();

        this.getStage().addActor(layout);

        Gdx.input.setInputProcessor(this.getStage());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
