package com.deco2800.hcg.entities.npc_entities;

import java.util.List;

import com.badlogic.gdx.Input;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ConversationManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Box3D;

/**
 * Concrete class of a Quest NPC entity
 * 
 * @author Blake Bodycote
 *
 */
public class QuestNPC extends NPC {
	private float boundaryX; // defaults to 5
	private float boundaryY; // defaults to 5
	private int moveDirection; // defaults to 0;
	private int speed; // defaults to 1;
	private ConversationManager conversationManager;

	/**
	 * Constructs a new Quest NPC
	 * 
	 * @param posX
	 *            X Position of the NPC
	 * @param posY
	 *            Y positon of the NPC
	 * @param fName
	 *            first name of NPC
	 * @param sName
	 *            last name of NPC
	 * @param texture
	 *            texture of NPC
	 */
	public QuestNPC(float posX, float posY, String fName, String sName,
			String texture, String conversation, String faceImage) {
		super(posX, posY, fName, sName, texture, conversation, faceImage);

		this.boundaryX = 5;
		this.boundaryY = 5;
		moveDirection = 0;
		speed = 1;
		this.conversationManager = new ConversationManager();
	}
	
	public void interact(){
			conversationManager.startConversation(this.getConversation(),this.getFaceImage());
	}

	/**
	 * Moves the NPC
	 */
	protected void move() {
		// TODO actually get NPC moving again
		do {
			checkBoundaryPosition();
		} while (!collided());

	}

	/**
	 * Sets the boundary of the NPC
	 * 
	 * @param x
	 *            maximum distance from
	 * @param y
	 */
	public void setBoundary(float x, float y) {
		boundaryX = x;
		boundaryY = y;
	}

	/**
	 * Sets the speed for the NPC
	 * 
	 * @param speed
	 */
	public void setMovementSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Checks if the NPC has gone past the boundary and if it has, reverse the
	 * direction
	 */
	private void checkBoundaryPosition() {
		if (moveDirection == 0 && (getPosition().getY()
				- this.getInitialPosition().getY()) > boundaryY) {
			moveDirection = 1;
		}

		if (moveDirection == 1 && (getPosition().getY()
				+ this.getInitialPosition().getY()) < boundaryY) {
			moveDirection = 0;
		}

		if (moveDirection == 2 && (getPosition().getX()
				- this.getInitialPosition().getX()) > boundaryX) {
			moveDirection = 3;
		}

		if (moveDirection == 3 && (getPosition().getX()
				+ this.getInitialPosition().getX()) < boundaryX) {
			moveDirection = 2;
		}
	}

	/**
	 * Checks if a NPC has collided with something
	 * 
	 * @return true if NPC overlaps with another entity
	 */
	private boolean collided() {
		List<AbstractEntity> entities = GameManager.get().getWorld()
				.getEntities();
		for (AbstractEntity entity : entities) {
			if (!this.equals(entity)
					& getPosition().overlaps(entity.getBox3D())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the current position of the NPC
	 * 
	 * @return the NPC position in the Box3D form
	 */
	public Box3D getPosition() {
		return this.getBox3D();
	}

	@Override
	public void onTick(long gameTickCount) {
		// TODO get onTick stuff working

	}

}
