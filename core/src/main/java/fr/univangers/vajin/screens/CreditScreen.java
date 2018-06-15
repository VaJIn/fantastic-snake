package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import fr.univangers.vajin.SnakeRPG;

public class CreditScreen extends AbstractMenuScreen {

    private FileHandle creditFile;

    public CreditScreen(SnakeRPG parent) {

        super(parent);

        this.loadCreditText();
    }

    private void loadCreditText() {
        this.creditFile = Gdx.files.internal("credit.txt"); //TODO remove magic string :)
    }

    @Override
    public void show() {
        super.show();
        Skin skin = this.getApplication().getUISkin();

        Table layout = new Table();
        layout.setFillParent(true);

        this.getStage().addActor(layout);

        TextButton backToMenu = new TextButton("Back to menu", skin);

        Label creditTxt = new Label("", skin);

        if (creditFile.exists()) {
            creditTxt.setText(creditFile.readString());
        } else {
            creditTxt.setText("Error : Could not find credit.txt in internal assets");
        }
        creditTxt.setAlignment(Align.center);

        layout.add(creditTxt).width(this.getStage().getWidth() / 2);
        layout.row().pad(10, 0, 0, 0);
        layout.add(backToMenu);

        backToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

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
