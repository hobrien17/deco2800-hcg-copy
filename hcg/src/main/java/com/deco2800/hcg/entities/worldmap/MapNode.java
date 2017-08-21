package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;
import java.util.List;

public class MapNode {
	// used to calculate nodePositionX based on nodeColumn
	private int COLUMN_OFFSET = 0; // <- placeholder value
	// used to calculate nodePositionY based on nodeRow
	private int ROW_OFFSET = 0; // <- placeholder value
	
	private List<MapNode> previousNodes;
	private List<MapNode> proceedingNodes;
	private String nodeTexture;
	private int nodePositionX;
	private int nodePositionY;
	private int nodeColumn;
	private int nodeRow;
	/* Type of node to display
	 * 0 for safe node.
	 * 1 for hidden node (not yet discovered).
	 * 2 for discovered node.
	 * 3 for cleared node.
	 * 4 for boss node.
	 */
	private int nodeType;
	//private Level linkedLevel; <- add once level class is available.
	
	public MapNode(int column, int row, String texture, int type ) {
		nodeTexture = texture;
		nodeColumn = column;
		nodeRow = row;
		nodeType = type;
		nodePositionX = nodeColumn * COLUMN_OFFSET;
		nodePositionY = nodeRow * ROW_OFFSET;
		previousNodes = new ArrayList<>();
		proceedingNodes = new ArrayList<>();
	}
	
	// ACCESSOR METHODS
	public List<MapNode> getPreviousNodes() {
		return new ArrayList<MapNode>(previousNodes);
	}
	
	public List<MapNode> getProceedingNodes() {
		return new ArrayList<MapNode>(proceedingNodes);
	}
	
	public String getNodeTexture() {
		return nodeTexture;
	}
	
	public int getNodeX() {
		return nodePositionX;
	}
	
	public int getNodeY() {
		return nodePositionY;		
	}
	
	public int getNodeColumn() {
		return nodeColumn;
	}
	
	public int getNodeRow() {
		return nodeRow;
	}
	
	public int getNodeType() {
		return nodeType;
	}
	
	/* public Level getNodeLinkedLevel() {
	 *     return linkedLevel;
	 * }
	 */
	
	// MANIPULATING METHODS
	public void addPreviousNode(MapNode node) {
		if(!(previousNodes.contains(node))) {
			previousNodes.add(node);
		}
	}
	
	public void addPreviousNodeCollection(List<MapNode> nodeList) {
		for(MapNode node : nodeList) {
			if(!(previousNodes.contains(node))) {
				previousNodes.add(node);
			}
		}
	}
	
	public void addProceedingNode(MapNode node) {
		if(!(proceedingNodes.contains(node))) {
			proceedingNodes.add(node);
		}
	}
	
	public void addProceedingNodeCollection(List<MapNode> nodeList) {
		for(MapNode node : nodeList) {
			if(!(proceedingNodes.contains(node))) {
				proceedingNodes.add(node);
			}
		}
	}
	
	// possibly need ability to remove nodes from these lists?
	
	public void changeTexture(String newTexture) {
		nodeTexture = newTexture;
	}
	
	public void changeNodeType(Integer newType) {
		nodeType = newType;		
	}
	
	/* public void changeLinkedLevel(Level newLevel) {
	 *     linkedLevel = newLevel;
	 * }
	 */	
}
