package com.deco2800.hcg.managers;

/**
 * A tickable manager.
 * Receives an onTick every game tick
 */
public interface TickableManager {
	void onTick(long gameTickCount);
}
