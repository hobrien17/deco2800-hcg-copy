package com.deco2800.hcg.entities.terrain_entities;

public class House extends TerrainEntity {

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
	public House(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 5f, 5f, 0.5f);
		System.out.print("run1");
		this.setTexture("house");
	}
}
