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

/**
 * 
 * Instructions Menu
 * 
 * @author Dylan Ridings
 *
 */

public class InstructionsMenuContext extends UIContext {

	private Image instructionsTitle;
	private Image instructionsText;
	private ImageButton instructionsBack;
	private Table table;
	
	/**
	 * Constructor for InstructionsMenuContext
	 */
	public InstructionsMenuContext() {
		
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
		
		instructionsTitle = new Image(textureManager.getTexture("instructions_title"));
		instructionsText = new Image(textureManager.getTexture("instructions_text"));
		instructionsBack = new ImageButton(new Image(textureManager.getTexture("instructions_back_button")).getDrawable());
		
		table.add(instructionsTitle);
		table.row();
		table.add(instructionsText);
		table.row();
		table.add(instructionsBack);
		stage.addActor(table);
		
		instructionsBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});
		
	}
	
}
