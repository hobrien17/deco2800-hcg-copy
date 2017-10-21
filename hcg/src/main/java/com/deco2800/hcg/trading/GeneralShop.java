package com.deco2800.hcg.trading;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.HealthPotion;
import com.deco2800.hcg.items.stackable.MagicMushroom;
import com.deco2800.hcg.items.stackable.SmallMushroom;

/**
 * General Shop class that implements all the methods of the shop
 *
 * @author Taari Meiners / Team 1
 */
public class GeneralShop extends Shop{
    public GeneralShop() {
        Item testPotion = new HealthPotion(100);
        testPotion.addToStack(9);
        addStock(testPotion);

        Item magicMushroom = new MagicMushroom();
        magicMushroom.setStackSize(10);
        addStock(magicMushroom);

        Item smallMushroom = new SmallMushroom();
        smallMushroom.setStackSize(10);
        addStock(smallMushroom);
    }

}
