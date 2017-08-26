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
    public boolean insertItem(Item item, int index) throws IndexOutOfBoundsException {
        if(this.items[index] == null) {
            this.items[index] = item;
            return true;
        } else if(this.items[index].sameItem(item)) {
            return this.items[index].addToStack(item.getStackSize());
        }
        
        return false;
    }
    
    @Override
    public boolean addItem(Item item) {
        if(this.canInsert(item)) {
            if(item.isStackable()) {
                int toAdd = item.getStackSize();
                for(int i = 0; i < this.getMaxSize(); i++) {
                    Item currentItem = this.items[i];
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
                    for(int i = 0; i < this.getMaxSize(); i++) {
                        if(items[i] == null) {
                            items[i] = item;
                        }
                    }
                }
            } else {
                for(int i = 0; i < this.getMaxSize(); i++) {
                    if(items[i] == null) {
                        items[i] = item;
                    }
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
                "Fixed Size Inventory (%d / %d items)", 
                this.getNumItems(), this.getMaxSize()));
        
        for(Item item : this) {
            builder.append(String.format(" %d x %s ", item.getStackSize(), item.getName()));
        }
        
        return builder.toString();
    }
}
