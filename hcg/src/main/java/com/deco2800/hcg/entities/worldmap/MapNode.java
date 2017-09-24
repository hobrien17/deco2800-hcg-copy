package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds information on a world map node.
 * Contains nodes which connect to the node (both towards the node and away from the node), the node's texture, the
 * node's x and y positions on the screen, the node's row and column within the map grid, the node's type (safe node,
 * standard node, cleared node or boss node), the node's linked level and whether the node is selected on the map (for
 * node selection using the keyboard) or has been discovered by the player.
 */
public class MapNode {
	
	private List<MapNode> previousNodes;
	private List<MapNode> proceedingNodes;
	private int nodeID;
	private int nodeColumn;
	private int nodeRow;
	/* Type of node to display
	 * 0 for safe node.
	 * 1 for standard node.
	 * 2 for cleared node.
	 * 3 for boss node.
	 */
	private int nodeType;
	private Level linkedLevel;
	private boolean selected;
	private boolean isDiscovered;
	
	private static int nodeCount = 0;

	/* These are the screen positions of the nodes on the world map screen.
	 * Note: The engine uses all sprite co-ordinates from the bottom left corner of the sprite, however when these are
	 * set from MapNodeEntity, they are actually calculated to be the center point of the sprite since we are using them
	 * to draw lines between the nodes.
	 */
	private int xPos;
	private int yPos;
	
	/**
	 * Initialises a new MapNode object based on the specified parameters.
	 * @param column
	 *     The map column that the node is to be situated in
	 * @param row
	 *     The map row that the node is to be situated in
	 * @param type
	 *     The type of the node:
	 *         0 for safe node
	 *         1 for standard node
	 *         2 for cleared node
	 *         3 for boss node
	 * @param level
	 *     The level that is associated with the node (what level will load when the node is clicked)
	 * @param discovered
	 *     Whether the node has been discovered by the player
	 */
	public MapNode(int column, int row, int type, Level level, boolean discovered) {
		nodeColumn = column;
		nodeRow = row;
		nodeType = type;
		linkedLevel = level;
		previousNodes = new ArrayList<>();
		proceedingNodes = new ArrayList<>();
		selected = false;
		isDiscovered = discovered;
		if(nodeType == 0) {
			this.nodeID = -1;
		} else {
			this.nodeID = MapNode.nodeCount;
			MapNode.nodeCount++;
		}
	}
	
	// ACCESSOR METHODS
	
	/**
	 * Gets the node's previous attaching nodes
	 * @return
	 *     Returns a list of MapNodes which are in the column before and attach to this node
	 */
	public List<MapNode> getPreviousNodes() {
		return new ArrayList<MapNode>(previousNodes);
	}
	
	/**
	 * Gets the node's proceeding attached nodes
	 * @return
	 *     Returns a list of MapNodes which are in the column after and attach to this node
	 */
	public List<MapNode> getProceedingNodes() {
		return new ArrayList<MapNode>(proceedingNodes);
	}
	
	/**
	 * Gets the node's column number in the map grid.
	 * @return
	 *     Returns the node's current column number
	 */
	public int getNodeColumn() {
		return nodeColumn;
	}
	
	/**
	 * Gets the node's row number in the map grid.
	 * @return
	 *     Returns the node's current row number
	 */
	public int getNodeRow() {
		return nodeRow;
	}
	
	/**
	 * Gets the node's type.
	 * @return
	 *     Returns the node's current type
	 */
	public int getNodeType() {
		return nodeType;
	}
	
	/**
	 * Gets the node's associated level.
	 * @return
	 *     Returns the level currently linked to this node
	 */
	public Level getNodeLinkedLevel() {
		return linkedLevel;
	}

	/**
	 * Gets the nodes sprite center point screen x position
	 * @return
	 * 		Returns the x co-ordinate
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * Sets the nodes sprite center point screen x position
	 * @param xPos the new center point screen x position
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * Gets the nodes sprite center point screen y position
	 * @return
	 * 		Returns the y co-ordinate
	 */
	public int getYPos() {
		return yPos;
	}

	/**
	 * Sets the nodes sprite center point screen y position
	 * @param yPos the new center point screen y position
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * Gets the node's selection status.
	 * @return
	 *     Returns whether or not the node is currently selected by the player using keyboard controls
	 * 
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Gets the node's discovered status.
	 * @return
	 *     Returns whether the player has discovered this node by playing through the map
	 */
	public boolean isDiscovered() {
		return isDiscovered;
	}
	
