package com.deco2800.hcg.managers;

import com.badlogic.gdx.Screen;
import com.deco2800.hcg.contexts.Context;

import java.util.ArrayDeque;
import java.util.Deque;

public class ContextManager extends Manager implements Screen, TickableManager {

	private Deque<Context> contextStack;

	public ContextManager() {
		contextStack = new ArrayDeque<>();
	}

	public Context currentContext() {
		return contextStack.peek();
	}

	public void pushContext(Context newContext) {
		// Hide the old Context and show the new one
		if (!contextStack.isEmpty()) {
			contextStack.peek().hide();
		}
		contextStack.push(newContext);
		newContext.show();
	}

	public void popContext() {
		// Destroy the current context
		if (!contextStack.isEmpty()) {
			Context old = contextStack.pop();
			old.hide();
			old.dispose();
		}

		// Show the context underneath
		if (!contextStack.isEmpty()) {
			contextStack.peek().show();
		}
	}

	@Override
	public void onTick(long gameTickCount) {
		for (Context context: contextStack) {
			context.onTick(gameTickCount);
		}
	}

	public boolean ticksRunning() {
		if (!contextStack.isEmpty()) {
			return contextStack.peek().ticksRunning();
		} else {
			return false;
		}
	}

	@Override
	public void show() {
		// Do nothing
	}

	@Override
	public void render(float delta) {
		if (!contextStack.isEmpty()) {
			contextStack.peek().render(delta);
		}
	}

	@Override
	public void resize(int width, int height) {
		for (Context context: contextStack) {
			context.resize(width, height);
		}
	}

	@Override
	public void pause() {
		for (Context context: contextStack) {
			context.pause();
		}
	}

	@Override
	public void resume() {
		for (Context context: contextStack) {
			context.resume();
		}
	}

	@Override
	public void hide() {
		// Do nothing
	}

	@Override
	public void dispose() {
		for (Screen screen: contextStack) {
			screen.dispose();
		}
	}

}
