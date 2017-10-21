package com.deco2800.hcg.contexts.playcontextclasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Image levelBar;
    private Label playerHealth;
    private Label playerLevel;
    private String staminaBarLoction;
    private TextureManager textureManager;
    
    public PlayerStatusDisplay() {
        super();
        
        /*adding GameManager and obtaining player class*/
        gameManager = GameManager.get();
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        player = playerManager.getPlayer();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        /*position of Status Display*/

        /* Add the player's health and stamina display*/
        playerImage = new Image(new Texture(player.getDisplayImage()));
        playerBorder = new Image(textureManager.getTexture("player_normal_border"));
        staminaBar = new Image(textureManager.getTexture("health_bar_pixel"));
        healthBar = new Image(textureManager.getTexture("health_bar_pixel"));
        healthBarShadow = new Image(textureManager.getTexture("health_bar_pixel"));
        playerHealth = new Label(player.getHealthCur() + " / " + player.getHealthMax(), skin);
        playerLevel = new Label("Lv: " + player.getLevel(), skin);
        levelBar = new Image(textureManager.getTexture("health_bar_pixel"));

		/* create player display GUI and add it to the stage */
        this.addActor(levelBar);
        this.addActor(playerImage);
        this.addActor(playerBorder);
        this.addActor(playerLevel);
        this.addActor(healthBarShadow);
        this.addActor(healthBar);
        this.addActor(playerHealth);
        this.addActor(staminaBar);


        /*setting bar dimensions*/
        float healthBarWidth = (float) player.getHealthCur() / player.getHealthMax() * 310;
        float healthBarHeight = 29;
        float staminaBarWidth = (float) player.getStaminaCur() / player.getStaminaMax() * 260;
        float staminaBarHeight = 22;
        healthBar.setSize(healthBarWidth, healthBarHeight);
        healthBarShadow.setSize(healthBarWidth, healthBarHeight);
        staminaBar.setSize(staminaBarWidth, staminaBarHeight);
        levelBar.setSize(player.getXp()/player.getXpThreshold() * 100,
                115);

        /* Setting bar colours, health changes from green to red as health drops
         * 200/157 multiplier so function ranges from 0 to 255; the range of RGB colours.
         */
        setHealthBarColours();
        healthBarShadow.setColor(Color.BLACK);
        staminaBar.setColor(Color.ORANGE);
        levelBar.setColor(Color.GOLD);

        /* positioning and scaling images bars and labels*/
        healthBar.setPosition(130, 51);
        healthBarShadow.setPosition(130, 50);
        staminaBar.setPosition(130, 22);
        playerImage.setPosition(25, 8);
        playerHealth.setPosition(135, 50);
        playerLevel.setPosition(135, 90);
        levelBar.setPosition(10, 5);

        playerLevel.setFontScale(1.5f);
        playerHealth.setFontScale(1.2f);
        playerImage.scaleBy(-0.75f);
        playerBorder.scaleBy(-0.8f);
    }

    /**
     * method to update the health, health bar, stamina bar and level of the display
     */
    public void updatePlayerStatus() {
        /* Health bar */
        float healthBarWidth = (float) player.getHealthCur() /
                player.getHealthMax() * 315;

        setHealthBarColours();
        healthBar.setWidth(healthBarWidth);
        playerHealth.setText(player.getHealthCur() + " / " + player.getHealthMax());

        /* shadow that displays when a lot of damage is taken in one hit */
        if (healthBarShadow.getImageWidth() > healthBarWidth) {
            healthBarShadow.setWidth(healthBarShadow.getImageWidth() - 2);
        }
        /* Stamina bar */
        float staminaBarWidth = (float) player.getStaminaCur() /
                player.getStaminaMax() * 260;
        staminaBar.setWidth(staminaBarWidth);
        playerLevel.setText("Lv: " + player.getLevel());
        levelBar.setWidth(((float) player.getXp()/(float)player.getXpThreshold()) * 110f);

    }

    /**
     * method to change the colour value of the players health bar so its a gradient from green to red
     * as health drops from full to 0
     */
    private void setHealthBarColours() {
        float healthBarWidth = ((float) player.getHealthCur()) /
                ((float) player.getHealthMax()) * 330f;
        /* colour functions*/
        float G;
        float R;
        if (healthBarWidth > (330f / 2f)) {
            G = 1f;
            R = 2f - (2f * healthBarWidth / 325f);
        } else {
            G = 2f * healthBarWidth / 325f;
            R = 1f;
        }
        //setting colour
        healthBar.setColor(R, G, 0f, 1f);
    }
}

