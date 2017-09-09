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
        config.width = 1280;
        config.height = 720;
        config.title = "DECO2800 2017: Hardcor3Gard3ning";
        @SuppressWarnings("unused")
        LwjglApplication game = new LwjglApplication(new Hardcor3Gard3ning(),
                config);
    }
}