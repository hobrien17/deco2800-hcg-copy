package com.deco2800.hcg.items.stackable;

import com.deco2800.hcg.items.StackableItem;
import com.deco2800.hcg.entities.Character;

public abstract class ConsumableItem extends StackableItem {
    
    /**
     * Consume one dose of this item and apply any effects to the consumer.
     * 
     * @param character
     *            The character to apply consumption effects to.
     */
    public abstract void consume(Character character);
}
