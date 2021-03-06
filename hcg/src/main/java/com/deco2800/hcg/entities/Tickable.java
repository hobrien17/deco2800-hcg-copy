package com.deco2800.hcg.entities;

/**
 * Tickable An interface to allow Entities to be ticked at a constant rate
 * allowing for AI and other tasks to occur
 *
 * @Author Tim Hadwen
 */
@FunctionalInterface
public interface Tickable {

    /**
     * On tick is called periodically (time dependant on the world settings)
     *
     * @param gameTickCount Current game tick
     */
    void onTick(long gameTickCount);
}
