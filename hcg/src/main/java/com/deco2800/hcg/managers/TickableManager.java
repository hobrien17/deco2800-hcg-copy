package com.deco2800.hcg.managers;

/**
 * A tickable manager. Receives an onTick every game tick
 */
public interface TickableManager {

    /**
     * A methdd that runs on every game tick
     * @param gameTickCount the current game tick count
     */
    void onTick(long gameTickCount);
}
