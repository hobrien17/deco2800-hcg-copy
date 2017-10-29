package com.deco2800.hcg;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Launches Hardcor3Gard3ning.
 *
 * @author leggy
 */
public class GameLauncher {
	
	private GameLauncher(){
	}

    /**
     * Main function for the game
     *
     * @param arg Command line arguments (we wont use these)
     */
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "DECO2800 2017: Hardcor3Gard3ning";
        config.fullscreen = false;
        config.x = 1920;
        config.y = 1080;
        //Gets width and height from desktop resolution. May reduce performance for higher resolution displays
        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        @SuppressWarnings("unused")
        LwjglApplication game = new LwjglApplication(new Hardcor3Gard3ning(), config);
    }
}