package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TimeManager;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.util.Box3D;

/**
 * Represents a plant in the garden Used as a shell for more specific types of
 * plants
 *
 * @author Henry O'Brien
 */
public abstract class AbstractGardenPlant implements Lootable {

    static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    /**
     * Represents a stage of growth Can be either SPROUT, SMALL, or LARGE New
     * plants should start as SPROUTs
     *
     * More stages can be added if needed (remember to update relevant methods)
     */
    enum Stage {
        SPROUT,
        SMALL,
        LARGE
    }

    private Stage stage;
    private Pot master;
    private int lastGrow;
    private int growDelay;

    Map<String, Double> lootRarity;

    public AbstractGardenPlant(Pot master, int delay) {
        this.stage = Stage.SPROUT;
        growDelay = delay;
        this.lastGrow = ((TimeManager)GameManager.get().getManager(TimeManager.class)).getTimeElapsed();
        this.master = master;
        setupLoot();
    }
    
    /**
     * Checks if the plant is ready for growing, and advances a stage if it is
     */
    public void checkGrow() {
        TimeManager tm = (TimeManager)GameManager.get().getManager(TimeManager.class);
        int time = tm.getTimeElapsed();
        if (time - lastGrow >= growDelay) {
        	lastGrow = time;
        	this.advanceStage();
        }
    }

    @Override
    public Map<String, Double> getRarity() {
        return lootRarity;
    }

    /**
     * Gets the plant's stage of growth
     *
     * @return The current stage of growth (i.e. SPROUT, SMALL, or LARGE)
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Advances the plant's stage of growth, if possible
     */
    public void advanceStage() {
        switch (stage) {
            case SPROUT:
                stage = Stage.SMALL;
                break;
            case SMALL:
                stage = Stage.LARGE;
                break;
            case LARGE:
                //Plant is at maximum growth, do nothing
                break;
        }

        master.setThisTexture();
    }

    /**
     * Sets the texture based on the plant's stage of growth
     */
    public abstract String getThisTexture();

    /**
     * Sets up the loot rarity map for a plant
     */
    abstract void setupLoot();


    /**
     * Gets a list of all possible loot dropped by this plant
     *
     * @return An array of all possible loot
     */
    public String[] getLoot() {
        return lootRarity.keySet().toArray(new String[lootRarity.size()]);
    }

    /**
     * Generates a random item based on the loot rarity
     *
     * @return A random item string in the plant's loot map
     */
    String randItem() {
        Double prob = Math.random();
        Double total = 0.0;
        for (Map.Entry<String, Double> entry : lootRarity.entrySet()) {
            total += entry.getValue();
            if (total > prob) {
                return entry.getKey();
            }
        }
        LOGGER.warn("No item has been selected, returning null");
        return null;
    }

    //Rest to be implemented later

}
