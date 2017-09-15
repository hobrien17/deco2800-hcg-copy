package com.deco2800.hcg.contexts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.ConsumableItem;
import com.deco2800.hcg.managers.TextureManager;

import java.util.Iterator;

public abstract class InventoryDisplayContext extends UIContext{

    //Input arguments
    private Table itemDisplay;
    private Table itemInfo;
    private TextureManager textureManager;
    private Skin skin;
    private Table playerInventory;

    private int maxRow = 4;
    private int currentRow;

    protected Item selectedItem;
    protected Image selectedImage;

    public void inventoryDisplay(Table itemDisplay, Table itemInfo, TextureManager textureManager, Player player,
                     Skin skin, Table playerInventory) {
        this.itemDisplay = itemDisplay;
        this.itemInfo = itemInfo;
        this.textureManager = textureManager;
        this.skin = skin;
        this.playerInventory = playerInventory;
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

    public void inventoryDisplay(TextureManager textureManager, Character character, Skin skin, Table playerInventory) {
        this.textureManager = textureManager;
        this.skin = skin;
        this.playerInventory = playerInventory;
        currentRow = 0;
        Iterator inventory = null;

        if (character instanceof Player) {
            Player player = (Player) character;
            inventory = player.getInventory().iterator();
        } else if (character instanceof ShopNPC) {
            inventory = ((ShopNPC) character).getShop().getStock().iterator();
        }

        while (inventory.hasNext()) {
            Item currentItem = (Item) inventory.next();
            ImageButton button = new ImageButton(new Image(textureManager.getTexture(currentItem.getTexture()))
                    .getDrawable());
            Stack stack = new Stack();
            Image clickedImage = new Image(textureManager.getTexture("selected"));
            Label itemLabel = null;
            commonSetup(currentItem, button, stack, itemLabel, clickedImage);
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedImage != null) {
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

    private void commonSetup(Item currentItem, ImageButton button, Stack stack, Label itemLabel, Image clickedImage) {
        //Get the item to be displayed as a button
        System.out.println(currentItem);
        if (currentRow >= maxRow) {
            playerInventory.row();
            currentRow = 0;
        }
        button.setName(currentItem.getName());
        if (currentItem.isStackable()) {
            itemLabel = new Label(""+ currentItem.getStackSize(), skin);
        } else {
            //This keeps button size consistent
            itemLabel = new Label("-", skin);
        }
        itemLabel.setColor(Color.BLACK);
        stack.add(clickedImage);
        stack.add(button);
        Table newTable = new Table();
        newTable.add(stack).height(50).width(50);
        newTable.row();
        newTable.add(itemLabel);
        clickedImage.setVisible(false);
        playerInventory.add(newTable).width(50).height(60).pad(15);
    }
}
