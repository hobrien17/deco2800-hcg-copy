package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.entities.terrain_entities.BasicGreenTree;
import com.deco2800.hcg.entities.terrain_entities.WallBlock;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.entities.NPC;

import java.util.Iterator;

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

		Pot[] pots = new Pot[4];
		for(int i = 0; i < 4; i++) {
			pots[i] = new Pot(20, 10 + 2*i, 0);
			this.addEntity(pots[i]);
		}
						
	    // loop over all object layers
	    for (MapLayer layer : getObjectLayers()){
          
	        Iterator<MapObject> objects = layer.getObjects().iterator();

	        int i = 0; // for enemy's because they need unique id's i guess
	        
	        while (objects.hasNext()) {
	          	          
	          MapObject obj = objects.next();
	                  
	          // get x and y
	          float x = (float) obj.getProperties().get("y"); // no clue why these are switched, help
	          float y = (float) obj.getProperties().get("x");
	          
	          x/=32; // divide by the width / height, I guess this might screw up bigger tiles
	          y/=32;
	          
	          // do different things based on the layer name   
	          // ADD MORE CASES FOR YOUR OWN OBJECT LAYERS HERE
	          switch((String) layer.getProperties().get("name")){
	            case "wall": // walls
	              this.addEntity(new WallBlock(x, y, 0f));
	              break;
	            case "tree": // tree
	              this.addEntity(new BasicGreenTree(x, y, 0f));
	              break;
	            case "squirrel":
	              this.addEntity(new Squirrel(x, y, 0f, i + 1));
	              break;
	          }
	          
	          i++; // add to ensure uniqueness of the id, may be bad if there's multiple enemy types
	          
	        }
	        
	        // Remove this layer! After this method we will only have tile layers, which is good
	        map.getLayers().remove(layer);

	    }

		/**
		 * plant some trees (terrain entity) - @ken
		 */
		BasicGreenTree[] rowOfTrees = new BasicGreenTree[6];
		for (int i = 0; i < 6; i++) {
			rowOfTrees[i] = new BasicGreenTree(18, i*3 + 19, 0);
			this.addEntity(rowOfTrees[i]);
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
