package com.deco2800.hcg.items;

/** Item interface provids a high level guide for others to implement custom items.
 * The following methods outline the most basic methods an item requires
 * See the basicItem java class for an example implementation of this interface
 **/
public interface Item {
    /**Each item has four required fields: name, isStackable (a boolean value), itemWeight and itemIcon **/

    /** Function for returning the name of an item **/
    String  getName();

    /** Function for returning whether an item is stackable or not **/
    boolean isStackable();

    /** Function for returning the weight of a single instance of this item i.e weight of one potion, one coin, etc **/
    int getWeight();

    /** Function for setting the icon of an item
     * Implemented similar to the AbstractEntitry texture. Be sure to register texture with
     * TextureRegister before assigning the texture to a item*/
    void setTexture(String texture);

}
