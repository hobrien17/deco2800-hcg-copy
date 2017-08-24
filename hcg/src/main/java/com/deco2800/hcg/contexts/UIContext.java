package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Convenience subclass of Context. Can be used for building basic menus and
 * game screens. All provided methods should be sufficient as-is, and only ac
 * constructor needs to be provided.
 *
 * @author Richy McGregor
 */
public abstract class UIContext extends Context {

    // A stage for the UI elements
    protected Stage stage = new Stage(new ScreenViewport());

    @Override
    public void dispose() {
        // Do nothing
    }

    @Override
    public void show() {
        // Capture user input
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        // Release user input
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void resume() {
        // Do nothing
    }

    @Override
    public void render(float delta) {
        // Call act and draw on all Actors on the Stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void onTick(long gameTickCount) {
        // Do nothing
    }

    @Override
    public boolean ticksRunning() {
        return false;
    }

}
