package com.deco2800.hcg.items;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class SingleItemTest {
    int testBaseValue = 100;
    int testItemWeight = 10;
    String testItemName = "UniqueItem";
    String testItemtexture = "sword.png";

    //Test basic intilization + get and sets
    @Test
    public void TestIntilization() {

        /** Test Initialisation values **/
        TestUniqueItem testItem = new TestUniqueItem("UniqueItem", 10);
        testItem.setUniqueData("Some Awesome Sword");
        //assertTrue(testItem.getName() == (testItemName+"("+testItem.getUniqueData()+")"));
        assertTrue(testItem.getBaseValue() == testBaseValue);
        assertTrue(testItem.getMaxStackSize() == 1);
        assertTrue(testItem.getWeight() == testItemWeight);
        assertTrue(testItem.getStackSize() == 1);
        assertFalse(testItem.isStackable());
        assertFalse(testItem.isWearable());
        assertTrue(testItem.isTradable());
        assertTrue(testItem.getWeight() == 10); //Current stack size (1) * itemWeight (5)
    }

    //Test same item method
    @Test
    public void TestSameItem() {

        /** Test Initialisation values **/
        TestUniqueItem testItem = new TestUniqueItem("UniqueItem", 10);
        testItem.setUniqueData("Some Awesome Sword");
        TestUniqueItem testItem2 = new TestUniqueItem("UniqueItem", 10);
        testItem2.setUniqueData("Some Awesome Sword");
        TestUniqueItem testItem3 = new TestUniqueItem("UniqueItem", 10);
        testItem3.setUniqueData("Another Awesome Sword");
        TestUniqueItem testItem4 = new TestUniqueItem("UniqueItem2", 10);
        testItem4.setUniqueData("Another Awesome Sword");
        TestUniqueItem testItem5 = new TestUniqueItem("UniqueItem2", 10);
        //Exact same item
        assertTrue(testItem.sameItem(testItem2));
        //Different unique data fields
        assertFalse(testItem2.sameItem(testItem3));
        //Same unique data fields, different name ->This should be false
        assertFalse(testItem3.sameItem(testItem4));
        //No unique item field
        assertFalse(testItem5.sameItem(testItem4));


    }



}

