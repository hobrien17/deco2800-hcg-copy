package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Screen;

/**
 * Abstract class representing a game context.
 * This class is VERY abstract - in most cases UIContext should be subclassed instead.
 * @see UIContext
 * @author Richy McGregor
 */
public abstract class Context implements Screen {

	/**
	 * Called when the context is destroyed.
	 * Cleaning up any used resources should be done here.
	 */
	@Override
	public abstract void dispose();

	/**
	 * Called when the context is (re-) presented to the user.
	 * Tasks like capturing user input should be done here.
	 */
	@Override
	public abstract void show();

	/**
	 * Called when the context is hidden from the user.
	 * Also called prior to dispose.
	 * Tasks like releasing user input should be done here.
	 */
	@Override
	public abstract void hide();

	/**
	 * Called when the game window loses focus,
	 */
	@Override
	public abstract void pause();

	/**
	 * Called when the game window regains focus,
	 */
	@Override
	public abstract void resume();

	/**
	 * Render this context to the screen.
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public abstract void render(float delta);

	/**
	 * Called when the game window is resized.
	 * @param width The new window width.
 The new window height.
	 */
	@Override
	public abstract void resize(int width, int height) ;

	/**
	 * Called each game tick.
	 * @param gameTickCount The number of ticks since game start.
	 */
	public abstract void onTick(long gameTickCount);

	/**
	 * Tests if ticks should be fired.
	 * @return True if ticks should be fired.
	 */
	public abstract boolean ticksRunning();

}
