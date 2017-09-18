package com.deco2800.hcg.entities.enemy_entities;

import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.weapons.*;

import java.util.HashMap;

/**
 * A generic player instance for the game
 */
public class Squirrel extends Enemy implements Tickable {


	// private float speed = 0.03f;
	//private boolean collided;

	/**
	 * Constructor for the Squirrel class. Creates a new squirrel at the given
	 * position.
	 *
	 * @param posX the x position
	 * @param posY the y position
	 * @param posZ the x position
	 * @param ID the ID of the squirrel
	 */
	public Squirrel(float posX, float posY, float posZ, int ID) {
		super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, ID);
		this.setTexture("squirrel");
		this.level = 1;
		this.enemyWeapon = new WeaponBuilder()
				.setWeaponType(WeaponType.MACHINEGUN)
				.setUser(this)
				.setCooldown(50)
				.setTexture("battle_seed")
				.build();
	}

	@Override
	public void setupLoot() {
		lootRarity = new HashMap<>();

		lootRarity.put("sunflower_seed", 1.0);

		checkLootRarity();
	}

	@Override
	public Item[] loot() {
		Item[] arr = new Item[1];
		arr[0] = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());
		return arr;
	}

	/**
	 * On Tick handler
	 * @param gameTickCount Current game tick
	 */
	@Override
	public void onTick(long gameTickCount) {
		this.detectPlayer();//Change status if player detected.
        this.setNewPos();//Put new position into Box3D.
		this.detectCollision();//Detect collision.
        this.moveAction();//Move enemy to the position in Box3D.
		// Apply any effects that exist on the entity
		myEffects.apply();
	}
}