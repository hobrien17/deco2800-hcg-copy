package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface TouchDraggedObserver {

    /**
     * Runs when a drag motion is made on the screen
     *
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @param pointer <unknown>
     */
    void notifyTouchDragged(int screenX, int screenY, int pointer);

}
