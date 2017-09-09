package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface KeyDownObserver {

    /**
     * Runs when a key down is detected
     *
     * @param keycode the keyboard key that was pressed
     */
    void notifyKeyDown(int keycode);


}
