package com.deco2800.hcg.entities.NPC_entities;

public class ShopNPC extends NPC{

	/**
	 * Constructs a new Shop NPC
	 * 
	 * @param posX X Position of the NPC
	 * @param posY Y position of the NPC
	 * @param fName first name of NPC
	 * @param sName last name of NPC
	 * @param texture texture of NPC
	 */
	public ShopNPC(float posX, float posY, String fName, String sName, String texture) {
		super(posX, posY, fName, sName, texture);
	}

	@Override
	public void onTick(long gameTickCount) {
		
	}

	@Override
	protected void move() {
		
	}

}
