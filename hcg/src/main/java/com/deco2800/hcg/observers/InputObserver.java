package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface InputObserver {

    /**
     * Runs when a key down is detected
     *
     * @param keycode the keyboard key that was pressed
     */
    void notifyKeyDown(int keycode);

    /**
     * Runs when a key up is detected.
     *
     * @param keycode the code of the keyboard key that was released
     */
    void notifyKeyUp(int keycode);

    /**
     * Runs when character is typed
     *
     * @param character the character that was typed
     */
    void notifyKeyTyped(char character);

    /**
     * Runs when a touch down event is detected at the given position
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @param pointer <unknown>
     * @param button <unknown>
     */
    void notifyTouchDown(int screenX, int screenY, int pointer, int button);

    /**
     * Runs when a touch up is detected at the given position
     *
     * @param screenX the x position on the screen of the touch up
     * @param screenY the y position on the screen of the touch up
     * @param pointer <unknown>
     * @param button <unknown>
     */
    void notifyTouchUp(int screenX, int screenY, int pointer, int button);

    /**
     * Runs when a drag motion is made on the screen
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @param pointer <unknown>
     */
    void notifyTouchDragged(int screenX, int screenY, int pointer);

    /**
     * Runs when mouse movement is detected
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     */
    void notifyMouseMoved(int screenX, int screenY);

    /**
     * Runs when scrolling is detected
     *
     * @param amount the scroll amount
     */
    void notifyScrolled(int amount);

}
