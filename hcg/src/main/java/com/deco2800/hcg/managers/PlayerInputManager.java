package com.deco2800.hcg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
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
    private HashMap<Integer, TouchDraggedObserver> touchDraggedListeners = new HashMap<>();
	private HashMap<Integer, MouseMovedObserver> mouseMovedListeners = new HashMap<>();
	private HashMap<Integer, ScrollObserver> scrollListeners = new HashMap<>();
	
	private HashMap<Long, ArrayList<Input>> inputQueue = new HashMap<>();
	
	private float mouseX;
	private float mouseY;
	
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
		touchDraggedListeners.put(player, observer);
	}

	/**
	 * Removes the given observer from the list of touch dragged listeners
	 * @param observer the touch dragged observer to remove
	 */
	public void removeTouchDraggedListener(Integer player, TouchDraggedObserver observer) {
		touchDraggedListeners.remove(player, observer);
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
		touchDraggedListeners.get(player).notifyTouchDragged(screenX, screenY, pointer);
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
	 * Queues a local input
	 */
	public void queueLocalInput(InputType type, int[] ints, float[] floats) {
		long tick = inputTickCount + (networkManager.isMultiplayerGame() ? 4 : 1);
		Input input = new Input(type, 0, ints, floats);
		
		if (networkManager.isMultiplayerGame()) {
			networkManager.queueMessage(new InputMessage(tick, input));
		}
		
		if (!inputQueue.containsKey(tick)) {
			inputQueue.put(tick, new ArrayList<>());
		}
		inputQueue.get(tick).add(input);
	}
	
	/**
	 * Queues an input
	 * @param tick the tick on which the input should be performed
	 * @param input the input to be queued
	 */
	public void queueInput(long tick, Input input) {
		if (!inputQueue.containsKey(tick)) {
			inputQueue.put(tick, new ArrayList<>());
		}
		inputQueue.get(tick).add(input);
	}
	
	/**
	 * Sets the local player's mouse position
	 * @param worldX The x position of mouse movement in the world
	 * @param worldY The y position of mouse movement in the world
	 */
	public void setLocalMousePosition(float worldX, float worldY) {
		mouseX = worldX;
		mouseY = worldY;
	}
	
	/**
	 * Gets the x coordinate of the local player's mouse
	 * @return The x position of mouse movement in the world
	 */
	public float getLocalMouseX() {
		return mouseX;
	}
	
	/**
	 * Gets the y coordinate of the local player's mouse
	 * @return The y position of mouse movement in the world
	 */
	public float getLocalMouseY() {
		return mouseY;
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
		
		ArrayList<Input> inputs = inputQueue.get(inputTickCount);
		if (inputs == null) {
			return;
		}
		for (Input input : inputs) {
			input.perform();
		}
		inputQueue.remove(inputTickCount);
	}
	
	public class Input {
		private InputType type;
		private int playerId;
		private int[] ints;
		private float[] floats;
		
		public Input(InputType type, int playerId, int[] ints, float[] floats) {
			this.type = type;
			this.playerId = playerId;
			this.ints = ints;
			this.floats = floats;
		}
		
		public InputType getType() {
			return type;
		}
		
		public int[] getInts() {
			return ints != null ? ints : new int[0];
		}
		
		public float[] getFloats() {
			return floats != null ? floats : new float[0];
		}
		
		private void perform() {
			int x = 0;
			int y = 0;
			
			if (floats != null && floats.length >= 2) {
				Vector3 screenCoords = GameManager.get().worldToScreen(new Vector3(floats[0], floats[1], 0));
				x = (int) screenCoords.x;
				y = (int) -screenCoords.y + Gdx.graphics.getHeight();
			}
			
			switch (type) {
			case KEY_DOWN:
				keyDown(playerId, ints[0]);
				break;
			case KEY_UP:
				keyUp(playerId, ints[0]);
				break;
			case MOUSE_MOVED:
				mouseMoved(playerId, x, y);
				break;
			case SCROLL:
				scrolled(playerId, ints[0]);
				break;
			case TOUCH_DOWN:
				touchDown(playerId, x, y, ints[0], ints[1]);
				break;
			case TOUCH_DRAGGED:
				touchDragged(playerId, x, y, ints[0]);
				break;
			case TOUCH_UP:
				touchUp(playerId, x, y, ints[0], ints[1]);
				break;
			default:
				break;	
			}
		}
	}
}
