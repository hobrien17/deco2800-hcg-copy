package com.deco2800.hcg.entities;


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
 *
 *
 *
 *
 *
 * @author guthers
 */

public abstract class NPC extends Character implements Tickable {

    public enum Type {
        Shop,
        Quest
    }


    public enum movementType {
        Stationary,
        shortWander,
        longWander
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(NPC.class);
    public String fName;
    public String sName;
    public NPC.Type NPCType;

    public NPC.movementType NPCMoveType;
    private PlayerManager playerManager;
    private Random random;

    private float shortWander_xMin = 0.0f;
    private float shortWander_yMin = 0.0f;
    private float shortWander_xMax = 0.0f;
    private float shortWander_yMax = 0.0f;
    private int moveDirection = 0;

    public NPC(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
               boolean centered,String fName,String sName,NPC.Type NPCType,String texture) {

        //Set up the parent constructor
        super(posX,posY,posZ,xLength,yLength,zLength,centered);

        //Set up the new NPC
        this.fName = fName;
        this.sName = sName;
        this.NPCType = NPCType;

        this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
        this.random = new Random();

        //We want specific NPCs to have set speeds
        if (NPCType == Type.Shop) {
            this.movementSpeed = 0.0f;
        } else if (NPCType == Type.Quest) {
            this.NPCMoveType = movementType.shortWander;
            this.movementSpeed = 0.01f;
        } else {
            //Set all other NPCs to some default speed
            this.movementSpeed = 0.02f;
        }

        //Set Short Wander movement bounds (5 x 5 tile grid)
        if (this.NPCMoveType == movementType.shortWander) {
            shortWander_xMin = posX - 2.5f;
            shortWander_yMin = posY - 2.5f;
            shortWander_xMax = posX + 2.5f;
            shortWander_yMax = posY + 2.5f;
        }

        setTexture(texture);

    }

    private void interact() {
        //What do we do with this NPC on click?
        if (NPCType == Type.Shop) {
            //Open Shop Menu
            LOGGER.info("NPC SHOP CLICKED");
        } else if (NPCType == Type.Quest) {
            LOGGER.info("NPC QUEST CLICKED");
        } else {
            LOGGER.info("NPC OTHER CLICKED");
        }
    }

    /**
     * On Tick handler
     *
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        if (this.NPCMoveType == movementType.Stationary) {
            //No need to move
            return;
        }

        //Default to short wander at the moment.

        Box3D newPos = getBox3D();
        float changeX = 0.0f;
        float changeY = 0.0f;
        boolean collided = false;
        int timeout = 100;
        int timeout_count = 0;

        //Calculate new valid direction for short wander, timeout and remain stationary if not possible.
        do {
            collided = false;
            timeout_count++;
            if (timeout_count > timeout) {
                changeX = 0.0f;
                changeY = 0.0f;
                break;
            }

            switch (this.moveDirection) {
                //Up
                case 0:
                    changeX = 0.0f;
                    changeY = -this.movementSpeed;
                    break;

                //Down
                case 1:
                    changeX = 0.0f;
                    changeY = this.movementSpeed;
                    break;

                //Left
                case 2:
                    changeX = this.movementSpeed;
                    changeY = 0.0f;
                    break;

                //Right
                case 3:
                    changeX = -this.movementSpeed;
                    changeY = 0.0f;
                    break;
            }

            //Stop when in close proximity to player
            //Currently bugged, one or both of this coordinates if offset significantly to the left.
            if (this.distance(playerManager.getPlayer()) < 2.5f) {
                changeX = 0.0f;
                changeY = 0.0f;
                return;
            }

            //Calculate new position
            newPos.setX(getPosX() + changeX);
            newPos.setY(getPosY() + changeY);

            //Search for collisions
            List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
            for (AbstractEntity entity : entities) {
                if (!this.equals(entity) & newPos.overlaps(entity.getBox3D())) {
                    if (entity instanceof Player) {
                        //Collided With Player - Stop
                        changeX = 0.0f;
                        changeY = 0.0f;
                    }
                    collided = true;
                }
            }

            //Calculate new valid direction
            if (collided || newPos.getX() > this.shortWander_xMax || newPos.getX() < this.shortWander_xMin ||
                    newPos.getY() > this.shortWander_yMax || newPos.getY() < this.shortWander_yMin) {
                this.moveDirection = this.random.nextInt() % 4;
            }

        } while (collided || newPos.getX() > this.shortWander_xMax || newPos.getX() < this.shortWander_xMin ||
                newPos.getY() > this.shortWander_yMax || newPos.getY() < this.shortWander_yMin);

        //Move NPC
        if (!collided) {
            setPosX(getPosX() + changeX);
            setPosY(getPosY() + changeY);
        }

    }

}