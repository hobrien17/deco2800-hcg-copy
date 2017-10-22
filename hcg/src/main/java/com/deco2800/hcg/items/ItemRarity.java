package com.deco2800.hcg.items;

import com.badlogic.gdx.graphics.Color;

/**
 * Borderlands-esque item rarity. Each item has one of these.
 * 
 * @author WillCS
 */
public enum ItemRarity {
    COMMON("Common", Color.WHITE, 0.5F),
    UNCOMMON("Uncommon", Color.GREEN, 1.0F),
    RARE("Rare", Color.BLUE, 1.2F),
    LEGENDARY("Legendary", Color.ORANGE, 1.5F);
    
    /** The colour to use when rendering this item's name and for loot beams. */
    public final Color colour;
    /** The name of this rarity. */
    public final String rarity;
    /** The size of an item with this rarity's loot beams. */
    public final float beamSize;
    
    ItemRarity(String rarity, Color colour, float beamSize) {
        this.rarity = rarity;
        this.colour = colour;
        this.beamSize = beamSize;
    }
}
