package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.turrets.Explosion;

/**
 * A generic player instance for the game
 */
public class Grenade extends Bullet implements Tickable {

    private Explosion explosion;
    private boolean activated;
    /**
     * Creates a new bullet of specified dimensions moving towards the given
     * co-ordinates
     *
     * @param posX
     *            the x position of the bullet
     * @param posY
     *            the x position of the bullet
     * @param posZ
     *            the x position of the bullet
     * @param newX
     *            the x goal of the bullet
     * @param newY
     *            the y goal of the bullet
     * @param newZ
     *            the z goal of the bullet
     * @param xLength
     *            the size of the bullet in the x-direction
     * @param yLength
     *            the size of the bullet in the y-direction
     * @param zLength
     *            the size of the bullet in the z-direction
     * @param user
     *            the entity using the bullet
     * @param hitCount
     *            the total number of enemies that can be hit
     */
    public Grenade(float posX, float posY, float posZ, float newX, float newY,
                   float newZ, float xLength, float yLength, float zLength,
                   AbstractEntity user, int hitCount) {
        super(posX, posY, posZ, newX, newY, newZ, xLength, yLength, zLength, user, -1);
        this.activated = false;
        this.bulletType = BulletType.GRENADE;
    }

    /**
     * On Tick handler
     *
     * @param gameTickCount
     *            Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        if (Math.abs(Math.abs(this.getPosX() + this.getXLength()/2)
                - Math.abs(goalX)) < 0.5
                && Math.abs(Math.abs(this.getPosY() + this.getYLength()/2)
                - Math.abs(goalY)) < 0.5 && !activated) {
            explosion = new Explosion(goalX, goalY, this.getPosZ(), 0.3f);
            GameManager.get().getWorld().addEntity(explosion);
            activated = true;
            // Play explosion sound
            playCollisionSound(this);
        }

        if(activated) {
            if(GameManager.get().getWorld().containsEntity(explosion)
                    && explosion.getRateOfChange() >= 0) {
                this.setPosX(goalX);
                this.setPosY(goalY);
                Bullet aoe = new Bullet(goalX - explosion.getXRenderLength()/2,
                        goalY - explosion.getYRenderLength()/2,
                        this.getPosZ(), goalX, goalY, this.getPosZ(),
                        explosion.getXRenderLength(), explosion.getYRenderLength(), explosion.getZLength(),
                        this.user, -1);
                GameManager.get().getWorld().addEntity(aoe);
            } else {
                GameManager.get().getWorld().removeEntity(this);
            }
        } else {
            setPosX(getPosX() + changeX);
            setPosY(getPosY() + changeY);
        }
    }
}