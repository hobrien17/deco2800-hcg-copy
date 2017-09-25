package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface TouchDownObserver {

    /**
     * Runs when a touch down event is detected at the given position
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @param pointer <unknown>
     * @param button <unknown>
     */
    void notifyTouchDown(int screenX, int screenY, int pointer, int button);


}
