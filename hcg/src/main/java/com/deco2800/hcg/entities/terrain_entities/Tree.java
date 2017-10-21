package com.deco2800.hcg.entities.terrain_entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.shading.LightEmitter;

/**
 * @author Ken
 */
public class Tree extends TerrainEntity implements LightEmitter {

    private TreeType type;
    private Random random = new Random();
    private ArrayList<TreeType> treeTypes;

    /**
     * constructs a new tree. if not random, defaults to basic leafy tree
     *
     * @param posX x position of the tree
     * @param posY y position of the tree
     * @param posZ z position of the tree
     * @param random whether the tree will be of random type/state
     */
    public Tree(float posX, float posY, float posZ, boolean random) {

        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);

        treeTypes = treeTypes();
        if (random) {
            randomiseType();
        } else {
            type = TreeType.LEAFY;
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
    @SuppressWarnings("unused")
    public Tree(float posX, float posY, float posZ, TreeType type) {
        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);

        treeTypes = treeTypes();
        this.type = type;
        this.setTexture();
    }

    /**
     * set texture based on tree type and state
     */
    public void setTexture() {
        switch (type){
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
            case SNOWY:
                this.setTexture("tree_snowy");
                break;
            case FLAMING:
                this.setTexture("tree_flaming");
                break;
            default:
                break;
        }
    }

    /**
     * generates a list of tree types
     * @return list of tree types
     */
    private ArrayList<TreeType> treeTypes() {
        ArrayList<TreeType> types = new ArrayList<>();
        Collections.addAll(types, TreeType.values());
        return types;
    }

    /**
     * randomises the tree type
     */
    private void randomiseType(){
        int index = random.nextInt(treeTypes.size());
        this.setType(treeTypes().get(index));
    }

    /**
     * @return the type of this tree
     */
    public TreeType getType() {
        return type;
    }

    /**
     * set the type of this tree
     * @param t the type to set the tree to
     */
    public void setType(TreeType t) {
        this.type = t;
        setTexture();
    }

    @Override
    public Color getLightColour() {
        switch(this.type) {
            case FLAMING:
                return Color.ORANGE;
            default:
                return Color.WHITE;
        }
    }

    @Override
    public float getLightPower() {
        switch(this.type) {
            case FLAMING:
                return 5;
            default:
                return 0;
        }
    }
}
