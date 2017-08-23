package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Screen;

// WARNING: Do not subclass this unless you are very sure about what you are doing!
//TODO documentation
public abstract class Context implements Screen {

	@Override
	public abstract void dispose();

	@Override
	public abstract void hide();

	@Override
	public abstract void pause();

	@Override
	public abstract void render(float delta);

	@Override
	public abstract void resize(int width, int height) ;

	@Override
	public abstract void resume();

	@Override
	public abstract void show();

	public abstract void onTick(long gameTickCount);

	public abstract boolean ticksRunning();

}
