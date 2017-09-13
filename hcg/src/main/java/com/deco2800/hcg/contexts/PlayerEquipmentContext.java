package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.contexts.UIContext;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.ConsumableItem;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.TimeManager;
import org.lwjgl.Sys;


/**
 * class for displaying the player inventory context
 * Currently an exact replica of the shopkeeper context, TODO: Change this
 */
public class PlayerEquipmentContext extends UIContext{

    private Image title;
    private Image shopFunds;
    private Image shopInventory;
    private ImageButton shopBuy;
    private ImageButton shopSell;
    private ImageButton shopExit;
    private Table centreTable;
    /**
     * Constructor for the Player Inventory Context
     */
    public PlayerEquipmentContext(Player player) {

        //Note that the player inventory context must take a reference to the exact player the context is being shown for
        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);
        TimeManager timeManager = (TimeManager)
                gameManager.getManager(TimeManager.class);
        timeManager.pauseTime();

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        //Generate Views needed
        //Centre table is the main table holding all elements
        centreTable = new Table();
        centreTable.setFillParent(true);
        centreTable.setBackground(new Image(textureManager.getTexture("wooden_background")).getDrawable());

        //Generate the inner table, this holds the actual item inventory
        Table innerTable = new Table();
        innerTable.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());
        shopExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());

        //Generate the grid to display the item that is clicked
        Table itemDisplay = new Table();
        itemDisplay.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        //Generate the view to display item information
        Table itemInfo = new Table();
        itemInfo.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());
        Label itemInfoTitle = new Label("Item Info", skin);
        itemInfoTitle.setColor(Color.BLACK);
        itemInfo.add(itemInfoTitle);
        itemInfoTitle.setFontScale(1.3f);

        //Generate the view to display the player stats
        Table playerInfo = new Table();
        playerInfo.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        //Populate views as needed. This also generates images and adds on click methods
        populateInventory(itemDisplay,itemInfo, textureManager, player, skin, innerTable);
        populatePlayerInfo(playerInfo, skin, player);

        //Add all these elements to the main table view (centreTable)
        centreTable.row();
        centreTable.add(itemDisplay).center();
        centreTable.add(playerInfo).center();
        centreTable.row();
        centreTable.add(innerTable).center();
        centreTable.add(itemInfo).center();
        centreTable.row();
        centreTable.add(shopExit).center().bottom();

        //add table to stage
        stage.addActor(centreTable);

        //Listeners
        shopExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //unpause in-game time
                timeManager.unpauseTime();
                contextManager.popContext();
            }
        });

    }

    private void populateInventory(Table itemDisplay, Table itemInfo, TextureManager textureManager, Player player,
                                   Skin skin, Table innerTable) {
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

    private void populatePlayerInfo(Table playerInfo, Skin skin, Player player) {
        Label title = new Label("Player Stats", skin);
        title.setColor(Color.BLACK);
        Label text1 = new Label(("Health  " + player.getAttribute("health")), skin);
        text1.setColor(Color.BLACK);
        Label text2 = new Label(("Level " + player.getAttribute("xp")), skin);
        text2.setColor(Color.BLACK);
        Label text3 = new Label(("Stamina " + player.getAttribute("stamina")), skin);
        text3.setColor(Color.BLACK);
        playerInfo.add(title).top();
        playerInfo.row();
        playerInfo.add(text1);
        playerInfo.row();
        playerInfo.add(text2);
        playerInfo.row();
        playerInfo.add(text3);
    }
}
