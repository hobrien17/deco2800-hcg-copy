package com.deco2800.hcg.entities;


import com.deco2800.hcg.managers.GameManager;


/**
 * A generic player instance for the game
 */
public class Bullet extends AbstractEntity implements Tickable {

    private float speed = 2f;


    private float goalX;
    private float goalY;

    private float angle;
    private float changeX;
    private float changeY;


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
}
