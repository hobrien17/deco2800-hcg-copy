package com.deco2800.hcg.contexts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.ConsumableItem;
import com.deco2800.hcg.managers.TextureManager;
import org.lwjgl.Sys;

public abstract class InventoryDisplayContext extends UIContext{

    //Input arguments
    private Table itemDisplay;
    private Table itemInfo;
    private TextureManager textureManager;
    private Player player;
    private Skin skin;
    private Table playerInventory;

    private int maxRow = 4;
    private int currentRow;
    int i;

    public void inventoryDisplay(Table itemDisplay, Table itemInfo, TextureManager textureManager, Player player,
                     Skin skin, Table playerInventory) {
        this.itemDisplay = itemDisplay;
        this.itemInfo = itemInfo;
        this.textureManager = textureManager;
        this.player = player;
        this.skin = skin;
        this.playerInventory = playerInventory;
        currentRow = 0;

        for (i=0; i<player.getInventory().getNumItems(); i++) {
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
                    itemDisplay.add(image).height(50).width(50).center();
                    itemDisplay.add(itemName).center();
                    itemDisplay.row();
                    //Populate item info when clicked
                    itemInfo.clear();
                    itemInfo.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());
                    Label title = new Label("Item Info", skin);
                    title.setColor(Color.BLACK);
                    title.setFontScale(1.5f);
                    itemName.setColor(Color.BLACK);
                    //itemDisplay.add(itemName).center();
                    //itemDisplay.row();
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
                                    playerInventory.clear();
                                    inventoryDisplay(itemDisplay, itemInfo, textureManager, player, skin, playerInventory);
                                } else if (currentItem.isEquippable()) {
                                    //TODO: Equip the item
                                } else if (currentItem.isWearable()) {
                                    //TODO: Wear item
                                }

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

    public void inventoryDisplay(TextureManager textureManager, Player player, Skin skin, Table playerInventory) {
        this.textureManager = textureManager;
        this.player = player;
        this.skin = skin;
        this.playerInventory = playerInventory;
        currentRow = 0;

        for (i=0; i<player.getInventory().getNumItems(); i++) {
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
                    if (clickedImage.isVisible()) {
                        clickedImage.setVisible(false);
                    } else {
                        clickedImage.setVisible(true);
                    }
                }
            });
            currentRow++;
        }

    }

    public void equipmentDisplay(TextureManager textureManager, Player player, Skin skin, Table playerEquipment) {
        this.textureManager = textureManager;
        this.player = player;
        this.skin = skin;
        this.playerInventory = playerEquipment;
        currentRow = 0;

        for (i=0; i<player.getEquippedItems().getNumItems(); i++) {
            Item currentItem = player.getEquippedItems().getItem(i);
            System.out.println(textureManager.getTexture(currentItem.getTexture()));
            //TODO: We need sprites for all items, weapons currently dont have sprites hence this falls with a nullpointer.
            //ImageButton button = new ImageButton(new Image(textureManager.getTexture(currentItem.getTexture()))
                    //.getDrawable());
            ImageButton button = new ImageButton(new Image(textureManager.getTexture("spacman")).getDrawable());
            Stack stack = new Stack();
            Image clickedImage = new Image(textureManager.getTexture("selected"));
            Label itemLabel = null;
            commonSetup(currentItem, button, stack, itemLabel, clickedImage);
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (clickedImage.isVisible()) {
                        clickedImage.setVisible(false);
                    } else {
                        clickedImage.setVisible(true);
                    }
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
        System.out.println(i);
        button.setName(player.getInventory().getItem(i).getName());
        //System.out.println("setName failed");
        if (currentItem.isStackable()) {
            itemLabel = new Label(""+ currentItem.getStackSize(), skin);
        } else {
            //This keeps button size consistent
            itemLabel = new Label("-", skin);
        }
        itemLabel.setColor(Color.BLACK);
        button.add(itemLabel);
        stack.add(button);
        clickedImage.setVisible(false);
        stack.add(clickedImage);
        playerInventory.add(stack).width(50).height(50).pad(15);
    }
}
