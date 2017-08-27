package com.deco2800.hcg.entities;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.util.Box3D;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * A generic player instance for the game
 */
public class Squirrel extends Enemy implements Tickable {

	private float speed = 0.03f;
	
	private Random random;

	public Squirrel(float posX, float posY, float posZ, int ID) {
		super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, ID);
		this.setTexture("squirrel");
		this.random = new Random();
		this.level = 1;
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
		if(this.detectPlayer()) {
			this.moveToPlayer();
		} else {
			this.randomMove();
		}
		Box3D newPos = getBox3D();
		newPos.setX(this.getPosX());
		newPos.setY(this.getPosY());
		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		boolean collided = false;
		for (AbstractEntity entity : entities) {
			if (!this.equals(entity) & newPos.overlaps(entity.getBox3D())) {
				if(entity instanceof Player) {
					this.causeDamage((Player)entity);
				}
				collided = true;
			}
		}
		if (!collided) {
            if(this.detectPlayer()) {
                this.moveToPlayer();
            } else {
                this.randomMove();
            }
		}
		// Apply any effects that exist on the entity
		myEffects.apply();
	}
}