package com.deco2800.hcg;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worlds.World;
import org.junit.BeforeClass;
import org.mockito.Mockito;

/**
 * Initialises libGDX in preparation for testing.
 * All tests that depend on libGDX code should extend this class.
 *
 * Created by josh on 8/07/2017.
 */
public abstract class BaseTest {

    private static Application game;

    @BeforeClass
    public static void init() {
        game = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {

            }

            @Override
            public void resize(int width, int height) {

            }

            @Override
            public void render() {

            }

            @Override
            public void pause() {

            }

            @Override
            public void resume() {

            }

            @Override
            public void dispose() {

            }
        });

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }


    public static void clean() {
        game.exit();
        game = null;
    }
}
