package fr.univangers.vajin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {

    private static final String PREFS_NAME = "snakeRPG";
    private static final String LAST_SERVER_PORT = "last_server_port";
    private static final String LAST_SERVER_NAME = "last_server_name";
    private static final String LOCAL_ALIAS = "local_alias";
    private static final String DEBUG_MODE = "debug_mode";
    private static final String UI_SKIN = "ui_skin";

    private boolean changed;

    public AppPreferences() {
        this.changed = false;
    }

    private Preferences getPreferences() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public void setLastServer(String name, int port) {
        changed = true;
        this.getPreferences().putString(LAST_SERVER_NAME, name);
        this.getPreferences().putInteger(LAST_SERVER_PORT, port);
    }

    public String getLastServerName() {
        return this.getPreferences().getString(LAST_SERVER_NAME);
    }

    public int getLastServerPort() {
        return this.getPreferences().getInteger(LAST_SERVER_PORT);
    }

    public String getLocalAlias() {
        return this.getPreferences().getString(LOCAL_ALIAS);
    }

    public void setLocalAlias(String localAlias) {
        changed = true;
        this.getPreferences().putString(LOCAL_ALIAS, localAlias);
    }

    public boolean isDebugModeActivated() {
        return this.getPreferences().getBoolean(DEBUG_MODE);
    }

    public void setDebugMode(boolean activated) {
        this.changed = true;
        this.getPreferences().putBoolean(DEBUG_MODE, activated);
    }

    public String getUISkin() {
        return this.getPreferences().getString(UI_SKIN);
    }

    public void setUISkin(String uiSkin) {
        this.changed = true;
        this.getPreferences().putString(UI_SKIN, uiSkin);
    }

    public void flush() {
        if (this.changed) {
            this.getPreferences().flush();
            this.changed = false;
        }
    }

    public boolean hasChanged() {
        return this.changed;
    }


}
