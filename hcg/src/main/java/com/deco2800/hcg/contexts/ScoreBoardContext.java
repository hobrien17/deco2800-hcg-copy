package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.multiplayer.InputType;

import java.util.*;
import java.util.List;

/**
 * Score board UI for 1...n players
 *
 * @author Ethan Phan, Duc Thuan Chu
 */
public class ScoreBoardContext extends UIContext {
	private Window playersWindow;
    
    protected Table masterTable;
    protected Table topRowInfoTable;

    List<Window> playerWindows;
    protected Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
    TextButton backButton;

    protected GameManager gameManager;
    protected ContextManager contextManager;
    protected TextureManager textureManager;
    protected PlayerManager playerManager;
    protected InputManager inputManager;
    private int keyCode;
    /**
     * Creating Score Board
     */
    public ScoreBoardContext() {
        // Get necessary managers
        gameManager = GameManager.get();
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        inputManager =  (InputManager) GameManager.get().getManager(InputManager.class);
        
        masterTable = new Table(skin);
        masterTable.setFillParent(true);
        masterTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());
        stage.addActor(masterTable);
        playerWindows = new ArrayList<Window>();
        
        setPlayersWindow();
        display_top_row_info();
        display_player_windows();
    }
    /**
     *  The outermost window that contains the player windows
     */
    
    private void setPlayersWindow() {
        playersWindow = new Window("Player List" , skin); 
        Texture backgroundTexture = textureManager.getTexture("ccWindow_Border_White");
        playersWindow.setBackground(new Image(backgroundTexture).getDrawable());
        Label title = new Label("YOUR TEAM", skin);
        playersWindow.add(title).top().center();
        playersWindow.row();
    } 
    
    private void handleKeyDown(int keyCode) {
    	System.out.println("Key code : " + keyCode);
    	switch(keyCode) {
    		case Input.Keys.TAB:
    			this.contextManager.popContext();
    			break;
    	}
    }
    
    /**
     * Top buttons to go back to the game
     */
    public void display_top_row_info() {
        topRowInfoTable = new Table(skin);
        backButton = new TextButton("Back", skin);
        topRowInfoTable.add(backButton).left().expandX();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
        masterTable.add(topRowInfoTable).top().left().expandX().fillX().colspan(2).padBottom(15);
        masterTable.row();
    }
    
    /**
     * Adding players info into the score board as pressing the TAB when in the game
     */
    
    public void display_player_windows() {
	
        int numPlayers = playerManager.getPlayers().size();
        for (int i = 0; i < numPlayers; i++) {
        		int level = playerManager.getPlayer().getLevel();
        		playerWindows.add(new Window(" ",skin));
        		int curHealth = playerManager.getPlayers().get(i).getHealthCur();
        		int maxHealth  = playerManager.getPlayers().get(i).getHealthMax();
        		int currentStamina = playerManager.getPlayer().getStaminaCur();
        		int maxStamina = playerManager.getPlayer().getStaminaMax();
	        	Label healthLabel = new Label("Health: " + curHealth + "/" + maxHealth, skin);
	            Label staminaLabel = new Label("Stamina: " + currentStamina + "/" + maxStamina, skin);
	            
	        	Label playerLabel = new Label("Player" + (i + 1) + "- Level " + level, skin);
	            Window window = playerWindows.get(i);
	            window.add(playerLabel).top();
	            window.row();
	            window.add(healthLabel);
	            window.row();
	            window.add(staminaLabel);
	            Texture backgroundTexture = textureManager.getTexture("ccWindow_Border_White");
	            window.setBackground(new Image(backgroundTexture).getDrawable());
	            playersWindow.add(window);
        }
        masterTable.add(playersWindow).top().expandY().fillY().padBottom(15);
    }
}
