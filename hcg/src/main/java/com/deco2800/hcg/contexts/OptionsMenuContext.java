package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ShaderManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.managers.TextureManager;

public class OptionsMenuContext extends UIContext{
	private Image optionsTitle;
	private ImageButton muteButton;
	private ImageButton toggleShadersButton;
	private ImageButton backButton;
	private Table table;
	private boolean isMute;
	private boolean isShadersMute;
	
	public OptionsMenuContext(){
		 // Get necessary managers
        GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);
		ShaderManager shaderManager = (ShaderManager)
				gameManager.getManager(ShaderManager.class);
		SoundManager soundManager = (SoundManager)
				gameManager.getManager(SoundManager.class);
		
		table = new Table();
		table.setFillParent(true);
		table.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());
		
		optionsTitle = new Image(textureManager.getTexture("menu_options_button"));
		toggleShadersButton = new ImageButton(new Image(textureManager.getTexture("toggle_shaders_button")).getDrawable());
		muteButton = new ImageButton(new Image(textureManager.getTexture("toggle_mute_button")).getDrawable());
		backButton = new ImageButton(new Image(textureManager.getTexture("instructions_back_button")).getDrawable());
		
		table.add(optionsTitle);
		table.row();
		table.add(toggleShadersButton);
		table.row();
		table.add(muteButton);
		table.row();
		table.add(backButton);
		stage.addActor(table);
		
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});
		muteButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				soundManager.toggleMute();
			}
		});
		toggleShadersButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				shaderManager.toggleShaders();
			}
		});
		
		
	}
}
