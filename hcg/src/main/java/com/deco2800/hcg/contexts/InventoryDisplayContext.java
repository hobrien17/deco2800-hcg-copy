package com.deco2800.hcg.contexts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.ConsumableItem;
import com.deco2800.hcg.managers.TextureManager;

import java.util.Iterator;

/**
 * Class for displaying items in a player or shopkeeper's inventory. This class holds the methods that are common in the
 * ShopMenuContext and PlayerEquipmentContext to cut down on duplicated code.
 *
 * @author Taari Meiners (@tmein) / Group 1
 * @author Group 2
 */
public abstract class InventoryDisplayContext extends UIContext{

    //Input arguments
    private Skin skin;
    private Table inventory;

    private int maxRow = 4;
    private int currentRow;

    protected Item selectedItem;
    protected Image selectedImage;

    /**
     * Inventory display method when called by the PlayerEquipmentContext. It has more input arguments because more tables
     * need to be updated in that screen.
     *
     * @param itemDisplay
     *          The item display table
     * @param itemInfo
     *          The item info table
     * @param textureManager
     *          The texture manager
     * @param player
     *          The player
     * @param skin
     *          The UI skin
     * @param inventory
     *          The player inventory table
     */
    public void inventoryDisplay(Table itemDisplay, Table itemInfo, TextureManager textureManager, Player player,
                     Skin skin, Table inventory) {
        this.skin = skin;
        this.inventory = inventory;
        currentRow = 0;

        for (int i=0; i<player.getInventory().getNumItems(); i++) {
            Item currentItem = player.getInventory().getItem(i);
            ImageButton button = new ImageButton(new Image(textureManager.getTexture(currentItem.getTexture()))
                    .getDrawable());
            Stack stack = new Stack();
            Image clickedImage = new Image(textureManager.getTexture("selected"));
            Label itemLabel = null;
            commonSetup(currentItem, button, stack, itemLabel, clickedImage);
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    itemDisplay.clear();
                    //Show item when clicked
                    Label itemName = new Label((button.getName()), skin);
                    Image image = new Image(button.getImage().getDrawable());
                    itemDisplay.add(image).height(50).width(50);
                    itemDisplay.add(itemName).left();
                    itemDisplay.row();
                    //Populate item info when clicked
                    itemInfo.clear();
                    itemInfo.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());
                    Label title = new Label("Item Info", skin);
                    title.setColor(Color.BLACK);
                    title.setFontScale(1.5f);
                    itemName.setColor(Color.BLACK);
                    itemInfo.add(title).top();
                    itemInfo.row();
                    itemInfo.add(itemName).left();
                    //If the item is consumable or stackable or equipablle, show use button
                    if (currentItem instanceof ConsumableItem || currentItem.isEquippable() || currentItem.isWearable()) {
                        //Add the button to use the consumable
                        Button useButton = new Button(skin);
                        useButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                System.out.println("Clicked USE");
                                if (currentItem instanceof ConsumableItem) {
                                    ((ConsumableItem) currentItem).consume(player);
                                    player.getInventory().removeItem(currentItem, 1);
                                    itemLabel.setText(""+ currentItem.getStackSize());
                                } else if (currentItem.isEquippable()) {
                                    //TODO: Equip the item
                                } else if (currentItem.isWearable()) {
                                    //TODO: Wear item
                                }
                                //TODO: This remove gets stuck when one item is left, this is because the redraw doesnt work for 0 case (i.e no item)
                                //We could completely redisplay the inventory, but seems a bit inefficient.

                            }
                        });
                        useButton.add("USE");
                        itemDisplay.add(useButton).pad(15);
                    }
                }
            });
            currentRow++;
        }

    }

    /**
     * The inventory display method when called by the shop
     *
     * @param textureManager
     *          The texture manager
     * @param character
     *          The character - either a player or a shopkeeper depending on the required inventory
     * @param skin
     *          The UI skin
     * @param inventory
     *          The inventory table
     */
    public void inventoryDisplay(TextureManager textureManager, Character character, Skin skin, Table inventory) {
        this.skin = skin;
        this.inventory = inventory;
        currentRow = 0;
        Iterator items = null;

        //Determine the input mode and get an iterator of the items that need to be displayed
        if (character instanceof Player) {
            Player player = (Player) character;
            items = player.getInventory().iterator();
        } else if (character instanceof ShopNPC) {
            items = ((ShopNPC) character).getShop().getStock().iterator();
        }

        //Iterate over all the items that need to be displayed and display them
        while ((items != null) && items.hasNext()) {
            //Setup variables for this iteration
            Item currentItem = (Item) items.next();
            ImageButton button = new ImageButton(new Image(textureManager.getTexture(currentItem.getTexture()))
                    .getDrawable());
            Stack stack = new Stack();
            Image clickedImage = new Image(textureManager.getTexture("selected"));
            Label itemLabel = null;
            commonSetup(currentItem, button, stack, itemLabel, clickedImage);

            //Add listener for this item button
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedItem != null) {
                        selectedImage.setVisible(false);
                    }
                    selectedImage = clickedImage;
                    selectedItem = currentItem;
                    selectedImage.setVisible(true);
                }
            });
            currentRow++;
        }
    }

    /**
     * Method containing all the common setup between the two instances of inventoryDisplay
     *
     * @param currentItem
     *          The item being worked on at the moment
     * @param button
     *          The button for the item
     * @param stack
     *          The stack for the item
     * @param itemLabel
     *          The label for the item
     * @param clickedImage
     *          The clicked image for the item
     */
    private void commonSetup(Item currentItem, ImageButton button, Stack stack, Label itemLabel, Image clickedImage) {
        //Get the item to be displayed as a button
        if (currentRow >= maxRow) {
            inventory.row();
            currentRow = 0;
        }
        button.setName(currentItem.getName());

        //Setup the label
        if (currentItem.isStackable()) {
            itemLabel = new Label(""+ currentItem.getStackSize(), skin);
        } else {
            //This keeps button size consistent
            itemLabel = new Label("-", skin);
        }
        itemLabel.setColor(Color.BLACK);

        //Stack containing the item image and the on click overlay
        stack.add(clickedImage);
        stack.add(button);

        //Wrapping table for the label and image
        Table newTable = new Table();
        newTable.add(stack).height(50).width(50);
        newTable.row();
        newTable.add(itemLabel);

        if (!currentItem.equals(selectedItem)) {
            clickedImage.setVisible(false);
        }

        inventory.add(newTable).width(50).height(60).pad(15);
    }
}
