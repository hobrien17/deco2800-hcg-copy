package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;
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
    private SoundManager soundManager;

    private Skin skin;
    private Image shopTitle;
    private Image playerTitle;
    private Table shopInventory;
    private Table playerInventory;
    private Table moneyCounter;
    private Image buyBag;
    private Image sellBag;
    private ImageButton shopBuy;
    private ImageButton shopSell;
    private ImageButton shopExit;
    private Table centreTable;
    private Table buySell;
    private Image errorImage;
    private boolean errorImageVisible;

    private TextField amount;
    private String amountString;

    private Player player;
    private ShopNPC shopKeeper;


    /**
     * Constructor for the ShopMenuContext
     */
    public ShopMenuContext(Player player, ShopNPC shopKeeper) {
        shopKeeper.getShop().open(0, player);
        this.player = player;
        this.shopKeeper = shopKeeper;
        // Get necessary managers
        gameManager = GameManager.get();
        contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);
        timeManager = (TimeManager)
                gameManager.getManager(TimeManager.class);
        soundManager = (SoundManager)
        		gameManager.getManager(SoundManager.class);
        amountString = "1";
        amount = new TextField(amountString,
                new Skin(Gdx.files.internal("resources/ui/uiskin.json")));
        errorImageVisible = false;
        draw();
    }

    public void draw() {

        //stop in-game time while in shop
        timeManager.pauseTime();

        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        centreTable = new Table();
        centreTable.setFillParent(true);
        centreTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());

        shopTitle = new Image(textureManager.getTexture("shop_title"));
        playerTitle = new Image(textureManager.getTexture("player_title"));

        shopInventory = new Table();
        shopInventory.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        playerInventory = new Table();
        playerInventory.setBackground(new Image(textureManager.getTexture("shop_inventory")).getDrawable());

        //adding the textfield
        amountString = amount.getText();
        amount = new TextField(amountString, skin);
        amount.setWidth(160);

        buySell = new Table();

        buyBag = new Image(new Image(textureManager.getTexture("buy_bag")).getDrawable());
        sellBag = new Image(new Image(textureManager.getTexture("sell_bag")).getDrawable());
        shopBuy = new ImageButton(new Image(textureManager.getTexture("shop_buy_button")).getDrawable());
        shopSell = new ImageButton(new Image(textureManager.getTexture("shop_sell_button")).getDrawable());
        shopExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());

        buySell.add(sellBag).height(80).width(80);
        buySell.row();
        buySell.add(shopBuy).height(80).width(160);
        buySell.row();
        buySell.add(amount).height(40).width(40).pad(15);
        buySell.row();
        buySell.add(shopSell).height(80).width(160);
        buySell.row();
        buySell.add(buyBag).height(100).width(80);
        buySell.row();
        shopExit.setPosition(0, stage.getHeight()-shopExit.getHeight());

        inventoryDisplay(textureManager, player, skin, playerInventory, this);
        inventoryDisplay(textureManager, shopKeeper, skin, shopInventory, this);

        //add elements to table
        centreTable.add(shopTitle);
        centreTable.add();
        centreTable.add(playerTitle);
        centreTable.row();
        centreTable.add(shopInventory);
        centreTable.add(buySell);
        centreTable.add(playerInventory);
        centreTable.row();

        errorImage = new Image(textureManager.getTexture("error_shop"));
        errorImage.setPosition(stage.getWidth()/2 - (errorImage.getWidth()/2) ,stage.getHeight()-errorImage.getHeight());
        errorImage.toFront();
        errorImage.setVisible(errorImageVisible);

        //add table to stage
        stage.addActor(centreTable);
        stage.addActor(shopExit);
        stage.addActor(errorImage);

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
				errorImageVisible = false;
				if ((selectedItem != null) && !selectedItem
						.sameItem(new Seed(Seed.Type.SUNFLOWER))) {
					int number = 0;
					try {
					    number = Integer.parseInt(amount.getText().trim());
					} catch(NumberFormatException e) {
					    amount.setText("NaN");
					}
					for (int i = 0; i < number; i++) {
						if (shopKeeper.getShop().buyStock(selectedItem) != 0) {
							errorImageVisible = true;
						}
					}
					soundManager.playSound("buy");
				}
				draw();
			}
		});

		shopSell.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				errorImageVisible = false;
				int number = 0;
                try {
                    number = Integer.parseInt(amount.getText().trim());
                } catch(NumberFormatException e) {
                    amount.setText("NaN");
                }
				if ((selectedItem != null)
						&& !selectedItem.sameItem(new Seed(Seed.Type.SUNFLOWER))
						&& !(shopKeeper.getShop().getStock()
								.contains(selectedItem))) {
					for (int i = 0; i < number; i++) {
						if (shopKeeper.getShop().sellStock(selectedItem) != 0) {
							errorImageVisible = true;
						}
					}
					soundManager.playSound("sell");
				}
				draw();
			}
		});
	}
}
