package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;

/**
 * Main Menu
 * 
 * @author Richy McGregor
 */
public class MainMenuContext extends UIContext {

	private Label title;
	private TextButton newGame;
	private TextButton quit;
	private Table table;

	public MainMenuContext() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);

		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

		table = new Table();
		table.setFillParent(true);

		title = new Label("Hardcore Gardening", skin);
		newGame = new TextButton("New Game", skin);
		quit = new TextButton("Quit", skin);

		table.add(title);
        table.row();
		table.add(newGame);
		table.row();
		table.add(quit);
		stage.addActor(table);

		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.pushContext(new PlayContext());
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
