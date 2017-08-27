package com.deco2800.hcg.items.single.wearable;

import com.deco2800.hcg.items.single.WearableItem;

public class CottonShirt extends WearableItem{
    private ShirtColour colour;

    public enum ShirtColour {RED, YELLOW, GREEN, BLUE, BLACK, WHITE, GREY, PURPLE}
    public CottonShirt(ShirtColour colour){
        this.colour = colour;
        this.itemName = "Cotton Shirt";
        this.type = WearableType.CHEST;
        this.baseValue = 10;
        this.itemWeight = 3;
        this.texture = String.format("%s_cottonshirt.png", this.colour);
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
        return String.format("%s %s", this.type, this.itemName);
    }
}