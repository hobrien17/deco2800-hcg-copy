package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.items.lootable.Lootable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.managers.WeatherManager;
import com.deco2800.hcg.util.WorldUtil;

import java.util.ArrayList;
import java.util.List;
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

	private PlantManager plantManager = (PlantManager) GameManager.get().getManager(PlantManager.class);

	static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

	/**
	 * Represents a stage of growth Can be either SPROUT, SMALL, or LARGE New
	 * plants should start as SPROUTs
	 *
	 * More stages can be added if needed (remember to update relevant methods)
	 */
	public enum Stage {
		SPROUT, SMALL, LARGE
	}

	private String name;
	private Stage stage;
	private Pot master;
	private int growDelay;
	private int lastGrow;

	protected Map<LootWrapper, Double> lootRarity;
	protected int numLoot;

	/**
	 * Creates a new plant in the given pot with the given growth delay.
	 * 
	 * @param master
	 *            the pot the plant is related to
	 * @param delay
	 *            the growth delay of the plant.
	 */
	public AbstractGardenPlant(Pot master, String name, int delay) {
		WeatherManager weather = (WeatherManager) GameManager.get().getManager(WeatherManager.class);

		this.stage = Stage.SPROUT;
		growDelay = delay;
		StopwatchManager manager = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
		manager.addObserver(this);
		lastGrow = (int) manager.getStopwatchTime();
		this.master = master;
		this.name = name;

		numLoot = 1;
		setupLoot();
	}

	@Override
	public void update(Observable o, Object arg) {
		int time = (int) (float) arg;
		if (time - lastGrow >= growDelay) {
			this.advanceStage();
			plantManager.updateLabel();
			lastGrow = time;
		}

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
	 * Gets the pot of this plant
	 *
	 * @return The pot object
	 */
	public Pot getPot() {
		return master;
	}

	/**
	 * Sets the current plant's name
	 *
	 * @param name
	 *            plant's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the plant's name
	 *
	 * @return The name for this plant
	 */
	public String getName() {
		return name;
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
		default:
			// if large or otherwise, do nothing
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

	@Override
	public Map<LootWrapper, Double> getRarity() {
		return lootRarity;
	}
	
	@Override
	public List<Item> getAllLoot() {
		List<Item> items = new ArrayList<>(lootRarity.size());
		for(LootWrapper wrapper : lootRarity.keySet().toArray(new LootWrapper[lootRarity.size()])) {
			items.add(wrapper.getItem());
		}
		return items;
	}

	@Override
	public List<Item> getLoot() {
		List<Item> items = new ArrayList<>(numLoot);
		for (int i = 0; i < numLoot; i++) {
			items.add(randItem().getItem());
		}
		return items;
	}

	@Override
	public LootWrapper randItem() {
		Double prob = Math.random();
		Double total = 0.0;
		for (Map.Entry<LootWrapper, Double> entry : lootRarity.entrySet()) {
			total += entry.getValue();
			if (total > prob) {
				return entry.getKey();
			}
		}
		LOGGER.warn("No item has been selected, returning null");
		return null;
	}

	private List<int[]> getPositions() {
		List<int[]> positions = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x != 0 || y != 0) {
					int[] pair = { (int)master.getPosX() + x, (int)master.getPosY() + y };
					positions.add(pair);
				}
			}
		}
		return positions;
	}

	@Override
	public void loot() {
		if(stage != Stage.LARGE) {
			return;
		}
		List<Item> items = getLoot();
		for (Item item : items) {
			List<int[]> positions = getPositions();
			List<AbstractEntity> entities = WorldUtil.allEntitiesToPosition(master.getPosX(), master.getPosY(), 2f,
					Player.class);
			for (AbstractEntity entity : entities) {
				Player player = (Player) entity;
				int[] pair = { (int) player.getPosX(), (int) player.getPosY() };
				positions.remove(pair);
			}
			if (positions.isEmpty()) {
				positions = getPositions();
			}
			int randomInt = (int) (Math.random() * positions.size());
			int[] randomPos = positions.get(randomInt);
			ItemEntity itemEntity = new ItemEntity(randomPos[0], randomPos[1], 0f, item);
			GameManager.get().getWorld().addEntity(itemEntity);
		}
		
		plantManager.removePlants(this);
		master.removePlant();
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
		if (Math.abs(sum - 1.0) > 0.00001) {
			LOGGER.warn("Total rarity should be 1");
			return false;
		}
		return true;
	}

	/**
	 * Increases or reduces the delay between stages
	 * 
	 * @param amount
	 *            the amount to reduce or increase the delay ( <1 to reduce, >1
	 *            to increase)
	 */
	public void changeDelay(float amount) {
		growDelay *= amount;
	}

	/**
	 * Increases the rarity of all "rare" items by change amount. A rare item is
	 * defined by an item with rarity less than or equal to threshold. Note that
	 * all rarities will be modified at the end of this method to ensure the
	 * total rarity remains at 1. Because of this, the rarities will actually
	 * change by an amount slightly less than change.
	 * 
	 * @param threshold
	 *            the threshold which defines a rare item
	 * @param change
	 *            the amount to change the rarity by
	 */
	public void increaseRarity(double threshold, double change) {
		double total = 0.0;
		for (Map.Entry<LootWrapper, Double> entry : lootRarity.entrySet()) {
			if (entry.getValue() <= threshold) {
				// if the rarity is below the threshold, make it more common
				total += change;
				lootRarity.put(entry.getKey(), entry.getValue() + change);
			}
		}
		total /= lootRarity.size();
		// modify all the rarities so they sum up to 1
		for (Map.Entry<LootWrapper, Double> entry : lootRarity.entrySet()) {
			lootRarity.put(entry.getKey(), entry.getValue() - total);
		}

		checkLootRarity(); // will display a warning if the loot rarity goes
							// above 1
	}
	
	/**
	 * Loots the plant at any stage of growth
	 * Smaller plants will drop less loot
	 * 
	 */
	public void harvest() {
		if(stage == Stage.LARGE) {
			loot();
			return;
		}
		for (Map.Entry<LootWrapper, Double> entry : lootRarity.entrySet()) {
			if(stage == Stage.SPROUT) {
				entry.getKey().modAmount(0.1f);
			} else if(stage == Stage.SMALL) {
				entry.getKey().modAmount(0.3f);
			}
		}
		stage = Stage.LARGE;
		loot();
	}
	
	public void modNumLoot(float mod) {
		numLoot *= mod;
	}

}
