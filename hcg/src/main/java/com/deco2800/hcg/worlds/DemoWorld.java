package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    @SuppressWarnings("finally")
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

		//TODO: move this to own method getObjectLayers
		
		List<MapLayer> objectLayers = new ArrayList<MapLayer>();
		
		//MapLayer wall = null;
		
		Iterator<MapLayer> itr = map.getLayers().iterator(); //GameManager.get().getWorld().getMap()

	    while (itr.hasNext()) {
	      
	      MapLayer layer = null;
	      
	      try{
	        
	          layer = itr.next();  
	          //wall = layer;
	          
	          // attempt to cast to tiled map layer. if it can't, the its an objectlayer
	          TiledMapTileLayer cast = (TiledMapTileLayer) layer;
	              	      
	      }
	      catch (Exception e){	     
	        
	          // if the cast didn't work, we have found an object layer
	          objectLayers.add(layer);
	      }
	      
	    }

	    // loop over all object layers
	    for (MapLayer layer : objectLayers){
	        //System.out.println(layer);
          
	        Iterator<MapObject> objects = layer.getObjects().iterator();

	        while (objects.hasNext()) {
	          	          
	          MapObject obj = objects.next();
	                  
	          // get x and y
	          float x = (float) obj.getProperties().get("y"); // no clue why these are switched
	          float y = (float) obj.getProperties().get("x");
	          
	          x/=32; // divide by the width / height
	          y/=32;
	          
	          // do different things based on the layer name   
	          switch((String) layer.getProperties().get("name")){
	            case "wall": // walls
	              this.addEntity(new WallBlock(x, y, 0f));
	              break;
	            case "tree": // tree
	              this.addEntity(new BasicGreenTree(x, y, 0f));
	              break;
	          }
	          
	        }
	        
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
		
		Random random = new Random();
		for(int i = 0; i < 20; i++) {
			this.addEntity(new Squirrel(random.nextFloat() * 20, random.nextFloat() * 20, 0,i+1));
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
