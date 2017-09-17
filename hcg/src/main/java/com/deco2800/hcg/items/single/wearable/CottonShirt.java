package com.deco2800.hcg.items.single.wearable;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.single.WearableItem;

/**
 * This class represents a type of shirt that can be
 * worn by the player.
 */
public class CottonShirt extends WearableItem{
    private ShirtColour colour;

    public enum ShirtColour {RED, YELLOW, GREEN, BLUE, BLACK, WHITE, GREY, PURPLE}

    /**
     * Constructor of the CottonShirt class. It creates a new CottonShirt
     * of the given colour
     * @param colour the colour of the shirt
     */
    public CottonShirt(ShirtColour colour){
        this.colour = colour;
        this.itemName = "Cotton Shirt";
        this.type = WearableType.CHEST;
        this.baseValue = 10;
        this.itemWeight = 3;
        //TODO: Change this texture from defaulting to spacman -> this is for testing
        this.texture = "spacman";
    }

    /**
     * Gets the shirt colour
     * @return enumerated colour of shirt
     */
    public ShirtColour getColour() {
        return this.colour;
    }

    /**
     * Checks whether or not this item is able to be sold to shops.
     *
     * @return Whether or not this item can be traded.
     */
    @Override
    public boolean isTradable() {
        return true;
    }

    /**
     * Retrieves the Display name
     * @return The name and current colour
     */
    @Override
    public String getName() {
        return String.format("Name: %s\nType: %s\nDetails: %s\n", this.itemName, this.type + " " + this.colour,
                "A simple shirt. Doesnt really do much...");
    }

    @Override
    public Item copy() {
        return new CottonShirt(colour);
    }
}
