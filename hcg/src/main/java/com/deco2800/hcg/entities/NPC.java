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
public abstract class NPC extends Character implements Clickable {
    public enum Type {
        Shop,
        Quest
    }

    public String fName;
    public String sName;
    public NPC.Type NPCType;


    public NPC(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
               boolean centered,String fName,String sName,NPC.Type NPCType,String texture) {

        //Set up the parent constructor
        super(posX,posY,posZ,xLength,yLength,zLength,centered);

        //Set up the new NPC
        this.fName = fName;
        this.sName = sName;
        this.NPCType = NPCType;

        //We want specific NPCs to have set speeds
        if (NPCType == Type.Shop) {
            this.movementSpeed = 0;
        } else if (NPCType == Type.Quest) {
            this.movementSpeed = 0.01f;
        } else {
            //Set all other NPCs to some default speed
            this.movementSpeed = 0.02f;
        }

        System.out.println("foo");
        setTexture(texture);

    }

    private void interact() {
        //What do we do with this NPC on click?
        if (NPCType == Type.Shop) {
            //Open Shop Menu
            System.out.print("NPC SHOP CLICKED");
        } else if (NPCType == Type.Quest) {
            System.out.print("NPC QUEST CLICKED");
        } else {
            System.out.print("NPC OTHER CLICKED");
        }
    }

    //Currently being used of a way to interact with NPCs
    @Override
    public void onClick() {
        interact();
    }
}