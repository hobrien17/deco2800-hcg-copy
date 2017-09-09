package com.deco2800.hcg.entities.terrain_entities;

import java.util.ArrayList;
import java.util.Random;

public class Tree extends TerrainEntity {

    private TreeState state;
    private TreeType type;

    private Random random = new Random();

    private ArrayList<TreeType> treeTypes;
    private ArrayList<TreeState> treeStates;

    /**
     * constructs a new tree. if not random defaults to basic leafy tree
     *
     * @param posX x position of the tree
     * @param posY y position of the tree
     * @param posZ z position of the tree
     * @param random whether the tree will be of random type/state
     */
    public Tree(float posX, float posY, float posZ, boolean random) {

        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);
//        this.setCentered();

        treeTypes = treeTypes();
        treeStates = treeStates();

        if (random) {
            randomiseTree();
        } else {
            type = TreeType.BASIC;
            state = TreeState.LEAFY;
        }
        this.setTexture();
    }

    /**
     * constructs a new specific tree
     *
     * @param posX x position of the tree
     * @param posY y position of the tree
     * @param posZ z position of the tree
     * @param type type of tree
     */
    public Tree(float posX, float posY, float posZ, TreeType type) {

        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);

        treeTypes = treeTypes();
        treeStates = treeStates();
        this.type = type;
        if (type == TreeType.BASIC) {
            state = TreeState.LEAFY;
        }
        this.setTexture();
    }

    /**
     * set texture based on tree type and state
     */
    public void setTexture() {
        if (type == TreeType.FLAMING) {
            this.setTexture("tree_flaming");
        }
        else if (type == TreeType.SNOWY) {
            this.setTexture("tree_snowy");
        }
        else if (type == TreeType.BASIC){
            switch (state){
                case LEAFY:
                    this.setTexture("tree_leafy");
                    break;
                case BUDDING:
                    this.setTexture("tree_budding");
                    break;
                case LEAFLESS:
                    this.setTexture("tree_leafless");
                    break;
                case FRUITING:
                    this.setTexture("tree_fruiting");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * generates a list of tree states
     * @return list of tree states
     */
    private ArrayList<TreeState> treeStates() {
        ArrayList<TreeState> states = new ArrayList<>();
        for (TreeState state : TreeState.values()) {
            states.add(state);
        }
        return states;
    }

    /**
     * generates a list of tree types
     * @return list of tree types
     */
    private ArrayList<TreeType> treeTypes() {
        ArrayList<TreeType> types = new ArrayList<>();
        for (TreeType type : TreeType.values()) {
            types.add(type);
        }
        return types;
    }

    /**
     * randomises the tree type, and state if applicable
     */
    private void randomiseTree(){
        int index = random.nextInt(treeStates.size() + treeTypes.size() - 1);
        if (index < treeStates.size()){
            type = TreeType.BASIC;
            randomiseState();
        } else {
            type = index == 4 ? TreeType.SNOWY : TreeType.FLAMING;
        }
    }

    /**
     * randomises the tree state (currently only the BASIC tree type can be in different states)
     */
    public void randomiseState(){
        int index = random.nextInt(treeStates.size());
        this.setState(treeStates.get(index));
    }

    public TreeType getType() {
        return type;
    }

    public TreeState getState() {
        return state;
    }

    public void setState(TreeState s) {
        state = s;
        setTexture();
    }

    public void setType(TreeType t) {
        this.type = t;
        setTexture();
    }
}
