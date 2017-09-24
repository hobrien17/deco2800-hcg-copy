package com.deco2800.hcg.items;

import com.badlogic.gdx.graphics.Color;

/**
 * Borderlands-esque item rarity. Each item has one of these.
 * 
 * @author WillCS
 */
public enum ItemRarity {
    COMMON("Common", Color.WHITE),
    UNCOMMON("Uncommon", Color.GREEN),
    RARE("Rare", Color.BLUE),
    LEGENDARY("Legendary", Color.ORANGE);
    
    /** The colour to use when rendering this item's name and for loot beams. */
    public final Color colour;
    /** The name of this rarity. */
    public final String rarity;
    
    ItemRarity(String rarity, Color colour) {
        this.rarity = rarity;
        this.colour = colour;
    }
}
