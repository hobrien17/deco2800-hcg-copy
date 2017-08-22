package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.entities.worldmap.*;
import com.deco2800.hcg.renderers.Renderable;

import java.util.Random;

/**
 * Initial world using preset world file.
 *
 * @author leggy
 *
 */
public class WorldMapWorld extends AbstractWorld {

    /**
     * Constructor for DemoWorld
     */
    public WorldMapWorld() {
		/* Load up the map for this world */
        this.map = new TmxMapLoader().load("resources/maps/placeholder.tmx");

		/*
		 * Grab the width and length values from the map file to use as the world size
		 */
        this.setWidth(this.getMap().getProperties().get("width", Integer.class));
        this.setLength(this.getMap().getProperties().get("height", Integer.class));
        Level level = new Level(this, 1, 1,1);  // <- Not sure the purpose of this level
        this.addEntity(new MapNode(5, 5, "levelPortal", 0, level));
        this.addEntity(new MapNode(10, 15, "levelPortal", 0, level));
        this.addEntity(new MapNode(35, 25, "levelPortal", 0, level));
        this.addEntity(new MapNode(45, 55, "levelPortal", 0, level));


//
//        Random random = new Random();
//        for(int i = 0; i < 20; i++) {
//            this.addEntity(new Squirrel(random.nextFloat() * 20, random.nextFloat() * 20, 0));
//        }

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
