package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
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
	private Table table;

	public MainMenuContext() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);

		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

		table = new Table();
		table.setFillParent(true);
		table.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());

		title = new Image(textureManager.getTexture("menu_title"));
		newGame = new ImageButton(new Image(textureManager.getTexture("menu_play_button")).getDrawable());
		quit = new ImageButton(new Image(textureManager.getTexture("menu_quit_button")).getDrawable());
		multiplayer = new ImageButton(new Image(textureManager.getTexture("menu_multiplayer_button")).getDrawable());

		table.add(title);
        table.row();
		table.add(newGame);
		table.row();
		table.add(multiplayer);
		table.row();
		table.add(quit);
		stage.addActor(table);

		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.pushContext(new PlayContext());
			}
		});
		
		multiplayer.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.pushContext(new MultiplayerMenuContext());
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
