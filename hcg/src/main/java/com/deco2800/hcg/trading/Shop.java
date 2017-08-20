package com.deco2800.hcg.trading;


import java.util.Map;

/** Generic shop interfece that has basic shop commands necessary for any shop such as the ability to buy, sell
 * and hold stock.
 */
public interface Shop {

    /**Open the shop so it can be interacted with.
     *
     * @param charisma
     *          charisma attribute of the player, used to apply discounts
     *
     * @return nothing*/
    public void open(int charisma);

    /**Retrieve the number of a certain item this shop currently has in stock
     *
     * @param stock
     *          the item to be checked
     * @return number in stock, 0 if none
     */
    public int inStock(Stock stock);

    /**Add a new stock item to the shop with a corresponding number of this item the shop should have in stock
     *
     * @param stock
     *          An instance of the stock class, e.g. a weapon or food item
     * @param available
     *          The number of this item that should be made available in the shop
     */
    public void addStock(Stock stock, int available);

    /**Add many new stock items to the shop, useful during initialisation of the shop. Care needs to be taken that the
     * indexes of both arrays match up, position 0 in the stock array will be assigned the availability number of
     * position 0 of the availability array.
     *
     * @param stock
     *          Array of stock items to be added
     * @param available
     *          Array of numbers corresponding to the number of stock that should be made available
     */
    public void addStock(Stock[] stock, int[] available);

    /**Method to get access to the items a shop currently has in stock for displaying to the user what options they
     * have to buy
     *
     * @return the items the shop currently has in stock and the number of that item present
     */
    public Map<Stock,Integer> getStock();


}
