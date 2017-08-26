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

    public void addPlants(AbstractGardenPlant plant){
        if (!plantList.contains(plant)){
            plantList.add(plant);
        }
    }

    public void removePlants(AbstractGardenPlant plant){
        if (plantList.contains(plant)){
            plantList.remove(plant);
        }
    }

    public void setTimeManager(TimeManager timeManager){
        this.timeManager = timeManager;
    }

    public void setPlantLabel(Label label){
        this.plantInfo = label;
    }

    public void updateLabel(){
        plantInfo.setText(labelContent());
    }

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
                result.append(i).append(": ").append("Name ").append("Stage ")
                        .append("\n").append("X:")
                        //.append(plant.getPosX())
                        .append(" Y:")
                        //.append(plant.getPosY())
                        .append(" Z:")
                        //.append(plant.getPosZ())
                        .append("\n");
            }
        }

        return result.toString();
    }
}
