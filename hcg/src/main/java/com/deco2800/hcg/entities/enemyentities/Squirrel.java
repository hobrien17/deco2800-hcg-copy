package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.weapons.*;
import java.util.HashMap;

/**
 * A generic player instance for the game
 */
public class Squirrel extends Enemy implements Tickable {

	private int spriteCount;
	private int counter;
	private int delay;

	/**
	 * Constructor for the Squirrel class. Creates a new squirrel at the given
	 * position.
	 *
	 * @param posX the x position
	 * @param posY the y position
	 * @param posZ the x position
	 * @param Id the ID of the squirrel
	 */
	public Squirrel(float posX, float posY, float posZ, int id) {
		super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, id, EnemyType.SQUIRREL);
		this.boss = false;
		this.setTexture("antSW");
		this.level = 1;
		this.spriteCount = 0;
		this.delay = 20;
		this.counter = 20;
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

		lootRarity.put(new LootWrapper("sunflower_seed"), 1.0);

		checkLootRarity();
	}

	public void updateSprite() {
		if (spriteCount%4 == 0) {
			switch (this.direction) {
				case 1:
					if (this.getTexture() == "antE") {
						this.setTexture("antE2");
					} else {
						this.setTexture("antE");
					}
					break;
				case 2:
					if (this.getTexture() == "antN") {
						this.setTexture("antN2");
					} else {
						this.setTexture("antN");
					}
					break;
				case 3:
					if (this.getTexture() == "antW") {
						this.setTexture("antW2");
					} else {
						this.setTexture("antW");
					}
					break;
				case 4:
					if (this.getTexture() == "antS") {
						this.setTexture("antS2");
					} else {
						this.setTexture("antS");
					}
					break;
				default:
					break;
			}
		}
		spriteCount++;
	}

	/**
	 * On Tick handler
	 * @param gameTickCount Current game tick
	 */
	@Override
	public void onTick(long gameTickCount) {
		this.detectPlayers();//Change status if player detected.
		this.setNewPos();//Put new position into Box3D.
		this.setDirection();
		this.detectCollision();//
		if (this.counter < this.delay) {
			this.counter++;
		} else if (this.collidedPlayer) {
			this.causeDamage(this.getTarget());
			this.counter = 0;
		}
		this.updateSprite();
		this.moveAction();//Move enemy to the position in Box3D.
		// Apply any effects that exist on the entity
		myEffects.apply();
	}
}