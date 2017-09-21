package com.deco2800.hcg.managers;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.deco2800.hcg.observers.KeyDownObserver;
import com.deco2800.hcg.observers.KeyUpObserver;
import com.deco2800.hcg.observers.MouseMovedObserver;
import com.deco2800.hcg.observers.ScrollObserver;
import com.deco2800.hcg.observers.TouchDownObserver;
import com.deco2800.hcg.observers.TouchDraggedObserver;
import com.deco2800.hcg.observers.TouchUpObserver;

public class StackInputManager extends Manager implements InputProcessor {
	private ArrayList<KeyDownObserver> keyDownListeners = new ArrayList<>();

    private ArrayList<KeyUpObserver> keyUpListeners = new ArrayList<>();

    private ArrayList<TouchDownObserver> touchDownListeners = new ArrayList<>();

    private ArrayList<TouchUpObserver> touchUpListeners = new ArrayList<>();

    private ArrayList<TouchDraggedObserver> touchDragegdListeners = new ArrayList<>();
    
	private ArrayList<MouseMovedObserver> mouseMovedListeners = new ArrayList<>();

	private ArrayList<ScrollObserver> scrollListeners = new ArrayList<>();

	/**
	 * Adds a key down listener to the list of key down listeners
	 * @param observer the key down observer to add
	 */
	public void addKeyDownListener(KeyDownObserver observer) {
		keyDownListeners.add(observer);
	}

	/**
	 * Removes the given observer from the list of key down listeners
	 * @param observer the key down observer to remove
	 */
	public void removeKeyDownListener(KeyDownObserver observer) {
		keyDownListeners.remove(observer);
	}

	/**
	 * Adds a key up listener to the list of key up listeners
	 * @param observer the key up observer to add
	 */
	public void addKeyUpListener(KeyUpObserver observer) {
		keyUpListeners.add(observer);
	}

	/**
	 * Removes the given observer from the list of key up listeners
	 * @param observer the key up observer to remove
	 */
	public void removeKeyUpListener(KeyUpObserver observer) {
		keyUpListeners.remove(observer);
	}

	/**
	 * Adds a touch down listener to the list of touch down listeners
	 * @param observer the touch down observer to add
	 */
	public void addTouchDownListener(TouchDownObserver observer) {
		touchDownListeners.add(observer);
	}

	/**
	 * Removes the given observer from the list of touch down listeners
	 * @param observer the touch down observer to remove
	 */
	public void removeTouchDownListener(TouchDownObserver observer) {
		touchDownListeners.remove(observer);
	}

	/**
	 * Adds a touch up listener to the list of touch up listeners
	 * @param observer the touch up observer to add
	 */
	public void addTouchUpListener(TouchUpObserver observer) {
		touchUpListeners.add(observer);
	}

	/**
	 * Removes the given observer from the list of touch up listeners
	 * @param observer the touch up observer to remove
	 */
	public void removeTouchUpListener(TouchUpObserver observer) {
		touchUpListeners.remove(observer);
	}

	/**
	 * Adds a touch dragged listener to the list of touch dragged listeners
	 * @param observer the touch dragged observer to add
	 */
	public void addTouchDraggedListener(TouchDraggedObserver observer) {
		touchDragegdListeners.add(observer);
	}

	/**
	 * Removes the given observer from the list of touch dragged listeners
	 * @param observer the touch dragged observer to remove
	 */
	public void removeTouchDraggedListener(TouchDraggedObserver observer) {
		touchDragegdListeners.remove(observer);
	}

	/**
	 * Adds a mouse moved listener to the list of mouse moved listeners
	 * @param observer the mouse moved observer to add
	 */
	public void addMouseMovedListener(MouseMovedObserver observer) {
		mouseMovedListeners.add(observer);
	}

	/**
	 * Removes the given observer from the list of mouse moved listeners
	 * @param observer the mouse moved observer to remove
	 */
	public void removeMouseMovedListener(MouseMovedObserver observer) {
		mouseMovedListeners.remove(observer);
	}

	/**
	 * Adds a scroll listener to the list of scroll listeners
	 * @param observer the scroll observer to add
	 */
	public void addScrollListener(ScrollObserver observer) {
		scrollListeners.add(observer);
	}

	/**
	 * Removes the given observer from the list of scroll listeners
	 * @param observer the scroll observer to remove
	 */
	public void removeScrollListener(ScrollObserver observer) {
		scrollListeners.remove(observer);
	}

	@Override
	public boolean keyDown(int keycode) {
		for (KeyDownObserver observer : keyDownListeners) {
			observer.notifyKeyDown(keycode);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		for (KeyUpObserver observer : keyUpListeners) {
			observer.notifyKeyUp(keycode);
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for (TouchDownObserver observer : touchDownListeners) {
			observer.notifyTouchDown(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (TouchUpObserver observer : touchUpListeners) {
			observer.notifyTouchUp(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for (TouchDraggedObserver observer : touchDragegdListeners) {
			observer.notifyTouchDragged(screenX, screenY, pointer);
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for (MouseMovedObserver observer : mouseMovedListeners) {
			observer.notifyMouseMoved(screenX, screenY);
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		for (ScrollObserver observer : scrollListeners) {
			observer.notifyScrolled(amount);
		}
		return true;
	}
}
