package com.deco2800.hcg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.StringBuilder;
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
	private Table windowTable;
	private Window plantWindow;
	private Skin skin;

	/**
	 * Creates a new PlantManager
	 */
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
     * Removes all plants in the plant manager
     */
	public void removeAll() {
	    plantList.clear();
    }

    /**
     * Returns all plants in the plant manager
     * 
     * @return a list of all plants
     */
	public ArrayList<AbstractGardenPlant> getPlants() {
	    return plantList;
    }

	/**
	 * Sets the window for the manager
     *
	 * @param plantWindow 
	 * 			the window to set it to
	 * @param skin 
	 * 			the window's skin
	 */
	public void setPlantWindow(Window plantWindow, Skin skin) {
		this.plantWindow = plantWindow;
		this.skin = skin;
		plantWindow.row().width(170);
		windowTable = new Table(skin);

		plantWindow.add(windowTable);
	}

	/**
	 * Updates the labels in the plant manager UI 
	 */
	public void updateLabel() {
	    windowTable.clear();
	    contents();
		plantWindow.pack();
	}

    /**
     * Sets the visibility of the plantWindow
     * 
     * @param bool if window is set to visible
     */
	public void setWindowVisible(boolean visible){
	    this.plantWindow.setVisible(visible);
    }

    /**
     * Highlights the plant
     *
     * @param plant the plant to be highlighted
     */
    private void highLight(AbstractGardenPlant plant) {
    	//TODO implement this
    }

	/**
	 * Retrieves plants from plantList
	 */
	private void contents() {
		if (plantList.size() == 0) {
		    Label nonPlant = new Label("No plants planted", skin);
		    nonPlant.setColor(Color.GRAY);
			windowTable.add(nonPlant);
		} else {
			for (AbstractGardenPlant plant : plantList) {
				String stage;
				if (plant.getStage() == SPROUT) {
					stage = "Sprout";
				} else if (plant.getStage() == SMALL) {
					stage = "Small";
				} else {
					stage = "Large";
				}
				UIupdate(plant, stage);
			}
        }
	}

    /**
     * Update the UI
     *
     * @param plant The plant is going to be displayed
     * @param stage The stage of this plant
     */
	private void UIupdate(AbstractGardenPlant plant, String stage){
        StringBuilder currentRow = new StringBuilder();
        String path = "resources/sprites/plants/icon/"+plant.getName()+".png";
        Image image = new Image(new
                Texture(Gdx.files.internal(path)));
        Button button = new TextButton(plant.getName(),skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                highLight(plant);
            }
        });
        windowTable.add();
        windowTable.add(button);
        windowTable.row();
        windowTable.add(image);
        currentRow.append("      Stage: ").append(stage).append("  \n")
                .append("      X:").append((int) plant.getPot().getPosX()).append("   Y:")
                .append((int) plant.getPot().getPosY());
        windowTable.add(currentRow);
        windowTable.row();
    }
}
