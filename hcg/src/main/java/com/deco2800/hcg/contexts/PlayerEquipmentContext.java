package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.contexts.UIContext;
import com.deco2800.hcg.entities.Player;
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

        //Generate the main table view used
        centreTable = new Table();
        centreTable.setFillParent(true);
        centreTable.setBackground(new Image(textureManager.getTexture("wooden_background")).getDrawable());
        //Generate the inner table
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
        //Generate the view to display the player stats
        Table playerInfo = new Table();
        playerInfo.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        //Populate inventory view
        int maxRow = 4;
        int currentRow = 0;
        //for (int i=0;i<player.getInventory().getNumItems();i++) {
        for (int i=0;i<15;i++) {
            //Get the item to be displayed as a button
            //TODO: add this function player.getInventory().getItem(i).getTexture();
            if (currentRow >= maxRow) {
                innerTable.row();
                currentRow = 0;
            }
            String string = "spacman";
            if (i%2 == 0) {
                string = "squirrel";
            }
            ImageButton button = new ImageButton(new Image(textureManager.getTexture(string)).getDrawable());
            innerTable.add(button).width(50).height(50).pad(15);
            button.addListener(new ClickListener() {
                                   @Override
                                   public void clicked(InputEvent event, float x, float y) {
                                       itemDisplay.clear();
                                       //Show item when clicke
                                       Image image = new Image(button.getImage().getDrawable());
                                       itemDisplay.add(image).height(100).width(100);
                                       //Populate item info when clicked
                                       itemInfo.clear();
                                       itemInfo.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());
                                       Label title = new Label("Item Info", skin);
                                       title.setColor(Color.BLACK);
                                       Label itemName = new Label((" " + button.getImage().getName()), skin);
                                       itemName.setColor(Color.BLACK);
                                       itemInfo.add(title);
                                       itemInfo.row();
                                       itemInfo.add(itemName);
                                   }
                               });
                    currentRow++;
        }

        //Populate player info view
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

        //populate the item infomation section

        //add elements to the inner table i.e the inventory


        //Add elements to the main table
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
}
