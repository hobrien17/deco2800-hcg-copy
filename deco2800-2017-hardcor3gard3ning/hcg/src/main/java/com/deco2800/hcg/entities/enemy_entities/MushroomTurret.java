package com.deco2800.hcg.entities.enemy_entities;


public class MushroomTurret extends Enemy {


    /**
     * Constructor for the Squirrel class. Creates a new squirrel at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param ID the ID of the squirrel
     */
    public MushroomTurret(float posX, float posY, float posZ, int ID) {
        super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, ID);
        //this.setTexture();
        this.level = playerManager.getPlayer().getLevel();
    }

    @Override
    void setupLoot() {

    }
}
