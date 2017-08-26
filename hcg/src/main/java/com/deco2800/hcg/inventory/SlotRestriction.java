package com.deco2800.hcg.inventory;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import com.deco2800.hcg.items.Item;

/**
 * Container class to manage the restrictions on a specific / multiple slots in
 * an Inventory. SlotRestrictions can require an item to pass all conditions or
 * just one.
 * 
 * @author WillCS
 */
public class SlotRestriction {
    private Inventory inventory;
    private List<BiFunction<Inventory, Item, Boolean>> conditions;
    private boolean requireAll;
    
    /**
     * Create a new SlotRestriction for a slot in {@code Inventory}
     * 
     * @param inventory
     *            The inventory containing the slot/slots where this restriction
     *            will be applied.
     */
    public SlotRestriction(Inventory inventory) {
        this.inventory = inventory;
        this.conditions = new LinkedList<>();
        this.requireAll = true;
    }
    
    /**
     * Create a new SlotRestriction for a slot in {@code Inventory}
     * 
     * @param inventory
     *            The inventory containing the slot/slots where this restriction
     *            will be applied.
     * @param requireAll
     *            Whether or not an item needs to meet all the requirements of the
     *            restriction or just one. Defaults to all.
     */
    public SlotRestriction(Inventory inventory, boolean requireAll) {
        this.inventory = inventory;
        this.conditions = new LinkedList<>();
        this.requireAll = requireAll;
    }
    
    /**
     * Add a new condition to this restriction.
     * 
     * @param condition
     *            The condition to be added to this restriction.
     */
    public void addCondition(BiFunction<Inventory, Item, Boolean> condition) {
        this.conditions.add(condition);
    }

    /**
     * Remove a condition from this restriction.
     * 
     * @param condition
     *            The condition to be removed from this restriction.
     */
    public void removeCondition(BiFunction<Inventory, Item, Boolean> condition) {
        this.conditions.remove(condition);
    }
    
    /**
     * Check to make sure {@code item} meets the conditions of the restriction.
     * 
     * @param item
     *            The item to check
     */
    public boolean allowItem(Item item) {
        for(BiFunction<Inventory, Item, Boolean> function : this.conditions) {
            if(!function.apply(this.inventory, item) && this.requireAll) {
                return false;
            } else if(function.apply(this.inventory, item) && !this.requireAll) {
                return true;
            }
        }
        
        return this.requireAll; // cheeky
    }
}
