package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.turrets.Explosion;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.WorldUtil;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

import java.util.HashMap;
import java.util.List;

public class Crab extends Enemy implements Tickable {

    private int explosionCounter;
    private boolean explosionSet;
    private Bullet explosionLocation;
    private int delay = 0;

    private String[] sprites = {"crabE", "crabN", "crabW", "crabS"};
    
    /**
     * Constructor for the Crab class. Creates a new crab boss at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param id the ID of the Hedgehog Enemy
     */
    public Crab(float posX, float posY, float posZ, int id) {
        super(posX, posY, posZ, 1f, 1f, 1, false, 12000, 10, id, EnemyType.CRAB);

        this.boss = true;
        // set texture
        this.level = 1;
        newPos.setX(posX);
        newPos.setY(posY);
        newPos.setZ(posZ);
        this.enemyWeapon = new WeaponBuilder()
                .setWeaponType(WeaponType.MACHINEGUN)
                .setUser(this)
                .setCooldown(50)
                .setTexture("battle_seed")
                .build();
        this.setStatus(2);
        this.setMovementSpeed((float) (this.movementSpeed*0.1));
        this.defaultSpeed = this.getMovementSpeed();

    }


    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("water_seed", 1.0f), 1.0);

        checkLootRarity();
    }

    public void delayedExplosion(float posX, float posY) {
        if(explosionSet) {
            if(explosionCounter >= 150) {
                Explosion explosion = new Explosion(explosionLocation.getPosX(),
                        explosionLocation.getPosY(),
                        explosionLocation.getPosZ(), 0.3f);
                GameManager.get().getWorld().addEntity(explosion);
                List<AbstractEntity> closest = WorldUtil.allEntitiesToPosition(explosionLocation.getPosX(),
                        explosionLocation.getPosY(), 2.5f, AbstractEntity.class);
                for(AbstractEntity close : closest) {
                    if(close instanceof Enemy) {
                        ((Enemy)close).giveEffect(new Effect("Explosion", 1, 200, 1, 0, 1, 0, this));
                    } else if(close instanceof Player) {
                        ((Player)close).giveEffect(new Effect("Explosion", 1, 200, 1, 0, 1, 0, this));
                    }
                }
                GameManager.get().getWorld().removeEntity(explosionLocation);
                explosionCounter = 0;
                explosionSet = false;
            } else if(explosionCounter % 50 == 0) {
                spawnParticles(explosionLocation, "warning.p");
                explosionCounter++;
            } else {
                explosionCounter++;
            }
        } else {
            explosionLocation = new Bullet(posX, posY, this.getPosZ(),
                    posX + 5, posY + 5, this.getPosZ(), 0.6f, 0.6f, 1, null, 1, 0, 0);
            GameManager.get().getWorld().addEntity(explosionLocation);
            spawnParticles(explosionLocation, "warning.p");
            explosionSet = true;
        }
    }

    /**
     * On Tick handler
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        if (this.getHealthCur() <= this.getHealthMax()*0.5){
            this.setMovementSpeed(this.defaultSpeed *3);
        }
        this.crab();
    }
    
    /**
     *  Logic for Crab
     *
     */
    private void crab(){
        this.setClosestPlayer(playerManager.getPlayer());
        if (this.getNumberPlayers() > 1) {
            findClosestPlayer();
            this.lastPlayerX = closestPlayer.getPosX();
            this.lastPlayerY = closestPlayer.getPosY();
            if(this.distance(closestPlayer) < 7){
                //open fire!!
            }
        } else {
            this.lastPlayerX = playerManager.getPlayer().getPosX();
            this.lastPlayerY = playerManager.getPlayer().getPosY();
            if(this.distance(playerManager.getPlayer()) < 7){
                //open fire!!
            }
        }
        
        if(delay >= 500) {
            delayedExplosion(lastPlayerX, lastPlayerY);
        } else {
            delay++;
        }
        
        //Set new position
        newPos = this.getToPlayerPos(closestPlayer);
        this.setDirection();
        this.updateSprite(sprites);
        this.detectCollision();
        this.moveAction();
        myEffects.apply();

    }
}
