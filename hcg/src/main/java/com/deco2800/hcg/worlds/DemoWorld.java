package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.entities.terrain_entities.WallBlock;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.entities.NPC;

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

		Pot[] pots = new Pot[4];
		for(int i = 0; i < 4; i++) {
			pots[i] = new Pot(20, 10 + 2*i, 0);
			this.addEntity(pots[i]);
		}

		//demo wall around the South-Western corner of the demo map
		//south wall
		//todo: wall-builder type class
		WallBlock[] southWall = new WallBlock[15];
		for (int i = 0; i < 15; i++) {
			southWall[i] = new WallBlock(0, i,0);
			this.addEntity(southWall[i]);
		}
		//east wall
		WallBlock[] westWall = new WallBlock[14];
		for (int i = 0; i < 14; i++) {
			westWall[i] = new WallBlock(i+1, 0,0);
			this.addEntity(westWall[i]);
		}

		//demo wall lining the main path in the demo map
		//south wall
		WallBlock[] pathWallSouth = new WallBlock[16];
		for (int i = 0; i < 16; i++) {
			pathWallSouth[i] = new WallBlock(i+20, 21,0);
			this.addEntity(pathWallSouth[i]);
		}
		//north wall part 1
		WallBlock[] pathWallNorthP1 = new WallBlock[9];
		for (int i = 0; i < 9; i++) {
			pathWallNorthP1[i] = new WallBlock(i + 20, 18, 0);
			this.addEntity(pathWallNorthP1[i]);
		}
		//north wall part 2
		WallBlock[] pathWallNorthP2 = new WallBlock[4];
		for (int i = 0; i < 4; i++) {
			pathWallNorthP2[i] = new WallBlock(i + 32, 18, 0);
			this.addEntity(pathWallNorthP2[i]);
		}



//		WallBlock wallBlock = new WallBlock(1,1,0);
//		this.addEntity(wallBlock);
		
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
