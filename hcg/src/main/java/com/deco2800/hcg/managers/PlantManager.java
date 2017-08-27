package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;

import java.util.ArrayList;


/**
 * Plant Manager manages all the plants on map.
 *
 * @author Youwen Mao
 */
public class PlantManager extends Manager{

    private TimeManager timeManager;
    private ArrayList <AbstractGardenPlant> plantList;
    private ArrayList timeleft;
    private Label plantInfo;

    public PlantManager(){
        plantList = new ArrayList<>();
        timeleft = new ArrayList();
    }

    /**
     * Add a plant into the manager, if the plant exists in game do nothing.
     *
     * @param plant The plant need to be managed
     */
    public void addPlants(AbstractGardenPlant plant){
        if (!plantList.contains(plant)){
            plantList.add(plant);
        }
    }

    /**
     * Remove a plant from the manager, if the plant does not exist in game do nothing.
     *
     * @param plant The plant need to be removed
     */
    public void removePlants(AbstractGardenPlant plant){
        if (plantList.contains(plant)){
            plantList.remove(plant);
        }
    }

    /**
     * Link to the time manager
     *
     * @param timeManager The time manager of the whole
     */
    public void setTimeManager(TimeManager timeManager){
        this.timeManager = timeManager;
    }

    /**
     * Link to label of the plant window
     *
     * @param label The label of plant window
     */
    public void setPlantLabel(Label label){
        this.plantInfo = label;
    }

    /**
     * Update the label
     */
    public void updateLabel(){
        plantInfo.setText(labelContent());
    }

    /**
     * Returns information of all plants as a string
     *
     * @return Plants' info
     */
    private String labelContent(){
        StringBuilder result = new StringBuilder("Not yet plant.");
        if (plantList.size() == 0){
            plantInfo.setColor(Color.GRAY);

        }else{
            plantInfo.setColor(Color.GREEN);
            for(int i = 0; i < plantList.size(); i++){
                if (i == 0){
                    result = new StringBuilder();
                }
                AbstractGardenPlant plant = plantList.get(i);
                result.append(i).append(": ").append(plant.getName()).append(" Stage ")
                        .append("\n").append("X:")
                        .append(plant.getPot().getPosX())
                        .append(" Y:")
                        .append(plant.getPot().getPosY())
                        .append(" Z:")
                        .append(plant.getPot().getPosZ())
                        .append("\n");
            }
        }

        return result.toString();
    }
}
