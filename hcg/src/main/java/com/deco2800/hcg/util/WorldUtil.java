package com.deco2800.hcg.util;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.renderers.Renderable;

import java.util.Optional;

/**
 * A utility class for the World instances Created by timhadwen on 23/7/17.
 */
public class WorldUtil {

	/**
	 * Finds the closest entity to a position within a delta
	 * 
	 * @param x
	 * @param y
	 * @param delta
	 * @return Optional of WorldEntity
	 */
	public static Optional<AbstractEntity> closestEntityToPosition(float x, float y, float delta) {
		return closestEntityToPosition(x, y, delta, AbstractEntity.class);
	}

	/**
	 * Finds the closest entity of given type to a position within a delta
	 * 
	 * @param x
	 * @param y
	 * @param delta
	 * @return Optional of WorldEntity
	 */
	public static Optional<AbstractEntity> closestEntityToPosition(float x, float y, float delta, 
			Class<? extends AbstractEntity> type) {
		AbstractEntity result = null;
		double distance = Double.MAX_VALUE;
		for (Renderable r : GameManager.get().getWorld().getEntities()) {
			if (type.isInstance(r)) {
				double tempDistance = Math.sqrt(Math.pow((r.getPosX() - x), 2) + Math.pow((r.getPosY() - y), 2));

				if (tempDistance < distance) {
					// Closer than current closest
					distance = tempDistance;
					result = (AbstractEntity) r;
				}
			}
		}
		if (distance < delta) {
			return Optional.of(result);
		} else {
			return Optional.empty();
		}
	}
}