package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.deco2800.hcg.BaseTest;
import com.deco2800.hcg.contexts.playContextClasses.PlantWindow;
import com.deco2800.hcg.entities.Plant;
import com.deco2800.hcg.entities.garden_entities.plants.*;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.StopwatchManager;

/**
 * IN PROGRESS
 * 
 * @author Henry O'Brien
 *
 */
public class PlantTest extends BaseTest {
	
	private static Pot pot;
	private static List<Details> details;
	
	@BeforeClass
	public static void setup() {
		PlantManager manager = (PlantManager) GameManager.get().getManager(PlantManager.class);
    	Skin skin = new Skin(Gdx.files.internal("resources/ui/plant_ui/flat-earth-ui.json"));
    	manager.setPlantWindow(new PlantWindow(skin), skin);
    	manager.setPlantButton(new Button());
    			
		Details[] arr = {new Details(Seed.Type.SUNFLOWER, Sunflower.class, "sunflower", 600),
				new Details(Seed.Type.GRASS, Grass.class, "grass", 900)};
		details = Arrays.asList(arr);
		
		pot = new Pot(5, 5, 0);
	}
	
	@Test
	public void testSeedPlantConversion() {
		pot.unlock();
		for(Details detail : details) {
			Seed seed = new Seed(detail.seed);
			assertEquals("Seed has wrong name", detail.seedName(), seed.getName().toUpperCase());
			assertEquals("Seed has wrong texture", detail.seedTex(), seed.getTexture());
			pot.plantInside(seed);
			AbstractGardenPlant plant = pot.getPlant();
			assertTrue("Seed growing into the wrong plant", detail.plant.isInstance(plant));
			assertEquals("Plant has wrong name", detail.plantName(), plant.getName().toUpperCase());
			assertEquals("Plant has wrong grow delay", detail.growDelay, plant.getGrowDelay());
			assertEquals("Plant should be in pot", pot, plant.getPot());
			pot.removePlant();
		}
	}
	
	@Test
	public void testPlantGrowth() {
		pot.unlock();
		for(Details detail : details) {
			pot.plantInside(new Seed(detail.seed));
			AbstractGardenPlant plant = pot.getPlant();
			assertEquals("Plant is at wrong stage of growth", AbstractGardenPlant.Stage.SPROUT, plant.getStage());
			assertEquals("Plant has wrong sprout texture", detail.sproutTex(), plant.getThisTexture());
			plant.advanceStage();
			assertEquals("Plant is at wrong stage of growth", AbstractGardenPlant.Stage.SMALL, plant.getStage());
			assertEquals("Plant has wrong sprout texture", detail.smallTex(), plant.getThisTexture());
			plant.advanceStage();
			assertEquals("Plant is at wrong stage of growth", AbstractGardenPlant.Stage.LARGE, plant.getStage());
			assertEquals("Plant has wrong sprout texture", detail.largeTex(), plant.getThisTexture());
			pot.removePlant();
		}
	}
	
	@Test
	public void testPlantUpdate() {
		StopwatchManager sw = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
		pot.unlock();
		pot.plantInside(new Seed(details.get(0).seed));
		AbstractGardenPlant plant = pot.getPlant();
		AbstractGardenPlant.Stage last = plant.getStage();
		int i;
		for(i = 1; i <= details.get(0).growDelay * 3 - 1; i++) {
			plant.update(sw, (float)i);
			if(i % details.get(0).growDelay == 0) {
				assertFalse("Plant should have grown", last.equals(plant.getStage()));
				last = plant.getStage();
			}
		}
		plant.update(sw, (float)i);
		assertTrue("Plant shouldn't change any more", last.equals(plant.getStage()));
		pot.removePlant();
	}

	private static class Details {
		private Seed.Type seed;
		private Class<? extends AbstractGardenPlant> plant;
		private String name;
		private int growDelay;
		
		private Details(Seed.Type seed,	Class<? extends AbstractGardenPlant> plant, String name, int growDelay) {
			this.seed = seed;
			this.plant = plant;
			this.name = name;
			this.growDelay = growDelay;
		}
		
		private String seedName() {
			return (name + " Seed").toUpperCase();
		}
		
		private String plantName() {
			return name.toUpperCase();
		}
		
		private String seedTex() {
			return name + "_seed";
		}
		
		private String sproutTex() {
			return name + "_01";
		}
		
		private String smallTex() {
			return name + "_02";
		}
		
		private String largeTex() {
			return name + "_03";
		}
	}
}
