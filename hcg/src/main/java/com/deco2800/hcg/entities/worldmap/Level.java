package com.deco2800.hcg.entities.worldmap;

import com.deco2800.hcg.worlds.World;

/**
 * Used to hold information about a specific level within a world.
 * Holds the map/world that the player will play on, the world type of level (jungle, city, beach, etc.), the difficulty
 * of the level (for enemy damage/health modification possibly?) and the level's type (safe zone, standard or boss).
 * 
 * This should likely be stored in the AbstractWorld class but it is here for now.
 */
public class Level {
	private World world;
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
	
	/**
	 * Initialises a Level object. The provided parameters are used as the properties of the level.
	 * @param world
	 *     The level's layout/world.
	 * @param worldType
	 *     The world's type (jungle, city, beach, etc.) given as an integer (once biome numbers are decided they can
	 *     be added here for description).
	 * @param difficulty
	 *     The level's difficulty given as an integer.
	 * @param levelType
	 *     The level's type given as an integer.
	 *         0 for safe zone level
	 *         1 for standard level
	 *         2 for boss level 
	 */
	public Level(World world, int worldType, int difficulty, int levelType) {
		this.world = world;
		this.worldType = worldType;
		this.difficulty = difficulty;
		this.levelType = levelType;
	}
	
	// ACCESSOR METHODS
	
	/**
	 * Gets the level's world/layout.
	 * @return
	 *     Returns the level's world/layout
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Gets the level's world type.
	 * @return
	 *     Returns the level's world type
	 */
	public int getWorldType() {
		return worldType;
	}
	
	/**
	 * Gets the level's difficulty
	 * @return
	 *     Returns the level's difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}
	
	/**
	 * Gets the level's type
	 * @return
	 *     Returns the level's type
	 */
	public int getLevelType() {
		return levelType;
	}
	
	// MANIPULATING METHODS
	
	/**
	 * Sets the level's world/layout to the specified new world.
	 * @param newWorld
	 *     The world/layout to set the level's world to
	 */
	public void setWorld(World newWorld) {
		world = newWorld;
	}
	
	/**
	 * Sets the level's world type to the specified new type.
	 * @param newWorldType
	 *     The type to set the level's world type to
	 *         0: no world type
	 *         1: abandoned suburbs
	 *         2: desolate forest
	 *         3: fungal wasteland
	 */
	public void changeWorldType(int newWorldType) {
		worldType = newWorldType;
	}
	
	/**
	 * Sets the level's difficulty to the specified new difficulty
	 * @param newDifficulty
	 *     The difficulty to set the level's difficulty to
	 */
	public void changeDifficulty(int newDifficulty) {
		difficulty = newDifficulty;
	}
	
	/**
	 * Sets the level's level type to the specified new type.
	 * @param newLevelType
	 *     The level type to set the level's level type to
	 */
	public void changeLevelType(int newLevelType) {
		levelType = newLevelType;
	}
}