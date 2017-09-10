package com.deco2800.hcg.entities.NPC_entities;


import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.util.Box3D;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The NPC class is to be implemented by Non-Player Characters (NPC) used in game
 *
 * This class extends the character class as the functionality provided there is built upon by the NPCs
 *
 * @author guthers
 */
public abstract class NPC extends Character implements Tickable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NPC.class);
    private String fName;
    private String sName;
   
    protected PlayerManager playerManager;

    
    /**
     * Constructor for an NPC entity 
     * 
     * @param posX the x position
     * @param posY the y position
     * @param fName NPC's first name
     * @param sName NPC's surname
     * @param texture NPC's texture
     */
    public NPC(float posX, float posY,String fName,String sName, String texture) {

        //Set up the parent constructor
        super(posX,posY,0,0.5f,0.5f,1.0f,false);

        //Set up the new NPC
        this.fName = fName;
        this.sName = sName;
        this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
        setTexture(texture);
    }

    /**
     * On Tick handler
     *
     * @param gameTickCount Current game tick
     */
    @Override
	public abstract void onTick(long gameTickCount);
    
    protected abstract void move();
    
    /**
     * Returns the NPC's first name
     * @return NPC's first name
     */
    public String getFirstName(){
    	return this.fName;
    }
    
    /**
     * Returns the NPC's last name
     * @return NPC's last name
     */
    public String getSurname(){
    	return this.sName;
    }
}