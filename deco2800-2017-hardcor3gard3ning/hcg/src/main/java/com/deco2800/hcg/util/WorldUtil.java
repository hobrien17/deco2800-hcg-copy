package com.deco2800.hcg.util;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.renderers.Renderable;

import java.util.ArrayList;
import java.util.List;
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
		List<AbstractEntity> entities = allEntitiesToPosition(x, y, delta, type);
		if (entities.isEmpty()) {
			return Optional.empty();
		}
		double distance = Double.MAX_VALUE;
		AbstractEntity result = null;
		for (AbstractEntity entity : entities) {
			double tempDistance = getDistance(entity, x, y);
			if (tempDistance < distance) {
				distance = tempDistance;
				result = entity;
			}
		}
		return Optional.of(result);
	}

	public static List<AbstractEntity> allEntitiesToPosition(float x, float y, float delta,
			Class<? extends AbstractEntity> type) {
		List<AbstractEntity> ret = new ArrayList<>();
		AbstractEntity entity = null;
		double distance = Double.MAX_VALUE;
		for (Renderable r : GameManager.get().getWorld().getEntities()) {
			if (type.isInstance(r)) {
				entity = (AbstractEntity) r;
				distance = getDistance(entity, x, y);
				if (distance < delta) {
					ret.add(entity);
				}
			}
		}

		return ret;
	}

	private static double getDistance(AbstractEntity entity, float x, float y) {
		return Math.sqrt(Math.pow((entity.getPosX() - x), 2) + Math.pow((entity.getPosY() - y), 2));
	}
}