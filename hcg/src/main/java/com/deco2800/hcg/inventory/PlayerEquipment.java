package com.deco2800.hcg.inventory;

import com.deco2800.hcg.items.Item;

/**
 * Inventory purpose built to contain the player's currently equipped items
 * (Hotbar?) with methods to easily get the currently equipped item and change
 * the equipped slot.
 */
public class PlayerEquipment extends FixedSizeInventory {
    private SlotRestriction[] slotRestrictions;
    private int equippedSlot;
    
    private PlayerEquipment(int equipSlots) {
        super(equipSlots);
        this.slotRestrictions = new SlotRestriction[equipSlots];
        this.equippedSlot = 0;
    }
    
    @Override
    public boolean allowItemInSlot(Item item, int index) throws IndexOutOfBoundsException {
        if(this.slotRestrictions[index] != null) {
            return this.slotRestrictions[index].allowItem(item) && super.allowItemInSlot(item, index);
        }
        
        return true;
    }
    
    /**
     * @return The currently equipped item.
     */
    public Item getCurrentEquippedItem() {
        return this.getItem(this.equippedSlot);
    }

    /**
     * Sets the currently equipped item slot.
     * 
     * @param slot
     *            The slot to equip.
     */
    public void setEquippedSlot(int slot) {
        this.equippedSlot = slot;
    }
    
    /**
     * Return the index of the currently equipped item slot.
     * 
     * @return the index of the currently equipped item slot.
     */
    public int getEquippedSlot() {
        return this.equippedSlot;
    }
    
    /**
     * Cycle the currently equipped slot. Equips the next item in the equipment
     * inventory, looping around to the beginning if necessary.
     */
    public void cycleEquippedSlot() {
        this.equippedSlot++;
        this.equippedSlot %= this.getMaxSize();
    }
    
    /**
     * Factory method to get basic PlayerEquipment instance with restrictions placed
     * on slots ahead of time.
     * 
     * @return A ready to use instance of PlayerEquipment
     */
    public static PlayerEquipment getPlayerEquipment() {
        PlayerEquipment equipment = new PlayerEquipment(3);
        SlotRestriction equipmentRestriction = new SlotRestriction(equipment);
        equipmentRestriction.addCondition((inv, item) -> item.isEquippable());
        equipment.slotRestrictions[0] = equipmentRestriction;
        equipment.slotRestrictions[1] = equipmentRestriction;
        return equipment;
    }
}
