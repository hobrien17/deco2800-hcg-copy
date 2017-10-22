package com.deco2800.hcg.contexts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ShaderManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.managers.TextureManager;


public class OptionsMenuContext extends UIContext{

	private ImageButton toggleMuteButton;
	private ImageButton toggleShadersButton;
	private ImageButton backButton;
	private Table table;
	private boolean isMute;
	private boolean isShaders;
	private GameManager gameManager;
	private ContextManager contextManager;
	private TextureManager textureManager;
	private ShaderManager shaderManager;
	private SoundManager soundManager;
	
	public OptionsMenuContext() {

		 // Get necessary managers
        gameManager = GameManager.get();
		contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
		textureManager = (TextureManager)
				gameManager.getManager(TextureManager.class);
		shaderManager = (ShaderManager)
				gameManager.getManager(ShaderManager.class);
		soundManager = (SoundManager)
				gameManager.getManager(SoundManager.class);
		
		draw();
	}

	private void draw() {

		table = new Table();
		table.setFillParent(true);
		table.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());

		isMute = soundManager.getMuteStatus();
		isShaders = shaderManager.getShaderStatus();

		if (isMute) {
			toggleMuteButton = new ImageButton(new Image(textureManager.getTexture("toggle_mute_button_greyscale")).getDrawable());
		} else {
			toggleMuteButton = new ImageButton(new Image(textureManager.getTexture("toggle_mute_button")).getDrawable());
		}

		if (isShaders) {
			toggleShadersButton = new ImageButton(new Image(textureManager.getTexture("toggle_shaders_button_greyscale")).getDrawable());
		} else {
			toggleShadersButton = new ImageButton(new Image(textureManager.getTexture("toggle_shaders_button")).getDrawable());
		}

		backButton = new ImageButton(new Image(textureManager.getTexture("menu_back_button")).getDrawable());

		table.add(toggleShadersButton);
		table.row();
		table.add(toggleMuteButton);
		table.row();
		table.add(backButton);
		stage.addActor(table);

		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});
		toggleMuteButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				soundManager.toggleMute();
				draw();
			}
		});
		toggleShadersButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				shaderManager.toggleShaders();
				draw();
			}
		});
	}


}






