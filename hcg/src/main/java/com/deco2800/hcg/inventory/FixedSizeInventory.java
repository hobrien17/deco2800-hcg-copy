package com.deco2800.hcg.inventory;

import java.util.Arrays;
import java.util.Iterator;

import com.deco2800.hcg.items.Item;

/** Basic implementation of an inventory with a fixed number of item slots. */
public class FixedSizeInventory implements Inventory {
    private int size;
    private Item[] items;
    
    public FixedSizeInventory(int size) {
        this.size = size;
        this.items = new Item[size];
    }
    
    @Override
    public Iterator<Item> iterator() {
        return Arrays.asList(this.items).iterator();
    }
    
    @Override
    public int getMaxSize() {
        return this.size;
    }
    
    @Override
    public int getNumItems() {
        int itemsFound = 0;
        for(int i = 0; i < this.getMaxSize(); i++) {
            if(this.items[i] != null) {
                itemsFound++;
            }
        }
        
        return itemsFound;
    }
    
    @Override
    public Item getItem(int index) throws IndexOutOfBoundsException {
        return this.items[index];
    }
    
    @Override
    public Item removeItem(int index) throws IndexOutOfBoundsException {
        Item item = this.items[index];
        this.items[index] = null;
        return item;
    }
    
    @Override
    public boolean removeItem(Item item) {
        if(this.containsItem(item)) {
            int toRemove = item.getStackSize();
            for(int i = 0; i < this.getMaxSize(); i++) {
                Item currentItem = this.items[i];
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
    public boolean canInsert(Item item) {
        int spaceFound = 0;
        for(int i = 0; i < this.getMaxSize(); i++) {
            if(this.items[i] == null) {
                return true;
            } else if(this.items[i].sameItem(item)) {
                spaceFound += this.items[i].getMaxStackSize() - this.items[i].getStackSize();
                if(spaceFound >= item.getStackSize()) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public boolean canFitItemInSlot(Item item, int index) throws IndexOutOfBoundsException {
        if(this.items[index] == null) {
            return true;
        } else if(this.items[index].sameItem(item)) {
            return items[index].getStackSize() + item.getStackSize() >= item.getMaxStackSize();
        }
        
        return false;
    }
    
    @Override
    public boolean insertItem(Item item, int index) throws IndexOutOfBoundsException {
        if(this.allowItemInSlot(item, index) && canFitItemInSlot(item, index)) {
            if(this.items[index] == null) {
                this.items[index] = item;
            } else {
                this.items[index].addToStack(item.getStackSize());
            }
            
            return true;
        }
        
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
        int toAdd = item.getStackSize();
        
        if(this.canInsert(item)) {
            if(item.isStackable()) {
                for(int i = 0; i < this.getMaxSize(); i++) {
                    Item currentItem = this.items[i];
                    if(this.allowItemInSlot(currentItem, i)) {
                        if(currentItem == null) {
                            this.items[i] = item;
                            return true;
                        } else if(item.sameItem(currentItem)) {
                            if(!currentItem.addToStack(toAdd)) {
                                toAdd -= currentItem.getMaxStackSize() - currentItem.getStackSize();
                                currentItem.setStackSize(currentItem.getMaxStackSize());
                            } else {
                                return true;
                            }
                        }
                        
                        if(toAdd <= 0) {
                            return true;
                        }
                    }
                }
                
                if(toAdd > 0) {
                    item.setStackSize(toAdd);
                }
            }
            
            for(int i = 0; i < this.getMaxSize(); i++) {
                if(items[i] == null && allowItemInSlot(item, i)) {
                    items[i] = item;
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public boolean containsItem(Item item) {
        int numFound = 0;
        for(int i = 0; i < this.getMaxSize(); i++) {
            if(this.items[i] != null && this.items[i].sameItem(item)) {
                numFound += this.items[i].getStackSize();
                
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
                "Fixed Size Inventory (%d / %d items)", 
                this.getNumItems(), this.getMaxSize()));
        
        for(Item item : this) {
            builder.append(String.format(" %d x %s ", item.getStackSize(), item.getName()));
        }
        
        return builder.toString();
    }
}
