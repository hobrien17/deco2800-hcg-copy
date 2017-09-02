package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.entities.turrets.ExplosiveTurret;
import com.deco2800.hcg.entities.turrets.FireTurret;
import com.deco2800.hcg.entities.turrets.SunflowerTurret;
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
		
		this.addEntity(new FireTurret());
		//this.addEntity(new SunflowerTurret());
		
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
