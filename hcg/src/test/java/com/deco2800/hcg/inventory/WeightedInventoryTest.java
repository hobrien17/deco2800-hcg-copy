package com.deco2800.hcg.inventory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.deco2800.hcg.items.stackable.TestItem;
import com.deco2800.hcg.items.single.TestUniqueItem;

public class WeightedInventoryTest {
    
    @Test
    public void TestWeightLimit() {
        int weightLimit = 11;
        WeightedInventory inventory = new WeightedInventory(weightLimit);

        assertTrue("Empty inventory's empty space is not its weight limit.", 
                inventory.getFreeSpace() == weightLimit);
        assertTrue("Empty inventory's max size is not its weight limit.", 
                inventory.getMaxSize() == weightLimit);
        
       TestItem item = new TestItem();
       item.addToStack(1);
       
       assertTrue("Was not able to add an item to inventory.",
               inventory.addItem(item));
       
       assertFalse("Was able to over-encumer an inventory.",
               inventory.addItem(item));
       
       inventory.setWeightLimit(100);

       TestItem item2 = new TestItem();
       item2.addToStack(5);
       inventory.addItem(item2);

       TestItem item3 = new TestItem();
       item3.addToStack(2);
       inventory.addItem(item3);
       
       TestUniqueItem uItem1 = new TestUniqueItem("Unique Item", 5);
       uItem1.setUniqueData("ONE");
       TestUniqueItem uItem2 = new TestUniqueItem("Unique Item", 5);
       uItem2.setUniqueData("TWO");
       
       assertTrue("Cannot fit item into inventory",
               inventory.canInsert(uItem1));
       
       inventory.addItem(uItem1);
       
       assertTrue("Unique item not found in inventory.",
               inventory.containsItem(uItem1));
       
       assertFalse("Unique item not present in inventory was found in inventory.",
               inventory.containsItem(uItem2));
       
       inventory.addItem(uItem2);
       
       /*assertThat("Improper amount of items in inventory.",
               inventory.getNumItems(), is(4));*/
    }


}
