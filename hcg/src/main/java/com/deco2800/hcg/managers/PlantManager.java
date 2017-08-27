package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;

import java.util.ArrayList;

import static com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant.Stage.SMALL;
import static com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant.Stage.SPROUT;

/**
 * Plant Manager manages all the plants on map.
 *
 * @author Youwen Mao
 */
public class PlantManager extends Manager {

	private ArrayList<AbstractGardenPlant> plantList;
	private Label plantInfo;
	private Window plantWindow;

	public PlantManager() {
		plantList = new ArrayList<>();
	}

	/**
	 * Add a plant into the manager, if the plant exists in game do nothing.
	 *
	 * @param plant
	 *            The plant need to be managed
	 */
	public void addPlants(AbstractGardenPlant plant) {
		if (!plantList.contains(plant)) {
			plantList.add(plant);
		}
	}

	/**
	 * Remove a plant from the manager, if the plant does not exist in game do
	 * nothing.
	 *
	 * @param plant
	 *            The plant need to be removed
	 */
	public void removePlants(AbstractGardenPlant plant) {
		if (plantList.contains(plant)) {
			plantList.remove(plant);
		}
	}

	/**
	 * Link to label of the plant window
	 *
	 * @param label
	 *            The label of plant window
	 */
	public void setPlantLabel(Label label) {
		this.plantInfo = label;
	}

	public void setPlantWindow(Window plantWindow) {
		this.plantWindow = plantWindow;
	}

	/**
	 * Update the label
	 */
	public void updateLabel() {
		plantInfo.setText(labelContent());
		plantWindow.pack();
	}

	/**
	 * Returns information of all plants as a string
	 *
	 * @return Plants' info
	 */
	private String labelContent() {
		StringBuilder result = new StringBuilder("No plants planted");
		if (plantList.size() == 0) {
			plantInfo.setColor(Color.GRAY);

		} else {
			plantInfo.setColor(Color.GREEN);
			for (int i = 0; i < plantList.size(); i++) {
				if (i == 0) {
					result = new StringBuilder();
				}
				AbstractGardenPlant plant = plantList.get(i);
				String stage;
				if (plant.getStage() == SPROUT) {
					stage = "Sprout";
				} else if (plant.getStage() == SMALL) {
					stage = "Small";
				} else {
					stage = "Large";
				}
				result.append(plant.getName()).append("\n").append("      Stage: ").append(stage).append("  \n")
					  .append("      X:").append((int)plant.getPot().getPosX()).append("   Y:")
					  .append((int)plant.getPot().getPosY()).append("  \n");
			}
		}

		return result.toString();
	}
}
