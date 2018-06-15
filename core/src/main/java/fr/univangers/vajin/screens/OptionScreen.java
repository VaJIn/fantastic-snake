package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import fr.univangers.vajin.SnakeRPG;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptionScreen extends AbstractMenuScreen {

    private static final Logger logger = LogManager.getLogger();

    public Array<String> availableSkins;

    public OptionScreen(SnakeRPG parent) {
        super(parent);
    }

    public void loadAvailableSkins() {
        if (this.availableSkins == null) {
            FileHandle fileHandle = Gdx.files.internal("skin");
            FileHandle[] childs = fileHandle.list((file, s) -> file.isDirectory());

            this.availableSkins = new Array<>(childs.length);

            this.availableSkins.sort();

            for (FileHandle childHandle : childs) {
                logger.info("Found skin " + childHandle.name());
                availableSkins.add(childHandle.name());
            }
        }
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


        final SelectBox<String> uiSkinSelect = new SelectBox<String>(skin);

        final TextButton validateButton = new TextButton("Save", skin);
        final TextButton cancelButton = new TextButton("Cancel", skin);

        this.loadAvailableSkins();
        uiSkinSelect.setItems(this.availableSkins);

        uiSkinSelect.setSelected(this.getApplication().getAppPreferences().getUISkin());

        validateButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().getAppPreferences().setDebugMode(debugModeCheckBox.isChecked());

                getApplication().getAppPreferences().setUISkin(uiSkinSelect.getSelected());
                getApplication().changeUISkin(uiSkinSelect.getSelected());

                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);

                getApplication().getAppPreferences().flush();

            }
        });

        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        layout.add(new Label("Debug mode", skin)).fillX();
        layout.add(debugModeCheckBox);
        layout.row().pad(10, 0, 0, 0);

        layout.add(new Label("Interface theme : ", skin)).fillX();
        layout.add(uiSkinSelect).fillX();
        layout.row().pad(10, 0, 0, 0);

        layout.add(cancelButton).fillX();
        layout.add(validateButton).fillX();

        this.getStage().addActor(layout);
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