	/**
	 * Gets the node's ID.
	 * @return
	 *     Returns the node's ID
	 */
	public int getNodeID() {
		return nodeID;
	}
	
	// MANIPULATING METHODS
	
	/**
	 * Adds a node to the list of nodes in the previous column which connect to this node. 
	 * @param node
	 *     The node to add to the list of previous nodes
	 */
	public void addPreviousNode(MapNode node) {
		if(!(previousNodes.contains(node))) {
			previousNodes.add(node);
		}
	}
	
	/**
	 * Adds a collection of nodes to the list of nodes in the previous column which connect to this node. 
	 * @param nodeList
	 *     The nodes to add to the list of previous nodes
	 */
	public void addPreviousNodeCollection(List<MapNode> nodeList) {
		for(MapNode node : nodeList) {
			if(!(previousNodes.contains(node))) {
				previousNodes.add(node);
			}
		}
	}
	
	/**
	 * Adds a node to the list of nodes in the proceeding column which connect to this node. 
	 * @param node
	 *     The node to add to the list of proceeding nodes
	 */
	public void addProceedingNode(MapNode node) {
		if(!(proceedingNodes.contains(node))) {
			proceedingNodes.add(node);
		}
	}
	
	/**
	 * Adds a collection of nodes to the list of nodes in the proceeding column which connect to this node. 
	 * @param nodeList
	 *     The nodes to add to the list of proceeding nodes
	 */
	public void addProceedingNodeCollection(List<MapNode> nodeList) {
		for(MapNode node : nodeList) {
			if(!(proceedingNodes.contains(node))) {
				proceedingNodes.add(node);
			}
		}
	}
	
	/**
	 * Changes the node type to a new type.
	 * @param newType
	 *     The node type to change to
	 */
	public void changeNodeType(Integer newType) {
		nodeType = newType;		
	}
	
	/**
	 * Changes the linked level of the node.
	 * @param newLevel
	 *     The new level to be linked to the node
	 */
	public void changeLinkedLevel(Level newLevel) {
		linkedLevel = newLevel;
	}
	
	/**
	 * Sets the current node to be selected by the player.
	 * ONLY ONE NODE IS TO BE SELECTED AT A TIME.
	 */
	public void selectNode() {
		selected = true;
	}
	
	/**
	 * Sets the current node to be unselected by the player.
	 */
	public void unselectNode() {
		selected = false;
	}
	
	/**
	 * Sets the node to be discovered by the player.
	 * Called when a player clears a node in the nodes which are previous to this one.
	 */
	public void discoverNode() {
		isDiscovered = true;
	}
	
	/**
	 * Sets the node to be hidden from the player.
	 * Called when a player dies and is returned to the last safe zone they visited.
	 */
	public void hideNode() {
		isDiscovered = false;
	}
	
	/**
	 * Sets a new ID for the node.
	 * @param newID
	 *     The new ID for the node.
	 */
	public void setNodeID(int newID) {
		nodeID = newID;
	}
	
	/**
	 * To string method. Helps with determining information about the node.
	 */
	@Override
	public String toString() {
		String nodeTypeString;
		String newline = System.getProperty("line.separator");
		switch (nodeType) {
			case 0:
				nodeTypeString = "Safe Node";
				break;
			case 1:
				nodeTypeString = "Standard Node";
				break;
			case 2:
				nodeTypeString = "Cleared Node";
				break;
			case 3:
				nodeTypeString = "Boss Node";
				break;
			default:
				nodeTypeString = "Default Node";
		}
		String nodeString = "nodeType: " + nodeTypeString + " | nodeRow: " + nodeRow + " | nodeColumn: " + 
				nodeColumn + " | nodeLevel: " + linkedLevel + newline +
				"Previous Nodes:" + newline;
		for(MapNode node : previousNodes) {
			nodeString += "nodeType: " + node.getNodeType() + " | nodeRow: " + node.getNodeRow() + " | nodeColumn: " +
					node.getNodeColumn() + newline;
		}
		nodeString += "Proceeding Nodes:" + newline;
		for(MapNode node : proceedingNodes) {
			nodeString += "nodeType: " + node.getNodeType() + " | nodeRow: " + node.getNodeRow() + " | nodeColumn: " +
					node.getNodeColumn() + newline;
		}
		return nodeString;
	}
}
