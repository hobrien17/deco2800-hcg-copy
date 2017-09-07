package com.deco2800.hcg.entities;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worlds.AbstractWorld;

public class ItemEntity extends AbstractEntity implements Tickable {
    
    protected Item item;
    
    public ItemEntity(float posX, float posY, float posZ, Item item) {
        super(posX, posY, posZ, 1, 1, 1);
        this.item = item;
    }
    
    @Override
    public void onTick(long gameTickCount) {
        AbstractWorld world = GameManager.get().getWorld();
        
        // Check to see if we're colliding with any other item entities and try and
        // merge with them if we can
        if(this.item.isStackable()) {
            for(AbstractEntity entity: world.getEntities()) {
                if(entity instanceof ItemEntity && this.collidesWith(entity)) {
                    ItemEntity otherItem = (ItemEntity) entity;
                    if(this.getItem().sameItem(otherItem.getItem())) {
                        if(otherItem.getItem().addToStack(this.getItem().getStackSize())) {
                            world.removeEntity(this);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public String getTexture() {
        // TODO fix this
        return "spacman";
    }
    
    /**
     * Returns the item this entity is holding
     * @return This entity's item
     */
    public Item getItem() {
        return this.item;
    }
}
