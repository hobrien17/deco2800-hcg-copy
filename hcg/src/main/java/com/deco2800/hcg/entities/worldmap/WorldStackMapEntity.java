package com.deco2800.hcg.entities.worldmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

public class WorldStackMapEntity extends Actor {
	private Texture worldStackMapTexture;
    private int xPos;
    private int yPos;

	private TextureManager textureManager;

    private WorldMap worldMap;

    private int spriteWidth = 125; // Used to scale the drawing of the mapNodes. (pixels)
    private int spriteHeight; // Will be calculated based on the above width


	// Constants
	private static final String SAFE_NODE = "safe_node";
	private static final String DISCOVERED_NODE = "discovered_node";

    public WorldStackMapEntity(WorldMap worldMap) {
    	this.worldMap = worldMap;
		GameManager gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        
        //Assign texture
        updateTexture();
        
        // pixels padding around each direction of the map
     	int mapPadding = 100;

        // Grab the current viewport dimensions
        int viewPortX = Gdx.graphics.getWidth();
        int viewPortY = Gdx.graphics.getHeight();

        // Calculates the spacing between cells
        int renderableColWidth = (viewPortX - mapPadding) / gameManager.getWorldStack().getNumberOfWorlds();

        // Calculates the nodes position in the grid layout
        xPos = renderableColWidth * worldMap.getWorldPosition() + mapPadding;
        yPos = viewPortY/2;

        // Calculate the scaling required on the sprite height.
        spriteHeight = worldStackMapTexture.getHeight() / (worldStackMapTexture.getWidth() / spriteWidth);
    }
    
    /**
	 * Checks the current biome in the parent world map of this WorldStackMapEntity, and updates the stored texture to 
	 * reflect.
	 */
	public void updateTexture(){
		switch (worldMap.getWorldType()) {
			case 0: 
				//no set biome
				worldStackMapTexture = textureManager.getTexture(SAFE_NODE);
				break;
			case 1:
				//desert biome
				worldStackMapTexture = textureManager.getTexture(DISCOVERED_NODE);
				break;
			case 2:
				//snow biome
				worldStackMapTexture = textureManager.getTexture(DISCOVERED_NODE);
				break;
			case 3:
				//jungle biome
				worldStackMapTexture = textureManager.getTexture(SAFE_NODE);
				break;
			case 4:
				//urban biome
				worldStackMapTexture = textureManager.getTexture(DISCOVERED_NODE);
				break;
			case 5:
				//fungus biome
				worldStackMapTexture = textureManager.getTexture(SAFE_NODE);
				break;
			default: // This shouldn't happen, but catch all if it does.
				worldStackMapTexture = textureManager.getTexture(DISCOVERED_NODE);
		}
	}
	
	/**
	 * Gets the world map stored in this actor object
	 *
	 * @return the stored world map
	 */
	public WorldMap getWorldMap() {
        return worldMap; }
	/**
	 * Gets the MapNodeEntity's screen x position
	 * @return the x co ordinate
	 */
	public float getXPos() {
		return xPos;
	}

	/**
	 * Gets the WorldStackMapEntity's screen y position
	 * @return the y coordinate
	 */
	public float getYPos() {
		return yPos;
	}

	/**
	 * Gets the sprite's width
	 * @return the width
	 */
	public float getWidth() {
		return spriteWidth;
	}

	/**
	 * Gets the sprite's height
	 * @return the height
	 */
	public float getHeight() {
		return spriteHeight;
	}

	/**
	 * Get the world stack's map entity texture
	 * @return the texture
	 */
	public Texture getWorldTexture() {
		return worldStackMapTexture;
	}
	
	public void setWorldTexture(Texture newTexture) {
		worldStackMapTexture = newTexture;
	}
	
	@Override
    public void draw(Batch batch, float alpha){
        batch.draw(worldStackMapTexture, xPos, yPos, spriteWidth, spriteHeight);
    }
}
