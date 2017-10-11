package com.deco2800.hcg.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface KeyTypedObserver {

    /**
     * Runs when character is typed
     *
     * @param character the character that was typed
     */
    void notifyKeyTyped(char character);

}
