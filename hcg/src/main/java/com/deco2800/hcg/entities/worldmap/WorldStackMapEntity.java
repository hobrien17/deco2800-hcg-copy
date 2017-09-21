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

    private float spriteWidth = 175; // Used to scale the drawing of the mapNodes. (pixels)
    private float spriteHeight; // Will be calculated based on the above width
    
    public WorldStackMapEntity(WorldMap worldMap) {
    	this.worldMap = worldMap;
		GameManager gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        
        //Assign texture
        updateTexture();
        
        // pixels padding around each direction of the map
     	int mapPadding = 175;

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
				worldStackMapTexture = textureManager.getTexture("safe_node");
				break;
			case 1:
				//suburbs biome
				worldStackMapTexture = textureManager.getTexture("discovered_node");
				break;
			case 2:
				//desolate forest biome
				worldStackMapTexture = textureManager.getTexture("discovered_node");
				break;
			case 3:
				//fungal wasteland biome
				worldStackMapTexture = textureManager.getTexture("safe_node");
				break;
			default: // This shouldn't happen, but catch all if it does.
				worldStackMapTexture = textureManager.getTexture("discovered_node");
		}
	}
	
	/**
	 * Gets the world map stored in this actor object
	 *
	 * @return the stored world map
	 */
	public WorldMap getWorldMap() {
        return worldMap;
    }
	
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
