package com.deco2800.hcg.managers;

import com.deco2800.hcg.multiplayer.InputType;
import com.deco2800.hcg.observers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInputManager extends Manager implements TickableManager {
	
	private NetworkManager networkManager = (NetworkManager) GameManager.get().getManager(NetworkManager.class);

    private HashMap<Integer, KeyDownObserver> keyDownListeners = new HashMap<>();
    private HashMap<Integer, KeyUpObserver> keyUpListeners = new HashMap<>();
    private HashMap<Integer, TouchDownObserver> touchDownListeners = new HashMap<>();
    private HashMap<Integer, TouchUpObserver> touchUpListeners = new HashMap<>();
    private HashMap<Integer, TouchDraggedObserver> touchDragegdListeners = new HashMap<>();
	private HashMap<Integer, MouseMovedObserver> mouseMovedListeners = new HashMap<>();
	private HashMap<Integer, ScrollObserver> scrollListeners = new HashMap<>();
	
	private ConcurrentHashMap<Long, ArrayList<int[]>> actionQueue = new ConcurrentHashMap<>();
	
	private long gameTickCount = 0;
	
	/**
	 * Adds a key down listener to the list of key down listeners
	 * @param observer the key down observer to add
	 */
	public void addKeyDownListener(Integer peer, KeyDownObserver observer) {
		keyDownListeners.put(peer, observer);
	}

	/**
	 * Removes the given observer from the list of key down listeners
	 * @param observer the key down observer to remove
	 */
	public void removeKeyDownListener(Integer peer, KeyDownObserver observer) {
		keyDownListeners.remove(peer, observer);
	}

	/**
	 * Adds a key up listener to the list of key up listeners
	 * @param observer the key up observer to add
	 */
	public void addKeyUpListener(Integer peer, KeyUpObserver observer) {
		keyUpListeners.put(peer, observer);
	}

	/**
	 * Removes the given observer from the list of key up listeners
	 * @param observer the key up observer to remove
	 */
	public void removeKeyUpListener(Integer peer, KeyUpObserver observer) {
		keyUpListeners.remove(peer, observer);
	}

	/**
	 * Adds a touch down listener to the list of touch down listeners
	 * @param observer the touch down observer to add
	 */
	public void addTouchDownListener(Integer peer, TouchDownObserver observer) {
		touchDownListeners.put(peer, observer);
	}

	/**
	 * Removes the given observer from the list of touch down listeners
	 * @param observer the touch down observer to remove
	 */
	public void removeTouchDownListener(Integer peer, TouchDownObserver observer) {
		touchDownListeners.remove(peer, observer);
	}

	/**
	 * Adds a touch up listener to the list of touch up listeners
	 * @param observer the touch up observer to add
	 */
	public void addTouchUpListener(Integer peer, TouchUpObserver observer) {
		touchUpListeners.put(peer, observer);
	}

	/**
	 * Removes the given observer from the list of touch up listeners
	 * @param observer the touch up observer to remove
	 */
	public void removeTouchUpListener(Integer peer, TouchUpObserver observer) {
		touchUpListeners.remove(peer, observer);
	}

	/**
	 * Adds a touch dragged listener to the list of touch dragged listeners
	 * @param observer the touch dragged observer to add
	 */
	public void addTouchDraggedListener(Integer peer, TouchDraggedObserver observer) {
		touchDragegdListeners.put(peer, observer);
	}

	/**
	 * Removes the given observer from the list of touch dragged listeners
	 * @param observer the touch dragged observer to remove
	 */
	public void removeTouchDraggedListener(Integer peer, TouchDraggedObserver observer) {
		touchDragegdListeners.remove(peer, observer);
	}

	/**
	 * Adds a mouse moved listener to the list of mouse moved listeners
	 * @param observer the mouse moved observer to add
	 */
	public void addMouseMovedListener(Integer peer, MouseMovedObserver observer) {
		mouseMovedListeners.put(peer, observer);
	}

	/**
	 * Removes the given observer from the list of mouse moved listeners
	 * @param observer the mouse moved observer to remove
	 */
	public void removeMouseMovedListener(Integer peer, MouseMovedObserver observer) {
		mouseMovedListeners.remove(peer, observer);
	}

	/**
	 * Adds a scroll listener to the list of scroll listeners
	 * @param observer the scroll observer to add
	 */
	public void addScrollListener(Integer peer, ScrollObserver observer) {
		scrollListeners.put(peer, observer);
	}

	/**
	 * Removes the given observer from the list of scroll listeners
	 * @param observer the scroll observer to remove
	 */
	public void removeScrollListener(Integer peer, ScrollObserver observer) {
		scrollListeners.remove(peer, observer);
	}

	public void keyDown(Integer peer, int keycode) {
		keyDownListeners.get(peer).notifyKeyDown(keycode);
	}

	public void keyUp(Integer peer, int keycode) {
		keyUpListeners.get(peer).notifyKeyUp(keycode);
	}

	public void touchDown(Integer peer, int screenX, int screenY, int pointer, int button) {
		touchDownListeners.get(peer).notifyTouchDown(screenX, screenY, pointer, button);
	}

	public void touchUp(Integer peer, int screenX, int screenY, int pointer, int button) {
		touchUpListeners.get(peer).notifyTouchUp(screenX, screenY, pointer, button);
	}

	public void touchDragged(Integer peer, int screenX, int screenY, int pointer) {
		touchDragegdListeners.get(peer).notifyTouchDragged(screenX, screenY, pointer);
	}

	public void mouseMoved(Integer peer, int screenX, int screenY) {
		mouseMovedListeners.get(peer).notifyMouseMoved(screenX, screenY);
	}

	public void scrolled(Integer peer, int amount) {
		scrollListeners.get(peer).notifyScrolled(amount);
	}
	
	public void queueLocalAction(int... args) {
		if (networkManager.isInitialised()) {
			networkManager.sendInputMessage(args);
		}
		
		long tick = gameTickCount + (networkManager.isInitialised() ? 3 : 1);
		if (!actionQueue.containsKey(tick)) {
			actionQueue.put(tick, new ArrayList<>());
		}
		int[] localArgs = new int[args.length + 1];
		localArgs[0] = 0;
		System.arraycopy(args, 0, localArgs, 1, args.length);
		actionQueue.get(tick).add(localArgs);
	}
	
	public void queueAction(long tick, int... args) {
		if (!actionQueue.containsKey(tick)) {
			actionQueue.put(tick, new ArrayList<>());
		}
		actionQueue.get(tick).add(args);
	}

	@Override
	public void onTick(long totalTickCount) {
		this.gameTickCount++;
		ArrayList<int[]> actions = actionQueue.get(gameTickCount);
		if (actions == null) {
			return;
		}
		for (int[] action : actions) {
			InputType inputType = InputType.values()[action[1]];
			switch (inputType) {
			case KEY_DOWN:
				keyDown(action[0], action[2]);
				break;
			case KEY_UP:
				keyUp(action[0], action[2]);
				break;
			case MOUSE_MOVED:
				mouseMoved(action[0], action[2], action[3]);
				break;
			case SCROLL:
				scrolled(action[0], action[2]);
				break;
			case TOUCH_DOWN:
				touchDown(action[0], action[2], action[3], action[4], action[5]);
				break;
			case TOUCH_DRAGGED:
				touchDragged(action[0], action[2], action[3], action[4]);
				break;
			case TOUCH_UP:
				touchUp(action[0], action[2], action[3], action[4], action[5]);
				break;
			default:
				break;
				
			}
		}
		actionQueue.remove(gameTickCount);
	}
}
