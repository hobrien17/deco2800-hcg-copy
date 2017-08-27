package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface TouchUpObserver {

    /**
     * Runs when a touch up is detected at the given position
     *
     * @param screenX the x position on the screen of the touch up
     * @param screenY the y position on the screen of the touch up
     * @param pointer <unknown>
     * @param button <unknown>
     */
    void notifyTouchUp(int screenX, int screenY, int pointer, int button);

}
