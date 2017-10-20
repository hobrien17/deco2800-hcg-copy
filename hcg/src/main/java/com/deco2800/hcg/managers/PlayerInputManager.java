package com.deco2800.hcg.managers;

import com.deco2800.hcg.contexts.PlayContext;
import com.deco2800.hcg.multiplayer.InputMessage;
import com.deco2800.hcg.multiplayer.InputType;
import com.deco2800.hcg.observers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInputManager extends Manager implements TickableManager {
	
	private ContextManager contextManager =
			(ContextManager) GameManager.get().getManager(ContextManager.class);
	private NetworkManager networkManager =
			(NetworkManager) GameManager.get().getManager(NetworkManager.class);

    private HashMap<Integer, KeyDownObserver> keyDownListeners = new HashMap<>();
    private HashMap<Integer, KeyUpObserver> keyUpListeners = new HashMap<>();
    private HashMap<Integer, TouchDownObserver> touchDownListeners = new HashMap<>();
    private HashMap<Integer, TouchUpObserver> touchUpListeners = new HashMap<>();
    private HashMap<Integer, TouchDraggedObserver> touchDragegdListeners = new HashMap<>();
	private HashMap<Integer, MouseMovedObserver> mouseMovedListeners = new HashMap<>();
	private HashMap<Integer, ScrollObserver> scrollListeners = new HashMap<>();
	
	private HashMap<Long, ArrayList<int[]>> actionQueue = new HashMap<>();
	
	private int screenX;
	private int screenY;
	
	private long inputTickCount = 0;
	
	/**
	 * Adds a key down listener to the list of key down listeners
	 * @param observer the key down observer to add
	 */
	public void addKeyDownListener(Integer player, KeyDownObserver observer) {
		keyDownListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of key down listeners
	 * @param observer the key down observer to remove
	 */
	public void removeKeyDownListener(Integer player, KeyDownObserver observer) {
		keyDownListeners.remove(player, observer);
	}

	/**
	 * Adds a key up listener to the list of key up listeners
	 * @param observer the key up observer to add
	 */
	public void addKeyUpListener(Integer player, KeyUpObserver observer) {
		keyUpListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of key up listeners
	 * @param observer the key up observer to remove
	 */
	public void removeKeyUpListener(Integer player, KeyUpObserver observer) {
		keyUpListeners.remove(player, observer);
	}

	/**
	 * Adds a touch down listener to the list of touch down listeners
	 * @param observer the touch down observer to add
	 */
	public void addTouchDownListener(Integer player, TouchDownObserver observer) {
		touchDownListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of touch down listeners
	 * @param observer the touch down observer to remove
	 */
	public void removeTouchDownListener(Integer player, TouchDownObserver observer) {
		touchDownListeners.remove(player, observer);
	}

	/**
	 * Adds a touch up listener to the list of touch up listeners
	 * @param observer the touch up observer to add
	 */
	public void addTouchUpListener(Integer player, TouchUpObserver observer) {
		touchUpListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of touch up listeners
	 * @param observer the touch up observer to remove
	 */
	public void removeTouchUpListener(Integer player, TouchUpObserver observer) {
		touchUpListeners.remove(player, observer);
	}

	/**
	 * Adds a touch dragged listener to the list of touch dragged listeners
	 * @param observer the touch dragged observer to add
	 */
	public void addTouchDraggedListener(Integer player, TouchDraggedObserver observer) {
		touchDragegdListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of touch dragged listeners
	 * @param observer the touch dragged observer to remove
	 */
	public void removeTouchDraggedListener(Integer player, TouchDraggedObserver observer) {
		touchDragegdListeners.remove(player, observer);
	}

	/**
	 * Adds a mouse moved listener to the list of mouse moved listeners
	 * @param observer the mouse moved observer to add
	 */
	public void addMouseMovedListener(Integer player, MouseMovedObserver observer) {
		mouseMovedListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of mouse moved listeners
	 * @param observer the mouse moved observer to remove
	 */
	public void removeMouseMovedListener(Integer player, MouseMovedObserver observer) {
		mouseMovedListeners.remove(player, observer);
	}

	/**
	 * Adds a scroll listener to the list of scroll listeners
	 * @param observer the scroll observer to add
	 */
	public void addScrollListener(Integer player, ScrollObserver observer) {
		scrollListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of scroll listeners
	 * @param observer the scroll observer to remove
	 */
	public void removeScrollListener(Integer player, ScrollObserver observer) {
		scrollListeners.remove(player, observer);
	}

	/**
	 * Notifies key down listeners
	 * @param player the player that performed the action
	 * @param keycode the keycode that was pressed
	 */
	public void keyDown(Integer player, int keycode) {
		keyDownListeners.get(player).notifyKeyDown(keycode);
	}

	/**
	 * Notifies key up listeners
	 * @param player the player that performed the action
	 * @param keycode the keycode that was released
	 */
	public void keyUp(Integer player, int keycode) {
		keyUpListeners.get(player).notifyKeyUp(keycode);
	}

	/**
	 * Notifies touch down listeners
	 * @param player the player that performed the action
	 * @param screenX x coordinate
	 * @param screenY y coordinate
	 * @param pointer unknown
	 * @param button unknown
	 */
	public void touchDown(Integer player, int screenX, int screenY, int pointer, int button) {
		touchDownListeners.get(player).notifyTouchDown(screenX, screenY, pointer, button);
	}

	/**
	 * Notifies touch up listeners
	 * @param player the player that performed the action
	 * @param screenX x coordinate
	 * @param screenY y coordinate
	 * @param pointer unknown
	 * @param button unknown
	 */
	public void touchUp(Integer player, int screenX, int screenY, int pointer, int button) {
		touchUpListeners.get(player).notifyTouchUp(screenX, screenY, pointer, button);
	}

	/**
	 * Notifies touch dragged listeners
	 * @param player the player that performed the action
	 * @param screenX x coordinate
	 * @param screenY y coordinate
	 * @param pointer unknown
	 */
	public void touchDragged(Integer player, int screenX, int screenY, int pointer) {
		touchDragegdListeners.get(player).notifyTouchDragged(screenX, screenY, pointer);
	}

	/**
	 * Notifies mouse moved listeners
	 * @param player the player that performed the action
	 * @param screenX x coordinate
	 * @param screenY y coordinate
	 */
	public void mouseMoved(Integer player, int screenX, int screenY) {
		mouseMovedListeners.get(player).notifyMouseMoved(screenX, screenY);
	}

	/**
	 * Notifies scroll listeners
	 * @param player the player that performed the action
	 * @param amount how far the player scrolled
	 */
	public void scrolled(Integer player, int amount) {
		scrollListeners.get(player).notifyScrolled(amount);
	}
	
	/**
	 * Queues a local action
	 * @param args the action arguments
	 */
	public void queueLocalAction(int... args) {
		long tick = inputTickCount + (networkManager.isMultiplayerGame() ? 3 : 1);
		
		if (networkManager.isMultiplayerGame()) {
			networkManager.queueMessage(new InputMessage(tick, args));
		}
		
		if (!actionQueue.containsKey(tick)) {
			actionQueue.put(tick, new ArrayList<>());
		}
		int[] localArgs = new int[args.length + 1];
		localArgs[0] = 0;
		System.arraycopy(args, 0, localArgs, 1, args.length);
		actionQueue.get(tick).add(localArgs);
	}
	
	/**
	 * Queues an action
	 * @param tick the tick on which the action should take place
	 * @param args the action arguments
	 */
	public void queueAction(long tick, int... args) {
		if (!actionQueue.containsKey(tick)) {
			actionQueue.put(tick, new ArrayList<>());
		}
		actionQueue.get(tick).add(args);
	}
	
	/**
	 * Sets the local player's mouse position
	 * @param screenX The x position of mouse movement on the screen
	 * @param screenY The y position of mouse movement on the screen
	 */
	public void setLocalMousePosition(int screenX, int screenY) {
		this.screenX = screenX;
		this.screenY = screenY;
	}
	
	/**
	 * Gets the x coordinate of the local player's mouse
	 * @return The x position of mouse movement on the screen
	 */
	public int getLocalMouseX() {
		return screenX;
	}
	
	/**
	 * Gets the y coordinate of the local player's mouse
	 * @return The y position of mouse movement on the screen
	 */
	public int getLocalMouseY() {
		return screenY;
	}
	
	/**
	 * Resets the input tick
	 */
	public void resetInputTick() {
		inputTickCount = 0;
	}
	
	/**
	 * Increments the input tick
	 */
	public void incrementInputTick() {
		inputTickCount++;
	}
	
	/**
	 * Gets the input tick
	 * @return The current input tick count
	 */
	long getInputTick() {
		return inputTickCount;
	}

	@Override
	public void onTick(long totalTickCount) {
		if (!(contextManager.currentContext() instanceof PlayContext)) {
			return;
		}
		
		ArrayList<int[]> actions = actionQueue.get(inputTickCount);
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
		actionQueue.remove(inputTickCount);
	}
}
