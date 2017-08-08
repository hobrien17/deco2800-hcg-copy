package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.Peon;
import com.deco2800.hcg.entities.Selectable;
import com.deco2800.hcg.entities.Squirrel;
import com.deco2800.hcg.entities.Tower;
import com.deco2800.hcg.renderers.Renderable;

import java.util.Random;

/**
 * Initial world using preset world file.
 * 
 * @author leggy
 *
 */
public class DemoWorld extends AbstractWorld {

	/**
	 * Constructor for DemoWorld
	 */
	public DemoWorld() {
		/* Load up the map for this world */
		this.map = new TmxMapLoader().load("resources/placeholderassets/placeholder.tmx");

		/*
		 * Grab the width and length values from the map file to use as the world size
		 */
		this.setWidth(this.getMap().getProperties().get("width", Integer.class));
		this.setLength(this.getMap().getProperties().get("height", Integer.class));

		this.addEntity(new Peon(7, 7, 0));
		this.addEntity(new Tower(8, 8, 0));
		
//		this.addEntity(new Squirrel(5, 5, 0));
//		this.addEntity(new Squirrel(10, 2, 0));
//		this.addEntity(new Squirrel(4, 7, 0));
		
		Random random = new Random();
		for(int i = 0; i < 20; i++) {
			this.addEntity(new Squirrel(random.nextFloat() * 20, random.nextFloat() * 20, 0));
		}
		
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
