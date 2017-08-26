package com.deco2800.hcg.inventory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.deco2800.hcg.items.Item;

/** Basic implementation of an inventory with a weight limit applied to it. */
public class WeightedInventory implements Inventory {
    private List<Item> items;
    private int weightLimit;
    
    /** Initialize a new instance of WeightedInventory with a given weight limit. */
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
    public Item getItem(int index) throws IndexOutOfBoundsException {
        return this.items.get(index);
    }
    
    @Override
    public Item removeItem(int index) throws IndexOutOfBoundsException {
        return this.items.remove(index);
    }
    
    @Override
    public boolean canInsert(Item item) {
        return item.getWeight() + this.getCarriedWeight() <= this.getWeightLimit();
    }
    
    @Override
    public boolean insertItem(Item item, int index) throws IndexOutOfBoundsException {
        if(this.canInsert(item)) {
            this.items.add(index, item);
            return true;
        }
        
        return false;
    }

    @Override
    public boolean canInsert(Item item, int index) throws IndexOutOfBoundsException {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean allowItemInSlot(Item item, int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= this.getMaxSize()) {
            // Even for an implementation without restrictions 
            // we don't want people calling this with silly indices
            throw new IndexOutOfBoundsException();
        }
        
        return true;
    }
    
    @Override
    public boolean addItem(Item item) {
        if(this.canInsert(item)) {
            if(item.isStackable()) {
                int toAdd = item.getStackSize();
                for(int i = 0; i < this.items.size(); i++) {
                    Item currentItem = this.getItem(i);
                    if(currentItem != null && item.sameItem(currentItem)) {
                        if(!currentItem.addToStack(toAdd)) {
                            toAdd -= currentItem.getMaxStackSize() - currentItem.getStackSize();
                            currentItem.setStackSize(currentItem.getMaxStackSize());
                        } else {
                            toAdd = 0;
                        }
                    }
                    
                    if(toAdd <= 0) {
                        break;
                    }
                }
                
                if(toAdd > 0) {
                    item.setStackSize(toAdd);
                    this.items.add(item);
                }
            } else {
                this.items.add(item);
            }
            
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
     * Sets the weight limit for this inventory.
     * 
     * @param weightLimit
     *            The new weight limit for this inventory.
     */
    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
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
        for(Item item : this) {
            weight += item.getWeight();
        }
        
        return weight;
    }

    @Override
    public boolean removeItem(Item item) {
        if(this.containsItem(item)) {
            int toRemove = item.getStackSize();
            for(int i = 0; i < this.items.size(); i++) {
                Item currentItem = this.getItem(i);
                if(currentItem != null && item.sameItem(currentItem)) {
                    if(toRemove >= currentItem.getStackSize()) {
                        toRemove -= currentItem.getStackSize();
                        this.removeItem(i);
                    } else {
                        currentItem.setStackSize(currentItem.getStackSize() - toRemove);
                        toRemove = 0;
                    }
                }
                
                if(toRemove <= 0) {
                    break;
                }
            }
            return true;
        }
        
        return false;
    }

    @Override
    public boolean containsItem(Item item) {
        int numFound = 0;
        for(Item currentItem : this) {
            if(item.sameItem(currentItem)) {
                numFound += currentItem.getStackSize();
                
                if(numFound >= item.getStackSize()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format(
                "Weighted Inventory (%d Items, %d / %d weight)", 
                this.getNumItems(), this.getCarriedWeight(), this.getWeightLimit()));
        
        for(Item item : this) {
            builder.append(String.format(" %d x %s ", item.getStackSize(), item.getName()));
        }
        
        return builder.toString();
    }
}
