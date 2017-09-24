package com.deco2800.hcg.entities.terrain_entities;

public class House2 extends TerrainEntity {

	/**
	 * Creates a new WallBlock at the given position with the given size
	 * parameters.
	 *
	 * @param posX
	 *            the x position
	 * @param posY
	 *            the y position
	 * @param posZ
	 *            the z position
	 */
	public House2(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 7f, 7f, 0f);
		this.setTexture("house2");
	}
}
