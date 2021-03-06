package com.deco2800.hcg.entities.npc_entities;

import com.deco2800.hcg.contexts.ShopMenuContext;
import com.deco2800.hcg.trading.GeneralShop;
import com.deco2800.hcg.trading.GardenShop;
import com.deco2800.hcg.trading.Shop;
import com.deco2800.hcg.trading.WeaponShop;

/**
 * Concrete implementation of a Shop NPC entity.
 * @author Blake
 *
 */
public class ShopNPC extends NPC {

    private Shop shop;

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
		super(posX, posY, fName, sName, texture, null);
		switch(sName) {
			case "George":
				shop = new GeneralShop();
				break;
			case "Plants":
				shop = new GardenShop();
				break;
			case "Weapons":
				shop = new WeaponShop();
				break;
		}
	}

    /**
     * Method to access the shop associated with this shopkeeper
     *
     * @return this shopkeeper's shop
     */
	public Shop getShop() {
	    return shop;
    }

	@Override
	public void interact() {
		Shop shop = this.getShop();
		shop.open(0, this.getPlayerManager().getPlayer());
		this.getContextManager().pushContext(new ShopMenuContext(this.getPlayerManager().getPlayer(), this));		
	}

}
