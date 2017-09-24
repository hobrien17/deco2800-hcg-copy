package com.deco2800.hcg.entities.npc_entities;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ConversationManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.PathfindingThread;
import com.deco2800.hcg.util.Point;
import org.lwjgl.Sys;

/**
 * Concrete class of a Quest NPC entity
 * 
 * @author Blake Bodycote & Ryan Lonergan
 *
 */
public class QuestNPC extends NPC {
	private float boundaryX; // defaults to 15
	private float boundaryY; // defaults to 15
	private float startX;
	private float startY;
	private float goalX;
	private float goalY;
	private int moveDirection; // defaults to 0;
	private float speed; // defaults to 1;

	private PathfindingThread pathfinder;
	private Thread thread;
	private List<Point> path;
	private Boolean astar_debug = true;

	private ConversationManager conversationManager;
	private TimeManager timemanager = (TimeManager) GameManager.get().getManager(TimeManager.class);
	private PlayerManager playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);

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

		this.startX = posX;
		this.startY = posY;
		this.boundaryX = 15;
		this.boundaryY = 15;
		this.moveDirection = 0;
		this.speed = 0.02f;
		this.conversationManager = new ConversationManager();

		this.setGoal(posX, posY);
	}
	
	public void interact(){
			conversationManager.startConversation(this.getConversation(),this.getFaceImage());
	}

	/**
	 * Moves the NPC
	 */
	protected void move() {

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
	 * Detects if any player is within a set margin of the NPC.
	 *
	 * @return True if player is within 2.5 units of a NPC.
	 */
	private boolean detectPlayer() {

		for (Player player : playerManager.getPlayers()) {
			float playerDistance = this.distance(player);

			if (playerDistance < 2.5f) {
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
		if (thread.isAlive()) {
			return;
		} else {
			path = pathfinder.getPath();
		}

		if (path != null && !astar_debug) {
			System.err.println(path.toString());
			astar_debug = true;
		}

		//Movement Completed (most likely)
		if (path != null && path.isEmpty()) {
			Random random = new Random();

			float newGoalX = (random.nextFloat() - 0.5f) * this.boundaryX + this.startX;
			float newGoalY = (random.nextFloat() - 0.5f) * this.boundaryY + this.startY;

			this.setGoal(newGoalX, newGoalY);
			return;
		}

		if (path != null && !path.isEmpty()) {
			//Move towards next point
			float tmpGoalX = path.get(0).getX();
			float tmpGoalY = path.get(0).getY();

			if (Math.abs(this.getPosX() - tmpGoalX) < speed && Math.abs(this.getPosY() - tmpGoalY) < speed) {
				this.setPosX(tmpGoalX);
				this.setPosY(tmpGoalY);
				path.remove(0);
				return;
			}

			/* Calculate a deltaX and Y to move based on polar coordinates and speed to ensure
				speed is constant regardless of direction
			 */
			float deltaX = this.getPosX() - tmpGoalX;
			float deltaY = this.getPosY() - tmpGoalY;
			float angle = (float) (Math.atan2(deltaY, deltaX)) + (float) (Math.PI);
			float changeX = (float) (speed * Math.cos(angle));
			float changeY = (float) (speed * Math.sin(angle));
			float newX = this.getPosX() + changeX;
			float newY = this.getPosY() + changeY;

			/* Apply these values to the entity */
			if (!collided() && !detectPlayer()) {
				this.setPosX(newX);
				this.setPosY(newY);
			}

		}

	}

	/**
	 * Set the immediate location goal for the NPC
	 *
	 * @param goalX desired x position
	 * @param goalY desired y position
	 */
	private void setGoal(float goalX, float goalY) {
		this.goalX = goalX;
		this.goalY = goalY;

		pathfinder = new PathfindingThread(GameManager.get().getWorld(), new Point(this.getPosX(), this.getPosY()),
				new Point(this.goalX, this.goalY));

		thread = new Thread(pathfinder);
		thread.start();
	}

}
