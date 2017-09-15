package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.TimeManager;

/**
 * Shop Menu
 * Extention of InventoryDisplayContext that initialises the shop menu when the context is pushed.
 * @author willc138
 * @author georgesburt97
 * @author tmein
 */
public class ShopMenuContext extends InventoryDisplayContext {

    private Image title;
    private Image shopFunds;
    private Table shopInventory;
    private Table playerInventory;
    private ImageButton shopBuy;
    private ImageButton shopSell;
    private ImageButton shopExit;
    private Table centreTable;

    /**
     * Constructor for the ShopMenuContext
     */
    public ShopMenuContext(Player player, ShopNPC shopKeeper) {

        shopKeeper.getShop().open(0, player);

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

        shopInventory = new Table();
        shopInventory.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        playerInventory = new Table();
        playerInventory.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        shopBuy = new ImageButton(new Image(textureManager.getTexture("shop_buy_button")).getDrawable());
        shopSell = new ImageButton(new Image(textureManager.getTexture("shop_sell_button")).getDrawable());
        shopExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());

        inventoryDisplay(textureManager, player, skin, playerInventory);
        inventoryDisplay(textureManager, shopKeeper, skin, shopInventory);

        //add elements to table
        centreTable.add(title);
        centreTable.add(shopFunds);
        centreTable.row();
        centreTable.add(shopInventory);
        centreTable.add(playerInventory);
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

        shopBuy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerInventory.reset();
                shopInventory.reset();
                shopKeeper.getShop().buyStock(selectedItem);
                inventoryDisplay(textureManager, player, skin, playerInventory);
                inventoryDisplay(textureManager, shopKeeper, skin, shopInventory);
            }
        });
    }

}
