package com.deco2800.hcg.managers;

import com.deco2800.hcg.entities.garden_entities.plants.*;
import org.junit.*;

import java.util.ArrayList;

public class PlantManagerTests {
    private PlantManager plantManager;
    private Pot pot;
    private AbstractGardenPlant ice;
    private AbstractGardenPlant ice2;
    private AbstractGardenPlant cactus;
    private AbstractGardenPlant grass;
    private AbstractGardenPlant inferno;
    private AbstractGardenPlant sunFlower;
    private AbstractGardenPlant water;

    //Set up
    @Before
    public void setup() {
        plantManager = new PlantManager();
        pot = new Pot(3, 3, 3);
        ice = new Ice(pot);
        ice2 = new Ice(new Pot(3, 4, 5));
        cactus = new Cactus(pot);
        grass = new Grass(pot);
        inferno = new Inferno(pot);
        sunFlower = new Sunflower(pot);
        water = new Water(pot);
    }

    //Add plant and check if gets expected value
    @Test
    public void addPlant() {
        plantManager.addPlants(ice);
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();
        expected.add(ice);

        Assert.assertEquals("Plant has not been correctly added",
                expected, plantManager.getPlants());

        plantManager.addPlants(grass);
        expected.add(grass);

        Assert.assertEquals("Plant has not been correctly added",
                expected, plantManager.getPlants());
    }

    //Add the exactly same plant and add the same plant but in different pot
    @Test
    public void addSamePlant() {
        plantManager.addPlants(ice);
        plantManager.addPlants(ice);
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();
        expected.add(ice);

        Assert.assertEquals("Plant has not been correctly added",
                expected, plantManager.getPlants());

        plantManager.addPlants(ice2);
        expected.add(ice2);

        Assert.assertEquals("Plant has not been correctly added",
                expected, plantManager.getPlants());
    }

    //Remove plant and then add one plant
    @Test
    public void removePlantOne() {
        plantManager.addPlants(grass);
        plantManager.removePlants(grass);
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();

        Assert.assertEquals("Plant has not been correctly removed",
                expected, plantManager.getPlants());

        plantManager.addPlants(ice);
        expected.add(ice);

        Assert.assertEquals("Plant has not been correctly added",
                expected, plantManager.getPlants());
    }

    //Remove plant when no plant in manager
    @Test
    public void removePlantTwo() {
        plantManager.removePlants(ice);
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();

        Assert.assertEquals("Error in removing plant",
                expected, plantManager.getPlants());
    }

    //Add some plants
    @Test
    public void addAllPlants() {
        plantManager.addPlants(ice);
        plantManager.addPlants(cactus);
        plantManager.addPlants(grass);
        plantManager.addPlants(inferno);
        plantManager.addPlants(sunFlower);
        plantManager.addPlants(water);
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();
        expected.add(ice);
        expected.add(cactus);
        expected.add(grass);
        expected.add(inferno);
        expected.add(sunFlower);
        expected.add(water);

        Assert.assertEquals("Plants has not been correctly added",
                expected, plantManager.getPlants());
    }

    //Remove all plants
    @Test
    public void removeAllPlants() {
        plantManager.addPlants(ice);
        plantManager.addPlants(cactus);
        plantManager.addPlants(grass);
        plantManager.addPlants(inferno);
        plantManager.addPlants(sunFlower);
        plantManager.addPlants(water);
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();
        plantManager.removeAll();

        Assert.assertEquals("Plant has not been correctly removed",
                expected, plantManager.getPlants());

    }

    //Remove all plants when no plant in manager and add one plant into manager
    @Test
    public void removeAllPlantsTwo() {
        plantManager.removeAll();
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();

        Assert.assertEquals("Error in removing all plants",
                expected, plantManager.getPlants());

        plantManager.addPlants(ice);
        expected.add(ice);

        Assert.assertEquals("plant has not been added correctly",
                expected, plantManager.getPlants());
    }

    //Test all function together
    @Test
    public void complex() {
        plantManager.addPlants(inferno);
        plantManager.addPlants(sunFlower);
        plantManager.addPlants(water);
        plantManager.removePlants(sunFlower);
        ArrayList<AbstractGardenPlant> expected = new ArrayList<>();
        expected.add(inferno);
        expected.add(water);

        Assert.assertEquals("Receive unexpected result",
                expected, plantManager.getPlants());

        plantManager.addPlants(inferno);

        Assert.assertEquals("Receive unexpected result",
                expected, plantManager.getPlants());

        plantManager.addPlants(ice2);
        expected.add(ice2);

        Assert.assertEquals("Receive unexpected result",
                expected, plantManager.getPlants());

        plantManager.removeAll();
        expected.clear();

        Assert.assertEquals("Receive unexpected result",
                expected, plantManager.getPlants());

        plantManager.addPlants(water);
        expected.add(water);

        Assert.assertEquals("Receive unexpected result",
                expected, plantManager.getPlants());
    }

}
