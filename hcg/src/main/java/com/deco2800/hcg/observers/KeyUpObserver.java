package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface KeyUpObserver {

    /**
     * Runs when a key up is detected.
     *
     * @param keycode the code of the keyboard key that was released
     */
    void notifyKeyUp(int keycode);

}
