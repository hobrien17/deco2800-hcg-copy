package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.managers.TimeManager;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a plant in the garden Used as a shell for more specific types of
 * plants
 *
 * @author Henry O'Brien
 */
public abstract class AbstractGardenPlant implements Lootable, Observer {

    static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    /**
     * Represents a stage of growth Can be either SPROUT, SMALL, or LARGE New
     * plants should start as SPROUTs
     *
     * More stages can be added if needed (remember to update relevant methods)
     */
    public enum Stage {
        SPROUT,
        SMALL,
        LARGE
    }

    private String name;
    private Stage stage;
    private Pot master;
    private int growDelay;
    private int lastGrow;

    Map<String, Double> lootRarity;

    /**
     * Creates a new plant in the given pot with the given growth delay.
     * @param master the pot the plant is related to
     * @param delay the growth delay of the plant.
     */
    public AbstractGardenPlant(Pot master, int delay) {
        this.stage = Stage.SPROUT;
        growDelay = delay;
        StopwatchManager manager = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
        lastGrow = (int)manager.getStopwatchTime();
        this.master = master;
        setupLoot();
    }
    
    @Override
	public void update(Observable o, Object arg) {
		int time = (int)(float)arg;
		if (time - lastGrow >= growDelay) {
        	this.advanceStage();
        	lastGrow = time;
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

    /**
     * Gets the pot of this plant
     *
     * @return The pot object
     */
    public Pot getPot(){
        return master;
    }

    /**
     * Gets the plant's name
     *
     * @return The name for this plant
     */
    public String getName(){
        return name;
    }

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
     * Returns the amount of time it takes for the plant to advance a stage
     * 
     * @return the growth delay of the plant
     */
    public int getGrowDelay() {
    	return growDelay;
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
    
    /**
     * Checks that the loot rarity is valid
     * 
     * @return true if the loot rarity is valid, otherwise false
     */
    public boolean checkLootRarity() {
    	double sum = 0.0;
        for (Double rarity : lootRarity.values()) {
            if (rarity < 0.0 || rarity > 1.0) {
                LOGGER.error("Rarity should be between 0 and 1");
                return false;
            }
            sum += rarity;
        }
        if (Double.compare(sum, 1.0) != 0) {
            LOGGER.warn("Total rarity should be 1");
            return false;
        }
        return true;
    }

    //Rest to be implemented later

}
