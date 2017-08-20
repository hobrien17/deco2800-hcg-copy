package com.deco2800.hcg.items;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class StackableItemTest {

    @Test
    public void TestStackLimit(){
        int maxStackSize = 64;
        String itemName = "Cobblestone";
        int itemWeight = 10;

        BasicStackableItem cobblestone = new BasicStackableItem(itemName, itemWeight, maxStackSize);

        /** Test Initialisation values **/
        assertTrue("Item Stack Limit not set to initialised value", cobblestone.maxStackSize()==maxStackSize);
        assertTrue("Weight not Initialised to 0", cobblestone.getWeight()==0);
        assertTrue("itemName not set properly", cobblestone.getName().equals(itemName));
        assertFalse("Stackabe items should not be wearable", cobblestone.isWearable());


        // TODO: Ensure deep copies are made and initialisation values aren't referencing external objects

        // TODO: Test functionality and edge cases
    }

    public void TestNonstackableItem(){
        //TODO: Test non stackable items
    }
}

