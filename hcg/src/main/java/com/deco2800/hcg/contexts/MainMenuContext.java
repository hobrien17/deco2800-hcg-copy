package com.deco2800.hcg.contexts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * Main Menu
 * 
 * @author Richy McGregor
 */
public class MainMenuContext extends UIContext {

	private Image title;
	private ImageButton newGame;
	private ImageButton quit;
	private ImageButton multiplayer;
	private ImageButton instructions;
	private Table table;

	public MainMenuContext() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);
		NetworkManager networkManager = (NetworkManager) 
				gameManager.getManager(NetworkManager.class);

		table = new Table();
		table.setFillParent(true);
		table.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());

		title = new Image(textureManager.getTexture("menu_title"));
		newGame = new ImageButton(new Image(textureManager.getTexture("menu_play_button")).getDrawable());
		quit = new ImageButton(new Image(textureManager.getTexture("menu_quit_button")).getDrawable());
		multiplayer = new ImageButton(new Image(textureManager.getTexture("menu_multiplayer_button")).getDrawable());
		instructions = new ImageButton(new Image(textureManager.getTexture("menu_instructions_button")).getDrawable());

		table.add(title);
        table.row();
		table.add(newGame);
		table.row();
		table.add(multiplayer);
		table.row();
		table.add(instructions);
		table.row();
		table.add(quit);
		stage.addActor(table);

		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				networkManager.setMultiplayerGame(false);
				contextManager.pushContext(new CharacterCreationContext());
			}
		});
		
		multiplayer.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				networkManager.setMultiplayerGame(true);
				contextManager.pushContext(new ServerBrowserContext());
			}
		});
		
		instructions.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.pushContext(new InstructionsMenuContext());
			}
		});

		quit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});

	}

}
