package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
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

    private GameManager gameManager;
    private ContextManager contextManager;
    private TextureManager textureManager;
    private TimeManager timeManager;

    private Skin skin;
    private Image shop_title;
    private Image player_title;
    private Table shopInventory;
    private Table playerInventory;
    private ImageButton shopBuy;
    private ImageButton shopSell;
    private ImageButton shopExit;
    private Table centreTable;
    private Table buySell;

    private Player player;
    private ShopNPC shopKeeper;

    private ShopMenuContext thisContext = this;

    /**
     * Constructor for the ShopMenuContext
     */
    public ShopMenuContext(Player player, ShopNPC shopKeeper) {
        shopKeeper.getShop().open(0, player);
        this.player = player;
        this.shopKeeper = shopKeeper;

        draw();


    }

    public void draw() {
        // Get necessary managers
        gameManager = GameManager.get();
        contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);
        timeManager = (TimeManager)
                gameManager.getManager(TimeManager.class);

        //stop in-game time while in shop
        timeManager.pauseTime();

        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        centreTable = new Table();
        centreTable.setFillParent(true);
        centreTable.setBackground(new Image(textureManager.getTexture("wooden_background")).getDrawable());

        shop_title = new Image(textureManager.getTexture("shop_title"));
        player_title = new Image(textureManager.getTexture("player_title"));

        shopInventory = new Table();
        shopInventory.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        playerInventory = new Table();
        playerInventory.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        shopBuy = new ImageButton(new Image(textureManager.getTexture("shop_buy_button")).getDrawable());

        buySell = new Table();
        shopSell = new ImageButton(new Image(textureManager.getTexture("shop_sell_button")).getDrawable());
        shopExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());
        buySell.add(shopBuy);
        buySell.row();
        buySell.add(shopSell);
        shopExit.setPosition(0, stage.getHeight()-shopExit.getHeight());

        inventoryDisplay(textureManager, player, skin, playerInventory, this);
        inventoryDisplay(textureManager, shopKeeper, skin, shopInventory, this);

        //add elements to table
        centreTable.add(shop_title);
        centreTable.add();
        centreTable.add(player_title);
        centreTable.row();
        centreTable.add(shopInventory);
        centreTable.add(buySell);
        centreTable.add(playerInventory);
        centreTable.row();

        //add table to stage
        stage.addActor(centreTable);
        stage.addActor(shopExit);

        //Listeners
        shopExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                draw();
                //unpause in-game time
                timeManager.unpauseTime();
                contextManager.popContext();
            }
        });

        shopBuy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopKeeper.getShop().buyStock(selectedItem);
                draw();
            }
        });

        shopSell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopKeeper.getShop().sellStock(selectedItem);
                draw();
            }
        });
    }

}
