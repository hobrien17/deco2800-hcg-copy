package com.deco2800.hcg.managers;

import com.badlogic.gdx.Screen;
import com.deco2800.hcg.contexts.Context;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A manager designed to hold and switch between game contexts.
 * @author Richy McGregor
 */
public class ContextManager extends Manager implements Screen, TickableManager {

	// To produce nested-menu behaviour, contexts are help in a stack
	private Deque<Context> contextStack;

	/**
	 * Basic constructor for ContextManager.
	 */
	public ContextManager() {
		contextStack = new ArrayDeque<>();
	}

	/**
	 * Fetch the current game context, or null if a context has not been pushed yet.
	 * @return The current context.
	 */
	public Context currentContext() {
		return contextStack.peek();
	}

	/**
	 * Push a new context onto the top of the context stack.
	 * This will cause the game to imediatly jump into displaying the new context.
	 * @param newContext The new game context.
	 */
	public void pushContext(Context newContext) {
		// Hide the old Context and show the new one
		if (!contextStack.isEmpty()) {
			contextStack.peek().hide();
		}
		contextStack.push(newContext);
		newContext.show();
	}

	/**
	 * Destroy the current context, and return to the previous one.
	 */
	public void popContext() {
		// Destroy the current context
		if (!contextStack.isEmpty()) {
			Context old = contextStack.pop();
			old.hide();
			old.dispose();
		}

		// Show the context underneath, or exit the game
		if (!contextStack.isEmpty()) {
			contextStack.peek().show();
		} else {
			System.exit(0);
		}
	}

	/**
	 * Called on game shutdown. Calls dispose on all contexts.
	 */
	@Override
	public void dispose() {
		for (Screen screen: contextStack) {
			screen.dispose();
		}
	}

	/**
	 * Never called, does noting.
	 */
	@Override
	public void show() {
		// Do nothing
	}

	/**
	 * Never called, does noting.
	 */
	@Override
	public void hide() {
		// Do nothing
	}

	/**
	 * Calls the current context's render method. Called by the Game.
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {
		if (!contextStack.isEmpty()) {
			contextStack.peek().render(delta);
		}
	}

	/**
	 * Called whenever the game window is resized. Calls resize in all stored contexts.
	 * @param width The new window width.
	 * @param height The new window height.
	 */
	@Override
	public void resize(int width, int height) {
		for (Context context: contextStack) {
			context.resize(width, height);
		}
	}

	/**
	 * Called whenever the game window loses focus. Calls pause in all stored contexts.
	 */
	@Override
	public void pause() {
		for (Context context: contextStack) {
			context.pause();
		}
	}

	/**
	 * Called whenever the game window regains focus. Calls resume in all stored contexts.
	 */
	@Override
	public void resume() {
		for (Context context: contextStack) {
			context.resume();
		}
	}

	/**
	 * Propagate a tick on to all stored contexts.
	 * @param gameTickCount The number of ticks since game start.
	 */
	@Override
	public void onTick(long gameTickCount) {
		for (Context context: contextStack) {
			context.onTick(gameTickCount);
		}
	}

	/**
	 * Check if the current context is requesting for ticks to be fired.
	 * @return True if ticks should be fired.
	 */
	public boolean ticksRunning() {
		if (!contextStack.isEmpty()) {
			return contextStack.peek().ticksRunning();
		} else {
			return false;
		}
	}

}
