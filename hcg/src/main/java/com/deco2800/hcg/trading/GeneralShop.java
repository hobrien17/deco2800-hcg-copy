package com.deco2800.hcg.trading;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.BasicSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.HealthPotion;

import java.util.HashMap;
import java.util.Map;

/**
 * General Shop class that implements all the methods of the shop
 *
 * @author Taari Meiners / Team 1
 */
public class GeneralShop extends Shop{
    public GeneralShop() {
        Item testPotion = new HealthPotion(100);
        addStock(testPotion, 3);
    }

}
