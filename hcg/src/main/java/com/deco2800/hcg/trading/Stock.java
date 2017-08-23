package com.deco2800.hcg.trading;

import com.deco2800.hcg.items.Item;

public class Stock {
    Item item;
    int value;

    /**Constructor. This is the only time that the item and value attributes should be modified as the base value of an
     * item should always be consistent during runtime.
     * @param item
     *          The item the stock refers to
     * @param value
     *          The monetary value of the stock
     */
    public Stock(Item item, int value) {
        this.item = item;
        this.value = value;
    }

    /**A method to access the item being stored in this instance of the stock class
     *
     * @return the item being stored
     */
    public Item getItem() {
        return item;
    }

    /**Return the value of the item depending on the player's charisma stat
     *
     * @param charisma
     *          The value of the player's charisma stat
     * @return the value of the item
     */
    public int getValue(int charisma) {
        //actual formula to be worked out
        return value - charisma;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Stock) {
            Stock compareStock = (Stock) object;
            if (item.getName().equals(compareStock.getItem().getName()) && (item.getWeight() == compareStock.getItem().getWeight())) {
                return true;
            }
        }
        return false;
    }
}
