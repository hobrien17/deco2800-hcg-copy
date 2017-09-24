package com.deco2800.hcg.contexts;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.input.Mouse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.ConsumableItem;
import com.deco2800.hcg.managers.TextureManager;

/**
 * Class for displaying items in a player or shopkeeper's inventory. This class holds the methods that are common in the
 * ShopMenuContext and PlayerEquipmentContext to cut down on duplicated code.
 *
 * @author Taari Meiners (@tmein) / Group 1
 * @author Group 2
 */
public abstract class InventoryDisplayContext extends UIContext {

    //Input arguments
    private Skin skin;
    private Table inventory;

    private int maxRow = 4;
    private int currentRow;
    private TextureManager textureManager;

    protected Item selectedItem;
    protected Image selectedImage;
    
    protected boolean hoveringOverItem;
    protected Item mouseOverItem;

    @Override
    public void render(float delta) {
        super.render(delta);
        
        if(hoveringOverItem) {
            drawTooltip();
        }
    }
    
    /**
     * Draws a tooltip for a the item currently being hovered over at the location of the mouse pointer
     */
    private void drawTooltip() {
        SpriteBatch batch = new SpriteBatch();
        Texture tooltip = textureManager.getTexture("tooltip");
        BitmapFont font = new BitmapFont();
        int originX = Mouse.getX();
        int originY = Mouse.getY();
        
        ArrayList<String> text = new ArrayList<>();
        text.add(mouseOverItem.getName());
        ArrayList<String> information = mouseOverItem.getInformation();
        if(information != null) {
            text.addAll(information);
        }

        GlyphLayout layout = new GlyphLayout();
        float width = 0;
        float lineHeight = font.getCapHeight();
        float padding = 5;
        int lines = text.size();
        float height = lineHeight * lines + padding * (lines - 1);
        for(int i = 0; i < lines; i++) {
            layout.setText(font, text.get(i));
            if(layout.width >= width) {
                width = layout.width;
            }
        }
        
        // We need to account for the position of the arrow
        int offsetX = -38;
        int offsetY = 16 - 3;
        
        batch.begin();
        
        // We're using a single texture for the tooltip but cutting it into pieces to allow the tooltip to resize
        
        // bottom left
        batch.draw(tooltip, originX + offsetX, originY + offsetY, 16, 16, 0, 112, 16, 16, false, false);
        // bottom
        batch.draw(tooltip, originX + offsetX + 16, originY + offsetY, width, 16, 16, 112, 2, 16, false, false);
        // top
        batch.draw(tooltip, originX + offsetX + 16, originY + offsetY + height + 16, width, 16, 16, 0, 2, 16, false, false);
        // left
        batch.draw(tooltip, originX + offsetX, originY + offsetY + 16, 16, height, 0, 16, 16, 2, false, false);
        // top left
        batch.draw(tooltip, originX + offsetX, originY + offsetY + 16 + height, 16, 16, 0, 0, 16, 16, false, false);
        // right
        batch.draw(tooltip, originX + offsetX + width + 16, originY + offsetY + 16, 16, height, 288, 16, 16, 2, false, false);
        // top right
        batch.draw(tooltip, originX + offsetX + width + 16, originY + offsetY + 16 + height, 16, 16, 288, 0, 16, 16, false, false);
        // bottom right
        batch.draw(tooltip, originX + offsetX + 16 + width, originY + offsetY, 16, 16, 288, 112, 16, 16, false, false);
        // middle
        batch.draw(tooltip, originX + offsetX + 16, originY + offsetY + 16, width, height, 16, 16, 16, 16, false, false);
        // arrow
        batch.draw(tooltip, originX, originY, 16, 16, 38, 152, 16, 16, false, false);
        
        for(int i = 0; i < lines; i++) {
            float down = lineHeight * i + padding * (i);
            // Only the item name should have a shadow
            if(i == 0) {
                font.setColor(Color.BLACK);
                font.draw(batch, text.get(i), originX + offsetX + 16 + 1, originY + offsetY + height + 16 - 1 - down);
            }
            
            // Only the item name should ever be any colour other than black
            font.setColor(i == 0 ? mouseOverItem.getRarity().colour : Color.BLACK);
            font.draw(batch, text.get(i), originX + offsetX + 16, originY + offsetY + height + 16 - down);
        }
        
        batch.end();
        batch.dispose();
    }

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
        this.textureManager = textureManager;
        currentRow = 0;
        for (int i=0; i<player.getInventory().getNumItems(); i++) {
            Item currentItem = player.getInventory().getItem(i);
            ImageButton button;
            if (textureManager.getTexture(currentItem.getTexture()) == null) {
                button = new ImageButton(new Image(textureManager.getTexture("error")).getDrawable());
            } else {
                button = new ImageButton(new Image(textureManager.getTexture(currentItem.getTexture())).getDrawable());
            }
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
                    itemName.setColor(currentItem.getRarity().colour);
                    itemName.setFontScale(1.2f);
                    itemInfo.add(title).top();
                    itemInfo.row();
                    itemInfo.add(itemName).left();
                    ArrayList<String> itemData = currentItem.getInformation();
                    if(itemData != null) {
                        for(int i = 0; i < itemData.size(); i++) {
                            Label line = new Label(itemData.get(i), skin);
                            line.setColor(Color.BLACK);
                            itemInfo.row();
                            itemInfo.add(line).left();
                        }
                    }
                    //If the item is consumable or stackable or equipablle, show use button
                    if (currentItem instanceof ConsumableItem || currentItem.isEquippable() || currentItem.isWearable()) {
                        //Add the button to use the consumable
                        Button useButton = new Button(skin);
                        useButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                System.out.println("Clicked USE");
                                if (currentItem instanceof ConsumableItem) {
                                    //Consume Item
                                    ((ConsumableItem) currentItem).consume(player);
                                    player.getInventory().removeItem(currentItem, 1);
                                    inventory.clear();
                                    inventoryDisplay(itemDisplay, itemInfo, textureManager, player, skin, inventory);
                                } else if (currentItem.isEquippable()) {
                                    //Equip the item
                                    player.getEquippedItems().addItem(currentItem);
                                    player.getInventory().removeItem(currentItem);
                                    inventory.clear();
                                    inventoryDisplay(itemDisplay, itemInfo, textureManager, player, skin, inventory);
                                }

                            }
                        });
                        useButton.add("USE");
                        itemDisplay.add(useButton).pad(15);
                    }
                }
            
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    hoveringOverItem = true;
                    mouseOverItem = currentItem;
                }
                
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    hoveringOverItem = false;
                    mouseOverItem = null;
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
    public void inventoryDisplay(TextureManager textureManager, Character character, Skin skin, Table inventory, ShopMenuContext shopMenuContext) {
        this.skin = skin;
        this.inventory = inventory;
        this.textureManager = textureManager;
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
                    selectedImage = clickedImage;
                    selectedItem = currentItem;
                    shopMenuContext.draw();
                }
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    hoveringOverItem = true;
                    mouseOverItem = currentItem;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    hoveringOverItem = false;
                    mouseOverItem = null;
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

        Table buttonTable = new Table();
        buttonTable.add(button).width(50).height(50);

        Table clickedImageTable = new Table();
        clickedImageTable.add(clickedImage).width(50).height(50);

        //Stack containing the item image, background and the on click overlay
        stack.add(new Image(textureManager.getTexture("item_background")));
        stack.add(buttonTable);
        stack.add(clickedImageTable);

        //Wrapping table for the label and image
        Table newTable = new Table();
        newTable.add(stack).height(60).width(60);
        newTable.row();
        newTable.add(itemLabel);

        if (!currentItem.equals(selectedItem)) {
            clickedImage.setVisible(false);
        }

        inventory.add(newTable).width(60).height(70).pad(5);
    }


    /** Method for updating table view to display the players current equipped items. Used within the equipment tab
     * of the inventory
     * @param textureManager the main texture manager
     * @param player the current player whos equipment will be displayed
     * @param skin default skin
     * @param playerEquipment table to display the equipment
     */
    public void equipmentDisplay(TextureManager textureManager, Player player, Skin skin, Table playerEquipment) {
        this.skin = skin;
        currentRow = 0;
        this.inventory = playerEquipment;
        for (int i=0; i<player.getEquippedItems().getNumItems(); i++) {
            Item currentItem = player.getEquippedItems().getItem(i);
            System.out.println(textureManager.getTexture(currentItem.getTexture()));
            //TODO: We need sprites for all items, weapons currently dont have sprites hence this falls with a nullpointer.
            ImageButton button;
            if (textureManager.getTexture(currentItem.getTexture()) == null) {
                 button = new ImageButton(new Image(textureManager.getTexture("error")).getDrawable());
            } else {
                 button = new ImageButton(new Image(textureManager.getTexture(currentItem.getTexture())).getDrawable());
            }
            Stack stack = new Stack();
            Image clickedImage = new Image(textureManager.getTexture("selected"));
            Label itemLabel = null;
            commonSetup(currentItem, button, stack, itemLabel, clickedImage);
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (clickedImage.isVisible()) {
                        //Remove from equipped items into the inventory
                        player.getEquippedItems().removeItem(currentItem);
                        player.getInventory().addItem(currentItem);
                        //Refresh the inventory
                        playerEquipment.clear();
                        equipmentDisplay(textureManager, player, skin, playerEquipment);
                        clickedImage.setVisible(false);
                    } else {
                        clickedImage.setVisible(true);
                    }
                }
            });
            currentRow++;
        }
    }
}
