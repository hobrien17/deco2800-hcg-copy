package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface MouseMovedObserver {

    /**
     * Runs when mouse movement is detected
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     */
    void notifyMouseMoved(int screenX, int screenY);

}
