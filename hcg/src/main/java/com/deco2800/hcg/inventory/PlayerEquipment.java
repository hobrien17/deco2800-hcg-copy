package com.deco2800.hcg.inventory;

import com.deco2800.hcg.items.Item;

public class PlayerEquipment extends FixedSizeInventory {
    private SlotRestriction[] slotRestrictions;
    
    private PlayerEquipment() {
        super(2);
        this.slotRestrictions = new SlotRestriction[2];
    }
    
    @Override
    public boolean allowItemInSlot(Item item, int index) throws IndexOutOfBoundsException {
        if(this.slotRestrictions[index] != null) {
            return this.slotRestrictions[index].allowItem(item) && super.allowItemInSlot(item, index);
        }
        
        return true;
    }
    
    /**
     * For the time being, the currently equipped item is the item in slot 0.
     * @return The currently equipped item.
     */
    public Item getCurrentEquippedItem() {
        return this.getItem(0);
    }
    
    /**
     * Factory method to get basic PlayerEquipment instance with restrictions placed
     * on slots ahead of time.
     * 
     * @return A ready to use instance of PlayerEquipment
     */
    public static PlayerEquipment getPlayerEquipment() {
        PlayerEquipment equipment = new PlayerEquipment();
        SlotRestriction equipmentRestriction = new SlotRestriction(equipment);
        equipmentRestriction.addCondition((inv, item) -> item.isEquippable());
        equipment.slotRestrictions[0] = equipmentRestriction;
        equipment.slotRestrictions[1] = equipmentRestriction;
        return equipment;
    }
}
