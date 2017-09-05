package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.multiplayer.NetworkState;
import com.deco2800.hcg.worlds.DemoWorld;

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

	/**
	 * Constructor for the MultiplayerMenuContext
	 */
	public MultiplayerMenuContext() {
		
		 // Get necessary managers
        GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
		PlayerManager playerManager = (PlayerManager)
                gameManager.getManager(PlayerManager.class);
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
		
		go.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				NetworkState.init(false);
				NetworkState.join(name.getText());
				gameManager.setWorld(new DemoWorld());
				gameManager.getWorld().addEntity(playerManager.getPlayer());
				gameManager.getWorld().addEntity(playerManager.getPlayer().getEquippedWeapon());
				Player player1 = new Player(0, 5, 10, 0);
				player1.initialiseNewPlayer(5, 5, 5, 5, 5, 20);
				gameManager.getWorld().addEntity(player1);
				gameManager.getWorld().addEntity(player1.getEquippedWeapon());
				contextManager.pushContext(new PlayContext());
			}
		});
		
		host.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				NetworkState.init(true);
				contextManager.pushContext(new WorldMapContext());
			}
		});
		
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});
		
	}
}
