package fr.univangers.vajin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {

    private static final String PREFS_NAME = "snakeRPG";
    private static final String LAST_SERVER_PORT = "last_server_port";
    private static final String LAST_SERVER_NAME = "last_server_name";

    private Preferences getPreferences() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public void setLastServer(String name, int port) {
        this.getPreferences().putString(LAST_SERVER_NAME, name);
        this.getPreferences().putInteger(LAST_SERVER_PORT, port);

        this.getPreferences().flush();
    }

    public String getLastServerName() {
        return this.getPreferences().getString(LAST_SERVER_NAME);
    }

    public int getLastServerPort() {
        return this.getPreferences().getInteger(LAST_SERVER_PORT);
    }


}
