package com.deco2800.hcg.entities.npc_entities;


import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.util.Box3D;

/**
 * The NPC class is to be implemented by Non-Player Characters (NPC) used in game
 *
 * This class extends the character class as the functionality provided there is built upon by the NPCs
 *
 * @author guthers, Blake Bodycote
 */
public abstract class NPC extends Character implements Tickable {

    private String fName;
    private String sName;
    private final Box3D initialPosition;
    protected PlayerManager playerManager;
    protected ContextManager contextManager;
    private String faceImage;
    

    
    /**
     * Constructor for an NPC entity 
     * 
     * @param posX the x position
     * @param posY the y position
     * @param fName NPC's first name
     * @param sName NPC's surname
     * @param texture NPC's texture
     */
    public NPC(float posX, float posY,String fName,String sName, String texture, String faceImage) {

        //Set up the parent constructor
        super(posX,posY,0,0.5f,0.5f,1.0f,false);

        //Set up the new NPC
        this.fName = fName;
        this.sName = sName;
        this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
        this.contextManager = (ContextManager)GameManager.get().getManager(ContextManager.class);
        this.initialPosition = new Box3D(posX, posY, 0, 0, 0, 0);
        setTexture(texture);
        this.growRender(-0.2f,-0.2f);

        this.faceImage = faceImage;
    }
    

    
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
    
    public String getFaceImage(){
    	return this.faceImage;
    }
    
    public PlayerManager getPlayerManager(){
    	return this.playerManager;
    }
    
    public ContextManager getContextManager(){
    	return this.contextManager;
    }
    
    /**
     * Returns the initial position of the NPC when spawned
     * @return Box3D representation of first spawn location
     */
    public Box3D getInitialPosition(){
    	return this.initialPosition;
    }
    
    public abstract void interact();
    
    @Override
    public boolean equals(Object object){
    	if(!(object instanceof NPC)){
    		return false;
    	}
    	NPC npc = (NPC) object;
    	if(!getFirstName().equals(npc.getFirstName())){
    		return false;
    	}
    	if(!getSurname().equals(npc.getSurname())){
    		return false;
    	}
    	return getInitialPosition().equals(npc.getInitialPosition());
    	}
    
    
    @Override
    public int hashCode(){
    	int result = 13;
    	result = 17*result + this.getFirstName().hashCode();
    	result = 17*result + this.getSurname().hashCode();
    	result = 17*result + this.getInitialPosition().hashCode();
    	return result;
    }
    
}