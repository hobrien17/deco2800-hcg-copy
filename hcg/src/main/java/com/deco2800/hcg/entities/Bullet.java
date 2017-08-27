package com.deco2800.hcg.entities;


import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.Effects;
import com.deco2800.hcg.entities.Enemy;

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

    private AbstractEntity user;

    /**
     * Creates a new Bullet at the given position with the given direction.
     *
     * @param posX the x position of the bullet
     * @param posY the y position of the bullet
     * @param posZ the z position of the bullet
     * @param xd the y direction for the bullet
     * @param yd the x direction for the bullet
     */
    public Bullet(float posX, float posY, float posZ, float xd, float yd, AbstractEntity user) {
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

        this.user = user;
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

        enemyHit();
    }

    /**
     * Detects collision with entity and if enemy, apply effect of bullet.
     */
    public void enemyHit(){
        Box3D pos = getBox3D();
        pos.setX(getPosX());
        pos.setY(getPosY());
        List<AbstractEntity> entities = GameManager.get().getWorld()
                .getEntities();
        for(AbstractEntity entity : entities){
            if (entity instanceof Enemy
                    && this.collidesWith(entity) && user instanceof Player) {
                Enemy target = (Enemy) entity;
                target.causeEffect(new Effect("test", 2, 1, 0.0, 0));
                GameManager.get().getWorld().removeEntity(this);
                break;
            }
        }
    }
}
