package com.deco2800.hcg.entities.worldmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * MapNodeEntity creates each node in the WorldMap as a renderable actor. Contains the sprite texture to render for
 * each Node, as well as sets the positional co-ordinates and padding.
 *
 * @author jakedunn
 */
public class MapNodeEntity extends Actor {
    private Texture nodeTexture;
    private int xPos;
    private int yPos;

    private MapNode node;

    private int spriteWidth = 50; // Used to scale the drawing of the mapNodes. (pixels)
    private int spriteHeight; // Will be calculated based on the above width

	/**
	 * Constructor decides which texture to render, and calculates the correct scaling and screen position to render.
	 *
	 * @param node the node to render
	 */
    public MapNodeEntity(MapNode node) {

        this.node = node;
        GameManager gameManager = GameManager.get();
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

        // Assigns the correct texture based on node type
        switch (node.getNodeType()) {
            case 0: nodeTexture = textureManager.getTexture("safe_node");
                break;
            case 1: nodeTexture = textureManager.getTexture("discovered_node");
                break;
            case 2: nodeTexture = textureManager.getTexture("completed_node");
                break;
            case 3: nodeTexture = textureManager.getTexture("fungi_node");
                break;
            default: // This shouldn't happen, but catch all if it does.
                nodeTexture = textureManager.getTexture("discovered_node");
        }

		// pixels padding around each direction of the map
		int mapPadding = 50;

        // Grab the current viewport dimensions
        int viewPortX = Gdx.graphics.getWidth();
        int viewPortY = Gdx.graphics.getHeight();

        // Calculates the spacing between cells
        int renderableColWidth = (viewPortX - mapPadding) / gameManager.getWorldMap().getWorldColumns();
        int renderableRowWidth = (viewPortY - mapPadding) / gameManager.getWorldMap().getWorldRows();

		// Calculates the nodes position in the grid layout
        xPos = renderableColWidth * node.getNodeColumn() + mapPadding;
        yPos = renderableRowWidth * node.getNodeRow() + mapPadding;

        // Calculate the scaling required on the sprite height.
        spriteHeight = nodeTexture.getHeight() / (nodeTexture.getWidth() / spriteWidth);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(nodeTexture, xPos, yPos, spriteWidth, spriteHeight);
    }

	/**
	 * Gets the node stored in this actor object
	 *
	 * @return the stored node
	 */
	public MapNode getNode() {
        return node;
    }

	public float getXPos() {
		return xPos;
	}
	
	public float getYPos() {
		return yPos;
	}
	
	public float getWidth() {
		return spriteWidth;
	}
	
	public float getHeight() {
		return spriteHeight;
	}
}