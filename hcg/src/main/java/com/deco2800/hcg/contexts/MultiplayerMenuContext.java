package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * Multiplayer Menu
 * 
 * @author Dylan Ridings
 */

public class MultiplayerMenuContext extends UIContext {
	
	private Image title;
	private TextField name;
	private Table table;
	private ImageButton go;
	private ImageButton host;
	private ImageButton back;
	
	public MultiplayerMenuContext() {
		
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
		go = new ImageButton(new Image(textureManager.getTexture("menu_go_button")).getDrawable());
		host = new ImageButton(new Image(textureManager.getTexture("menu_host_button")).getDrawable());
		back = new ImageButton(new Image(textureManager.getTexture("menu_back_button")).getDrawable());
		name = new TextField(null, skin);
		
		table.add(title);
		table.row();
		table.add(name);
		table.row();
		table.add(go);
		table.row();
		table.add(host);
		table.row();
		table.add(back);
		stage.addActor(table);
		
		
		// Change this to implement Multiplayer
		go.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.pushContext(new PlayContext());
			}
		});
		
		host.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.pushContext(new PlayContext());
			}
		});
		
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.pushContext(new MainMenuContext());
			}
		});
		
	}
}
