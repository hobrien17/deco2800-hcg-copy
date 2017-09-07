package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.entities.enemy_entities.Squirrel;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.entities.turrets.ExplosiveTurret;
import com.deco2800.hcg.entities.turrets.FireTurret;
import com.deco2800.hcg.entities.turrets.SunflowerTurret;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.entities.terrain_entities.Tree;
import com.deco2800.hcg.entities.terrain_entities.TreeState;
import com.deco2800.hcg.entities.terrain_entities.TreeType;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.entities.NPC;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;

import java.util.Iterator;
import java.util.Random;

/**
 * Initial world using preset world file.
 *
 * @author leggy
 */
public class DemoWorld extends AbstractWorld {
   
    /**
     * Constructor for DemoWorld
     */
    public DemoWorld() {
        /* Load up the map for this world */
        this.map = new TmxMapLoader()
                .load("resources/maps/initial-map-test.tmx");

		/*
		 * Grab the width and length values from the map file to use as the world size
		 */
		this.setWidth(this.getMap().getProperties().get("width", Integer.class));
		this.setLength(this.getMap().getProperties().get("height", Integer.class));

		// get player
		Player player = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
		
		// change player position based on the properties below
		player.setPosX(Float.parseFloat((String) this.getMap().getProperties().get("PlayerX")));
	    player.setPosY(Float.parseFloat((String) this.getMap().getProperties().get("PlayerY")));

		Pot[] pots = new Pot[4];
		for(int i = 0; i < 4; i++) {
			pots[i] = new Pot(20, 10 + 2*i, 0);
			this.addEntity(pots[i]);
		}
		
		Corpse corpse = new BasicCorpse(12, 12, 0, "sunflower_01");
		this.addEntity(corpse);
		corpse.plantInside(new Seed(Seed.Type.FIRE));
		
		Random random = new Random();
		for(int i = 0; i < 20; i++) {
			this.addEntity(new Squirrel(random.nextFloat() * 20, random.nextFloat() * 20, 0,i+1));
		}
						
	    // loop over all object layers
	    for (MapLayer layer : getObjectLayers()){
          
	        Iterator<MapObject> objects = layer.getObjects().iterator();

	        int i = 0; // for enemy's because they need unique id's i guess
	        
	        // store layer name
            String layerName = ((String) layer.getProperties().get("name")).toUpperCase();
            
            // make sure the layer has an associating entity type, otherwise we don't want to loop over the objects
            Boolean found = false;
            
            // loop over all entities, make sure the entity exists
            for(WorldEntities type : WorldEntities.values()){
              if (type.toString().equals(layerName)){
                found = true;
              }
            }
            
	        while (objects.hasNext() && found) {
	          	          
	          MapObject obj = objects.next();
	                  
	          // get x and y
	          float x = (float) obj.getProperties().get("y"); // no clue why these are switched, help
	          float y = (float) obj.getProperties().get("x");
	          
	          x/=32; // divide by the width / height, I guess this might screw up bigger tiles
	          y/=32;
	          
	          y--; // this fixes it for some reason
	          
	          this.addEntity(WorldEntities.valueOf(layerName).Spawn(x, y, i+1)); // spawn the entity in (we know it exists)
	          	          	          
	          i++; // add to ensure uniqueness of the id, may be bad if there's multiple enemy types
	          
	        }
	        
	        // Remove this layer! After this method we will only have tile layers, which is good
	        map.getLayers().remove(layer);

	    }

		/*
		 * plant some trees (terrain entity) - @ken
		 */
		//trees lining the path
		Tree[] rowOfTrees1 = new Tree[6];
		for (int i = 0; i < 6; i++) {
			rowOfTrees1[i] = new Tree(17.5f, i*3 + 20.5f, 0, TreeType.BASIC);
			this.addEntity(rowOfTrees1[i]);
		}
		Tree[] rowOfTrees2 = new Tree[6];
		for (int i = 0; i < 6; i++) {
			rowOfTrees2[i] = new Tree(20.5f, i*3 + 20.5f, 0, TreeType.BASIC);
			this.addEntity(rowOfTrees2[i]);
		}

		// ORCHARD
		// leafless trees
		Tree[] rowOfTrees3 = new Tree[6];
		for (int i = 0; i < 6; i++) {
			Tree tree = new Tree(i*2 + 24.5f, 0.5f, 0, TreeType.BASIC);
			tree.setState(TreeState.LEAFLESS);
			rowOfTrees3[i] = tree;
			this.addEntity(rowOfTrees3[i]);
		}
		// budding trees
		Tree[] rowOfTrees4 = new Tree[6];
		for (int i = 0; i < 6; i++) {
			Tree tree = new Tree(i*2 + 24.5f, 2.5f, 0, TreeType.BASIC);
			tree.setState(TreeState.BUDDING);
			rowOfTrees4[i] = tree;
			this.addEntity(rowOfTrees4[i]);
		}
		// leafy trees
		Tree[] rowOfTrees5 = new Tree[6];
		for (int i = 0; i < 6; i++) {
			Tree tree = new Tree(i*2 + 24.5f, 4.5f, 0, TreeType.BASIC);
			tree.setState(TreeState.LEAFY);
			rowOfTrees5[i] = tree;
			this.addEntity(rowOfTrees5[i]);
		}
		// fruiting trees
		Tree[] rowOfTrees6 = new Tree[6];
		for (int i = 0; i < 6; i++) {
			Tree tree = new Tree(i*2 + 24.5f, 6.5f, 0, TreeType.BASIC);
			tree.setState(TreeState.FRUITING);
			rowOfTrees6[i] = tree;
			this.addEntity(rowOfTrees6[i]);
		}

		//Add an example quest NPC
		this.addEntity(new NPC(10,10,0,0.5f,0.5f,1.0f, false,"Jane","Jensen", NPC.Type.Quest, "character_1") {});

		//Add an example shop NPC
		this.addEntity(new NPC(30,15,0,0.5f,0.5f,1.0f, false,"John","Jensen", NPC.Type.Shop, "character_shop") {});


	}

	/**
	 * Deselects all entities.
	 */
	public void deSelectAll() {
		for (Renderable r : this.getEntities()) {
			if (r instanceof Selectable) {
				((Selectable) r).deselect();
			}
		}
	}
}
