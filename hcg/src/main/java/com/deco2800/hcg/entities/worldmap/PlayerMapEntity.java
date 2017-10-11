package com.deco2800.hcg.entities.worldmap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

public class PlayerMapEntity extends Actor {
    GameManager gameManager;
    private Texture playerTexture;
    private TextureManager textureManager;
    private int xPos = 0;
    private int yPos = 0;
    private int spriteWidth = 30; // Used to scale the drawing of the mapNodes. (pixels), change this until fits
    private int spriteHeight; // Will be calculated based on the above width
    public PlayerMapEntity() {
        gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        playerTexture = textureManager.getTexture("player_0_stand");

    }

    /**
     * update the playerMapEntity position in the grid layout, center and above the MapNodeEntity
     *
     *
     */
    public void updatePosByNodeEntity(MapNodeEntity mapNodeEntity) {
        // Calculates the nodes position in the grid layout, trying to place center and above the node's rendering
        xPos =  Math.round(mapNodeEntity.getXPos() + mapNodeEntity.getWidth() / 2 - spriteWidth / 2);
        // move it up horizontally by the node entity sprite height
        yPos = Math.round(mapNodeEntity.getYPos() + mapNodeEntity.getHeight() / 2);
        // Calculate the scaling required on the sprite height.
        spriteHeight = playerTexture.getHeight() / (playerTexture.getWidth() / spriteWidth);
    }

    /**
     * Gets the PlayerMapEntity's screen x position
     * @return the x co ordinate
     */
    public float getXPos() {
        return xPos;
    }

    /**
     * Gets the PlayerMapEntity's screen y position
     * @return the y co ordinate
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
     * Get the PlayerMapEntity's texture
     * @return the node's texture
     */
    public Texture getPlayerTexture() {
        return playerTexture;
    }





}
