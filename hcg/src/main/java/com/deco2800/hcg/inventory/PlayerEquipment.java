package com.deco2800.hcg.inventory;

import com.deco2800.hcg.items.Item;

public class PlayerEquipment extends FixedSizeInventory {
    private SlotRestriction[] slotRestrictions;
    
    private PlayerEquipment() {
        super(2);
    }
    
    @Override
    public boolean allowItemInSlot(Item item, int index) throws IndexOutOfBoundsException {
        if(this.slotRestrictions[index] != null) {
            return this.slotRestrictions[index].allowItem(item) && super.allowItemInSlot(item, index);
        }
        
        return true;
    }
    
    /**
     * Factory method to get basic PlayerEquipment instance with restrictions placed
     * on slots ahead of time.
     * 
     * @return A ready to use instance of PlayerEquipment
     */
    public static PlayerEquipment getPlayerEquipment() {
        PlayerEquipment equipment = new PlayerEquipment();
        
        return equipment;
    }
}
