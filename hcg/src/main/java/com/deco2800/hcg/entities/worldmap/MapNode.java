package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;
import java.util.List;

import com.deco2800.hcg.entities.AbstractEntity;

public class MapNode extends AbstractEntity {
	// used to calculate nodePositionX based on nodeColumn
	private static final float COLUMN_OFFSET = 1; // <- placeholder value
	// used to calculate nodePositionY based on nodeRow
	private static final float ROW_OFFSET = 1; // <- placeholder value
	
	private List<MapNode> previousNodes;
	private List<MapNode> proceedingNodes;
	private String nodeTexture;
	private float nodePositionX;
	private float nodePositionY;
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
	private Level linkedLevel;
	private boolean selected;
	
	public MapNode(int column, int row, String texture, int type, Level level) {
		super(column * COLUMN_OFFSET, row * ROW_OFFSET, 0.0f, 1, 1, 1);
		nodeTexture = texture;
		this.setTexture(nodeTexture); // to render the node on the world
		nodeColumn = column;
		nodeRow = row;
		nodeType = type;
		nodePositionX = nodeColumn * COLUMN_OFFSET;
		nodePositionY = nodeRow * ROW_OFFSET;
		linkedLevel = level;
		previousNodes = new ArrayList<>();
		proceedingNodes = new ArrayList<>();
		selected = false;
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
	
	public float getNodeX() {
		return nodePositionX;
	}
	
	public float getNodeY() {
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
	
	public Level getNodeLinkedLevel() {
		return linkedLevel;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
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
	
	public void changeLinkedLevel(Level newLevel) {
		linkedLevel = newLevel;
	}
	
	public void selectNode() {
		selected = true;
	}
	
	public void unselectNode() {
		selected = false;
	}
}
