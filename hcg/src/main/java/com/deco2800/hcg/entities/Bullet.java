package com.deco2800.hcg.entities;


import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Box3D;

import java.util.List;

/**
 * A generic player instance for the game
 */
public class Bullet extends AbstractEntity implements Tickable {

    private float speed = 0.5f;

    private float goalX;
    private float goalY;

    private float angle;
    private float changeX;
    private float changeY;


    /**
     * Creates a new Bullet at the given position with the given direction.
     *
     * @param posX the x position of the bullet
     * @param posY the y position of the bullet
     * @param posZ the z position of the bullet
     * @param xd the y direction for the bullet
     * @param yd the x direction for the bullet
     */
    public Bullet(float posX, float posY, float posZ, float xd, float yd) {
        super(posX, posY, posZ, 0.6f, 0.6f, 1);
        this.setTexture("battle_seed");

        float projX;
        float projY;

        projX = xd / 55f;
        projY = -(yd - 32f / 2f) / 32f + projX;
        projX -= projY - projX;

        this.goalX = projX;
        this.goalY = projY;

        float deltaX = getPosX() - goalX;
        float deltaY = getPosY() - goalY;

        this.angle = (float) (Math.atan2(deltaY, deltaX)) + (float) (Math.PI);

        this.changeX = (float) (speed * Math.cos(angle));
        this.changeY = (float) (speed * Math.sin(angle));

    }

    /**
     * On Tick handler
     *
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        if (Math.abs(Math.abs(this.getPosX()) - Math.abs(goalX)) < 1
                && Math.abs(Math.abs(this.getPosY()) - Math.abs(goalY)) < 1) {
            GameManager.get().getWorld().removeEntity(this);
            GameManager.get().getWorld()
                    .addEntity(new Plant(this.goalX, this.goalY, 0));
        }
        setPosX(getPosX() + changeX);
        setPosY(getPosY() + changeY);
    }

    /**
     * Returns a boolean for collision between enemy and bullet
     *
     * @return True or false if bullet hits enemy
     */
    public boolean enemyHit(){
        Box3D pos = getBox3D();
        pos.setX(getPosX());
        pos.setY(getPosY());
        List<AbstractEntity> entities = GameManager.get().getWorld()
                .getEntities();
        for(AbstractEntity entity : entities){
            if (!this.equals(entity) && entity instanceof Squirrel
                    && pos.overlaps(entity.getBox3D())) {
                return true;
            }
        }
        return false;
    }
}
