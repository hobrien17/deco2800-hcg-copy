package com.deco2800.hcg.entities.worldmap;

import com.deco2800.hcg.worlds.AbstractWorld;

public class Level {
	private AbstractWorld world;
	private int worldType; // <- possibility for biomes in map
	private int difficulty; // <- levels can become harder as game progresses
	/* What kind of level?
	 * 0 for safe zone level
	 * 1 for standard level
	 * 2 for boss level 
	 */
	private int levelType;
	
	/* Quite likely that the worldType, difficulty and levelType will have to be included in the AbstractWorld class
	 * in future 
	 */
	
	public Level(AbstractWorld world, int type, int difficulty, int levelType) {
		this.world = world;
		worldType = type;
		this.difficulty = difficulty;
		this.levelType = levelType;
	}
	
	// ACCESSOR METHODS
	public AbstractWorld getWorld() {
		return world;
	}
	
	public int getWorldType() {
		return worldType;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public int levelType() {
		return levelType;
	}
	
	// MANIPULATING METHODS
	public void setWorld(AbstractWorld newWorld) {
		world = newWorld;
	}
	
	public void changeWorldType(int newWorldType) {
		worldType = newWorldType;
	}
	
	public void changeDifficulty(int newDifficulty) {
		difficulty = newDifficulty;
	}
	
	public void changeLevelType(int newLevelType) {
		levelType = newLevelType;
	}
}
