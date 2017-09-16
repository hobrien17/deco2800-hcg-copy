package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.TimeManager;

/**
 * class for displaying the player inventory context
 * Currently an exact replica of the shopkeeper context, TODO: Change this
 */
public class PlayerEquipmentContext extends InventoryDisplayContext{

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
        inventoryDisplay(itemDisplay, itemInfo, textureManager, player, skin, innerTable);
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
