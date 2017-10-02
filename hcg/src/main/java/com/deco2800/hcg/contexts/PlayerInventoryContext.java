package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.TimeManager;


/**
 * class for displaying the player inventory context
 * Currently an exact replica of the shopkeeper context, TODO: Change this
 */
public class PlayerInventoryContext extends InventoryDisplayContext{

    private ImageButton shopExit;
    private Table centreTable;
    private Button playerInventoryTab;
    private Button playerEquipmentTab;
    /**
     * Constructor for the Player Inventory Context
     */
    public PlayerInventoryContext(Player player) {

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
        centreTable = new Table();
        //Generate Views needed

        //Create the tabs to be placed at the top of the inventory
        playerInventoryTab = new Button(skin);
        playerEquipmentTab = new Button(skin);
        playerInventoryTab.add("Inventory");
        playerEquipmentTab.add("Equipment");
        playerInventoryTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                contextManager.popContext();
                contextManager.pushContext(new PlayerInventoryContext(player));

            }
        });
        playerEquipmentTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                contextManager.popContext();
                contextManager.pushContext(new PlayerEquipmentContext(player));

            }
        });
        //Add title
        Label title = new Label("Inventory", skin);
        title.setColor(Color.WHITE);
        centreTable.add(title).center().colspan(2);
        centreTable.add(playerInventoryTab).left();
        centreTable.add(playerEquipmentTab).left();
        //Centre table is the main table holding all elements
        centreTable.setFillParent(true);
        centreTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());

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
        innerTable.center();
        //Populate views as needed. This also generates images and adds on click methods
        inventoryDisplay(itemDisplay, itemInfo, textureManager, player, skin, innerTable);
        populatePlayerInfo(playerInfo, skin, player);
        //Add all these elements to the main table view (centreTable)
        centreTable.row();
        centreTable.add(itemDisplay);
        centreTable.add(itemInfo);
        centreTable.row();
        centreTable.add(innerTable);
        centreTable.add(playerInfo);
        centreTable.row();
        centreTable.add(shopExit);

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
        Label text1 = new Label("Health  " + player.getHealthCur(), skin);
        text1.setColor(Color.BLACK);
        Label text2 = new Label("Level " + player.getXp(), skin);
        text2.setColor(Color.BLACK);
        Label text3 = new Label("Stamina " + player.getStaminaCur(), skin);
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
