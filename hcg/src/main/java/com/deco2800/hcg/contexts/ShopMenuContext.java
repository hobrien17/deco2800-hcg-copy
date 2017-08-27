package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.TimeManager;

/**
 * Shop Menu
 *
 * @author(s) willc138 georgesburt97
 */
public class ShopMenuContext extends UIContext {

    private Image title;
    private Image shopFunds;
    private Image shopInventory;
    private ImageButton shopBuyButton;
    private ImageButton shopSellButton;
    private ImageButton shopExit;
    private Table centreTable;

    public ShopMenuContext() {

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
        shopBuyButton = new ImageButton(new Image(textureManager.getTexture("shop_buy")).getDrawable());
        shopSellButton = new ImageButton(new Image(textureManager.getTexture("shop_sell")).getDrawable());
        shopExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());

        //add elements to table
        centreTable.add(title);
        centreTable.add(shopFunds);
        centreTable.row();
        centreTable.add(shopInventory);
        centreTable.row();
        centreTable.add(shopBuyButton);
        centreTable.add(shopSellButton);
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
