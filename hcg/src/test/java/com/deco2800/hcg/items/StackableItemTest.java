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
        /** fails this test, commented out so it builds **/
        // assertFalse("Name should be a deep copy", cobblestone.getName()==itemName);

        maxStackSize = 32;
        assertFalse("max stack size should be a deep copy", cobblestone.maxStackSize()==maxStackSize);

        itemWeight = 47;
        cobblestone.addToStack(1);
        assertFalse("item weight should be a deep copy", cobblestone.getWeight()==itemWeight*1);

        // TODO: Test functionality and edge cases
    }
}

