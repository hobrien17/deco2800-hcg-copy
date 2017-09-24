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


public class PlayerEquipmentContext extends InventoryDisplayContext {
    //Context to display the players currently equipped items alongside the inventory
    private Table centreTable;
    private Button playerInventoryTab;
    private Button playerEquipmentTab;
    private ImageButton shopExit;

    public PlayerEquipmentContext(Player player){
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
        Label title = new Label("Equipment", skin);
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
        Table playerEquipment = new Table();
        playerEquipment.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());
        //Populate views as needed. This also generates images and adds on click methods
        inventoryDisplay(itemDisplay, itemInfo, textureManager, player, skin, innerTable);
        equipmentDisplay(textureManager,player,skin,playerEquipment);
        //Add all these elements to the main table view (centreTable)
        centreTable.row();
        centreTable.add(playerEquipment).expandY().fillY();
        centreTable.add(innerTable).expandY().fillY();
        centreTable.add(itemDisplay).expandY().fillY();
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


}
