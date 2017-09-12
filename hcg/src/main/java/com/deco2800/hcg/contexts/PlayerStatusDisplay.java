package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.*;

public class PlayerStatusDisplay extends Group {

    private Player player;
    private GameManager gameManager;
    private Image healthBar;
    private Image healthBarShadow;
    private Image staminaBar;
    private Image playerBorder;
    private Image playerImage;
    private Label playerHealth;
    private float posY;
    private float posX;

    public PlayerStatusDisplay() {
        super();
        /*adding GameManager and obtaining player class*/
        gameManager = GameManager.get();
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        player = playerManager.getPlayer();

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        /*position of Status Display*/
        posY = 500;
        posX = 30;


        /* Add the player's health and stamina display*/
        playerImage = new Image(new Texture(player.getDisplayImage()));
        playerBorder = new Image(new Texture(Gdx.files.internal("resources/ui/player_status_hud/player_normal_border.png")));
        staminaBar = new Image(new Texture(Gdx.files.internal("resources/ui/player_status_hud/health_bar_pixel.png")));
        healthBar = new Image(new Texture(Gdx.files.internal("resources/ui/player_status_hud/health_bar_pixel.png")));
        healthBarShadow = new Image(new Texture(Gdx.files.internal("resources/ui/player_status_hud/health_bar_pixel.png")));
        playerHealth = new Label(player.getHealthCur() + " / " + player.getHealthMax(), skin);

		/* create player display GUI and add it to the stage */
        this.setPosition(posX, posY);
        this.addActor(playerImage);
        this.addActor(playerBorder);
        this.addActor(playerHealth);
        this.addActor(healthBarShadow);
        this.addActor(healthBar);
        this.addActor(staminaBar);

        /*setting bar dimensions*/
        float healthBarWidth = (float) player.getHealthCur() / player.getHealthMax() * 330;
        float healthBarHeight = 29;
        float staminaBarWidth = (float) player.getStaminaCur() / player.getStaminaMax() * 260;
        float staminaBarHeight = 23;

        healthBar.setSize(healthBarWidth, healthBarHeight);
        healthBarShadow.setSize(healthBarWidth, healthBarHeight);
        staminaBar.setSize(staminaBarWidth, staminaBarHeight);

        /* Setting bar colours, health changes from green to red as health drops
         * 200/157 multiplier so function ranges from 0 to 255; the range of RGB colours.
         */
        healthBar.setColor(255 - (healthBarWidth * 200 / 157), healthBarWidth * 200 / 157, 0, 1);
        healthBarShadow.setColor(Color.BLACK);
        staminaBar.setColor(Color.GOLD);

        /* positioning and scaling images bars and labels*/
        healthBar.setPosition(120, 48);
        healthBarShadow.setPosition(120, 48);
        staminaBar.setPosition(120, 20);
        playerImage.setPosition(25, 8);
        playerHealth.setPosition(125, 85);

        playerHealth.setFontScale(1.2f);
        playerImage.scaleBy(-0.75f);
        playerBorder.scaleBy(-0.8f);
    }

    public void updatePlayerStatus() {
        float healthBarWidth = (float) player.getHealthCur() /
                player.getHealthMax() * 330;
        healthBar.setColor(255 - (healthBarWidth * 200 / 157), healthBarWidth * 200 / 157, 0, 1);
        healthBar.setWidth(healthBarWidth);
        playerHealth.setText(player.getHealthCur() + " / " + player.getHealthMax());

        if (healthBarShadow.getImageWidth() > healthBarWidth) {
            healthBarShadow.setWidth(healthBarShadow.getImageWidth() - 4);
        }
        float staminaBarWidth = (float) player.getStaminaCur() /
                player.getStaminaMax() * 260;
        staminaBar.setWidth(staminaBarWidth);
    }

    public void updatePosition(float stageHeight) {
        posY = stageHeight - 200;
        posX = 30;
        this.setPosition(posX, posY);
    }
}
