package com.deco2800.hcg.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.contexts.UIContext;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.TimeManager;


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
    public PlayerEquipmentContext() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);
        TimeManager timeManager = (TimeManager)
                gameManager.getManager(TimeManager.class);

        //stop in-game time while in shop
        timeManager.pauseTime();

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        centreTable = new Table();
        centreTable.setFillParent(true);
        centreTable.setBackground(new Image(textureManager.getTexture("wooden_background")).getDrawable());

        title = new Image(textureManager.getTexture("shop_title"));
        shopFunds = new Image(textureManager.getTexture("shop_funds"));
        shopInventory = new Image(textureManager.getTexture("shop_inventory"));
        shopBuy = new ImageButton(new Image(textureManager.getTexture("shop_buy_button")).getDrawable());
        shopSell = new ImageButton(new Image(textureManager.getTexture("shop_sell_button")).getDrawable());
        shopExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());

        //add elements to table
        centreTable.add(title);
        centreTable.add(shopFunds);
        centreTable.row();
        centreTable.add(shopInventory);
        centreTable.row();
        centreTable.add(shopBuy);
        centreTable.add(shopSell);
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
