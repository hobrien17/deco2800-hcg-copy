package com.deco2800.hcg.items;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class StackableItemTest {
    int testBaseValue = 2;
    String testItemName = "Test Item";
    int testCurrentStackSize = 1;
    int testMaxStackSize = 64;
    String testTexture = "generic.png";
    int testItemWeight = 5;

    //Test basic intilization + get and sets
    @Test
    public void TestIntilization() {

        /** Test Initialisation values **/
        StackableItem testItem = new TestItem();
        assertTrue(testItem.getName() == testItemName);
        assertTrue(testItem.getBaseValue() == testBaseValue);
        assertTrue(testItem.getMaxStackSize() == testMaxStackSize);
        assertTrue(testItem.getWeight() == testItemWeight);
        assertTrue(testItem.getStackSize() == testCurrentStackSize);
        assertTrue(testItem.isStackable());
        assertFalse(testItem.isWearable());
        assertTrue(testItem.isTradable());
        assertTrue(testItem.getWeight() == 5); //Current stack size (1) * itemWeight (5)
    }


    //Test adding to stack, inclduing attempting to overfill
    @Test
    public void TestAddToStack() {
        /** Test Initialisation values **/
        StackableItem testItem = new TestItem();
        //Max stack size is 64, current stack is 1
        //Add 30 to the stack, size now 31
        testItem.addToStack(30);
        assertTrue(testItem.getStackSize() == 31);
        //Max stack size hasnt changes
        assertTrue(testItem.getMaxStackSize() == 64);
        testItem.addToStack(33);
        //Stack now full
        assertTrue(testItem.getStackSize() == 64);
        //Cant add another item as its full
        assertFalse(testItem.addToStack(1));
        //Adding too much to stack in one go
        StackableItem testItem2 = new TestItem();
        assertFalse(testItem2.addToStack(64));
    }

    //Test set stack size
    @Test
    public void TestSetStackSize() {
        /** Test Initialisation values **/
        StackableItem testItem = new TestItem();
        testItem.setStackSize(30);
        assertTrue(testItem.getStackSize() == 30);

    }

    //Test same item
    @Test
    public void TestSameItem() {
        /** Test Initialisation values **/
        StackableItem testItem = new TestItem();
        StackableItem testItem2 = new TestItem();
        assertTrue(testItem.sameItem(testItem2));
        testItem2.setStackSize(35);
        //Items have different stack size, functionaly the same item (sameItem function) but not equals
        assertTrue(testItem.sameItem(testItem2));
        assertFalse(testItem.equals(testItem2));

    }


}

