package com.deco2800.hcg.inventory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.deco2800.hcg.items.Item;

/**
 * Basic implementation of an inventory with a weight limit applied to it.
 */
public class WeightedInventory implements Inventory {

    private List<Item> items;
    private int weightLimit;

    /**
     * Initialize a new instance of WeightedInventory with a given weight
     * limit.
     */
    public WeightedInventory(int weightLimit) {
        this.weightLimit = weightLimit;
        this.items = new LinkedList<>();
    }

    @Override
    public Iterator<Item> iterator() {
        return this.items.iterator();
    }

    @Override
    public int getMaxSize() {
        return this.getNumItems() + this.getFreeSpace();
    }

    @Override
    public int getNumItems() {
        return this.items.size();
    }

    @Override
    public Item getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public Item removeItem(int index) {
        return this.items.remove(index);
    }

    @Override
    public boolean canInsert(Item item) {
        return item.getWeight() + this.getCarriedWeight() <= this
                .getWeightLimit();
    }

    @Override
    public boolean insertItem(Item item, int index) {
        if (this.canInsert(item)) {
            if (index > this.getNumItems()) {
                index = this.getNumItems();
            }

            this.items.add(index, item);
            return true;
        }

        return false;
    }

    @Override
    public boolean appendItem(Item item) {
        if (this.canInsert(item)) {
            this.items.add(item);
            return true;
        }

        return false;
    }

    /**
     * Retrieves the weight limit for this inventory.
     *
     * @return The max amount of weight able to be stored in this inventory.
     */
    public int getWeightLimit() {
        return this.weightLimit;
    }

    /**
     * Retrieves the amount of free space left in this inventory.
     *
     * @return The amount of free space left in this inventory.
     */
    public int getFreeSpace() {
        return this.getWeightLimit() - this.getCarriedWeight();
    }

    /**
     * Retrieves the amount of weight currently stored in this inventory.
     *
     * @return The amount of weight currently stored in this inventory.
     */
    public int getCarriedWeight() {
        int weight = 0;
        for (Item item : this) {
            weight += item.getWeight();
        }

        return weight;
    }
}
