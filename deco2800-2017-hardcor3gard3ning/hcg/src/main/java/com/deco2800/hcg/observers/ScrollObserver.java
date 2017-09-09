package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface ScrollObserver {

    /**
     * Runs when scrolling is detected
     *
     * @param amount the scroll amount
     */
    void notifyScrolled(int amount);

}
