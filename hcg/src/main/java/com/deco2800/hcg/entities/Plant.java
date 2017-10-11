package com.deco2800.hcg.entities;

/**
 * Tower that can do things.
 *
 * @author leggy
 */
public class Plant extends AbstractEntity implements Tickable {


    /**
     * Constructor for the base
     *
     * @param posX The x-coordinate.
     * @param posY The y-coordinate.
     * @param posZ The z-coordinate.
     */
    public Plant(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
        this.setTexture("plant_01");
    }


    /**
     * On Tick handler
     *
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        throw new UnsupportedOperationException();
    }
}
