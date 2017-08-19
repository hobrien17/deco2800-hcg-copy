package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.entities.garden_entities.plants.BasicPlant;
import com.deco2800.hcg.renderers.Renderable;

import java.util.Random;

/**
 * World to test Garden
 * Copied from DemoWorld
 * 
 * @author Henry O'Brien
 *
 */
public class GardenDemo extends AbstractWorld {

	/**
	 * Constructor for DemoWorld
	 */
	public GardenDemo() {
		/* Load up the map for this world */
		this.map = new TmxMapLoader().load("resources/maps/grass.tmx");

		/*
		 * Grab the width and length values from the map file to use as the world size
		 */
		this.setWidth(this.getMap().getProperties().get("width", Integer.class));
		this.setLength(this.getMap().getProperties().get("height", Integer.class));

		this.addEntity(new BasicPlant(8, 8, 0));
		
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
