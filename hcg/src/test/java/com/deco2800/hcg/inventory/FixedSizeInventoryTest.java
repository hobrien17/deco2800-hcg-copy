package com.deco2800.hcg.inventory;

import com.deco2800.hcg.items.single.TestUniqueItem;
import com.deco2800.hcg.items.stackable.TestItem;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FixedSizeInventoryTest {

    //Test an empty inventory
    @Test
    public void TestEmpty() {
        int maxSize = 11;
        FixedSizeInventory inventory = new FixedSizeInventory(maxSize);
        //Check maxSize is set properly
        assertTrue("Max size was not intilized properly", inventory.getMaxSize() == maxSize);
        //Empty inventory holds no items
        assertTrue("Empty inventory held some items", inventory.getNumItems() == 0);


       /*assertThat("Improper amount of items in inventory.",
               inventory.getNumItems(), is(4));*/
    }

    //Test adding and removing unique items
    //TODO: do this test but with stackable items
    @Test
    public void TestAdd() {
        FixedSizeInventory inventory = new FixedSizeInventory(3);
        TestUniqueItem item = new TestUniqueItem("Test Item", 5);
        TestUniqueItem item2 = new TestUniqueItem("Test Item2", 5);
        TestUniqueItem item3 = new TestUniqueItem("Test Item3", 5);
        TestUniqueItem item4 = new TestUniqueItem("Test Item4", 5);
        //Empty inventory holds no items
        assertTrue("Item couldnt be added", inventory.addItem(item));
        assertTrue("Item couldnt be added", inventory.addItem(item2));
        assertTrue("Item couldnt be added", inventory.addItem(item3));
        //Inventory is now full, this should be false
        assertFalse("Item could be added", inventory.addItem(item4));


    }

    //Test adding and removing unique items
    @Test
    public void TestInsertIndex() {
        FixedSizeInventory inventory = new FixedSizeInventory(3);
        TestUniqueItem item = new TestUniqueItem("Test Item", 5);
        inventory.insertItem(item,0);
        inventory.insertItem(item,2);
        //Check the items were added properly
        assertTrue("Item not at correct index", inventory.getItem(0) == item);
        assertTrue("Item not at correct index", inventory.getItem(2) == item);
        //Should be able to insert an item still
        assertTrue("Cant insert anymore items", inventory.canInsert(item));

    }

    //Test removing unique items
    //TODO: Do this test but with stackable items
    @Test
    public void TestRemove() {
        FixedSizeInventory inventory = new FixedSizeInventory(3);
        TestUniqueItem item = new TestUniqueItem("Test Item", 5);
        TestUniqueItem item2 = new TestUniqueItem("Test Item2", 5);
        TestUniqueItem item3 = new TestUniqueItem("Test Item3", 5);
        TestUniqueItem item4 = new TestUniqueItem("Test Item4", 5);
        //Add all items in
        inventory.addItem(item);
        inventory.addItem(item2);
        inventory.addItem(item3);
        //remove by item
        assertTrue("Item not removed", inventory.removeItem(item));
        //remove by index
        assertTrue("Item not removed", inventory.removeItem(1) == item2);
        //remove using item and stack amount to remove
        assertTrue("Item not removed", inventory.removeItem(item3, 1));
        assertTrue("Inventory wasnt empty", inventory.getNumItems() == 0);


    }










}
