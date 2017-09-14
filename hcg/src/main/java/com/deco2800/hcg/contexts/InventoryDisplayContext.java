package com.deco2800.hcg.contexts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.ConsumableItem;
import com.deco2800.hcg.managers.TextureManager;

public abstract class InventoryDisplayContext extends UIContext{
    public void inventoryDisplay(Table itemDisplay, Table itemInfo, TextureManager textureManager, Character character,
                     Skin skin, Table innerTable) {
        Player player;
        if (character instanceof Player) {
            player = (Player) character;
        } else {
            player = null;
        }
        int maxRow = 4;
        int currentRow = 0;
        for (int i=0;i<player.getInventory().getNumItems();i++) {
            //Get the item to be displayed as a button
            Item item = player.getInventory().getItem(i);
            System.out.println(item);
            if (currentRow >= maxRow) {
                innerTable.row();
                currentRow = 0;
            }
            ImageButton button = new ImageButton(new Image(textureManager.getTexture(item.getTexture())).getDrawable());
            button.setName(player.getInventory().getItem(i).getName());
            Label label;
            if (item.isStackable()) {
                label = new Label(""+item.getStackSize(), skin);
            } else {
                //This keeps button size consistent
                label = new Label("-", skin);
            }
            label.setColor(Color.BLACK);
            button.add(label);
            innerTable.add(button).width(50).height(50).pad(15);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    itemDisplay.clear();
                    //Show item when clicke
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
                    if (item instanceof ConsumableItem || item.isEquippable() || item.isWearable()) {
                        //Add the button to use the consumable
                        Button useButton = new Button(skin);
                        useButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                System.out.println("Clicked USE");
                                if (item instanceof ConsumableItem) {
                                    ((ConsumableItem) item).consume(player);
                                    player.getInventory().removeItem(item, 1);
                                    label.setText(""+item.getStackSize());
                                } else if (item.isEquippable()) {
                                    //TODO: Equip the item
                                } else if (item.isWearable()) {
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
}
