package com.deco2800.hcg.entities;

/**
 * The NPC class is to be implemented by Non-Player Characters (NPC) used in game
 *
 * This class extends the character class as the functionality provided there is built upon by the NPCs
 *
 *
 *
 *
 *
 *
 * @author guthers
 */
public abstract class NPC extends Character {
    public String Fname;
    public String SName;







    public NPC(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
               boolean centered) {
        super(posX,posY,posZ,xLength,yLength,zLength,centered);

    }

}


