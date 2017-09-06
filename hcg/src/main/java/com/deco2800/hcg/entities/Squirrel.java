package com.deco2800.hcg.entities;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.worlds.AbstractWorld;

import java.util.HashMap;
import java.util.List;

/**
 * A generic player instance for the game
 */
public class Squirrel extends Enemy implements Tickable {

	// private float speed = 0.03f;
	private boolean collided;

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
		this.detectPlayer();
		Box3D newPos;
		if(this.getStatus() == 1) {
			newPos = this.randomMove();
		} else if (this.getStatus() == 2){
			newPos = this.moveToPlayer();
		} else if (this.getStatus() == 3){
			newPos = this.moveTo(this.getLastPlayerX(), this.getLastPlayerY());
		} else {
			newPos = getBox3D();
			newPos.setX(this.getPosX());
			newPos.setY(this.getPosY());
		}
		collided = false;
		AbstractWorld world = GameManager.get().getWorld();
		if (world.getTiledMapTileLayerAtPos((int) newPos.getX(), (int) newPos.getY()) == null) {
			collided = true;
		}
		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		for (AbstractEntity entity : entities) {
			if (!this.equals(entity) && newPos.overlaps(entity.getBox3D())) {
				if(entity instanceof Player) {
					this.causeDamage((Player)entity);
				}
				collided = true;
			}
		}
		if (!collided) {
			this.move(newPos.getX(), newPos.getY());
		}
		// Apply any effects that exist on the entity
		myEffects.apply();
	}
}