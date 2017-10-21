package com.deco2800.hcg.worldmapui;

import java.util.ArrayList;
import java.util.List;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.worlds.World;

public class TutorialWorld extends WorldMap {
	/**
	 * Initialises the TutorialWorld object using the specified parameters.	
	 * @param type
	 *     The world's type (not 100% confirmed yet so for now it is not used)
	 * @param rows
	 *     The number of rows that the TutorialWorld supports
	 * @param columns
	 *     The number of columns that the TutorialWorld supports
	 * @param nodeList
	 *     The nodes which make up the TutorialWorld's playable game areas
	 */
	public TutorialWorld(int type, int rows, int columns, List<MapNode> nodeList) {
		super(type, rows, columns, nodeList);
		ArrayList<Level> tutorialLevels = new ArrayList<>();
		// First level. This will be the first level which you play. Add levels in the order they should appear in the
		// tutorial world.
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		tutorialLevels.add(new Level(new World("resources/maps/maps/grass_safeZone_02.tmx"), 0, 1, 0));
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		tutorialLevels.add(new Level(new World("resources/maps/maps/grass_safeZone_02.tmx"), 0, 1, 0));
		tutorialLevels.add(new Level(new World("resources/maps/maps/tutorial_level_01.tmx"), 1, 1, 1));
		nodeList = createMappings(tutorialLevels);
		changeContainedNodes(nodeList);
	}
	
	private List<MapNode> createMappings(ArrayList<Level> list) {
		ArrayList<MapNode> nodeList = new ArrayList<MapNode>();
		MapNode initialNode = new MapNode(0, 5, 1, list.get(0), true);
		nodeList.add(initialNode);
		for(int i = 1; i < 10; i++) {
			if(i == 3 || i == 8) {
				MapNode nodeToAdd = new MapNode(i, 5, 0, list.get(i), false);
				nodeList.add(nodeToAdd);
			} else if(i == 9) {
				MapNode nodeToAdd = new MapNode(i, 5, 3, list.get(i), false);
				nodeList.add(nodeToAdd);
			} else {
				MapNode nodeToAdd = new MapNode(i, 5, 1, list.get(i), false);
				nodeList.add(nodeToAdd);
			}
		}
		for(int i = 0; i < 10; i++) {
			if(i < 1) {
				nodeList.get(i).addProceedingNode(nodeList.get(i + 1));
			} else if(i < 9){
				nodeList.get(i).addPreviousNode(nodeList.get(i - 1));
				nodeList.get(i).addProceedingNode(nodeList.get(i + 1));
			} else {
				nodeList.get(i).addPreviousNode(nodeList.get(i - 1));
			}
		}
		return nodeList;
	}
}
