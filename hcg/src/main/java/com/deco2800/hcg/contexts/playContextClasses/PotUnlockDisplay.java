package com.deco2800.hcg.contexts.playContextClasses;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.stackable.Key;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.util.WorldUtil;

public class PotUnlockDisplay extends Dialog {
	
	private GameManager gameManager;
	private PlayerManager playerManager;
	private Inventory inventory;
	
	private Stage stage;
	private Skin skin;
	private boolean open;
	
	private Table infoTbl;
	private Label infoLbl;
	private Label titleLbl;
	private Label xLbl;
	private Button conf;
	private Button cancel;
	
	private final static float WIDTH = 300f;
	private final static float HEIGHT = 200f;
	
	public PotUnlockDisplay(Stage stage, Skin skin) {
		super("Unlock Pot?", skin);
		this.stage = stage;
		this.skin = skin;
		gameManager = GameManager.get();
		playerManager = (PlayerManager)gameManager.getManager(PlayerManager.class);
		inventory = playerManager.getPlayer().getInventory();
        this.setMovable(false);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        open = false;
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/ui/plant_ui/basic_font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		BitmapFont font = generator.generateFont(parameter);
		LabelStyle normal = new LabelStyle(font, Color.valueOf("#004000"));
		generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/ui/plant_ui/basic_bold.ttf"));
		parameter.size = 50;
		font = generator.generateFont(parameter);
		LabelStyle bold = new LabelStyle(font, Color.valueOf("#004000"));
		generator.dispose();

		TextureManager texManager = (TextureManager)gameManager.getManager(TextureManager.class);
		Image keyImage = new Image(texManager.getTexture("key"));
		infoTbl = new Table();
		infoLbl = new Label("", bold);
		titleLbl = new Label("", normal);
		xLbl = new Label("x", normal);
		this.getContentTable().add(infoTbl);
		infoTbl.add(keyImage).size(50, 50);
		infoTbl.add(xLbl);
		infoTbl.add(infoLbl);
		this.getContentTable().row();
		this.getContentTable().add(titleLbl);
		
		conf = new TextButton("Open", skin);
		cancel = new TextButton("Cancel", skin);
		
		cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
            }
        });
		
		conf.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(playerManager.getPlayer().getPosX(), 
                		playerManager.getPlayer().getPosY(), 1.5f, Pot.class);
                if(closest.isPresent()) {
                	((Pot)closest.get()).unlock();
                	inventory.removeItem(new Key());
                }
            }
        });
		
		this.button(conf);
		this.button(cancel);
		
		Button closeButton = new Button(skin.getDrawable("button-close"));
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
            }
        });
        closeButton.setColor(Color.GREEN);
        this.getTitleTable().add(closeButton);
	}
	
	public void update() {				
		int keyCount = 0;
		for(int i = 0; i < inventory.getNumItems(); i++) {
			if(inventory.getItem(i) instanceof Key) {
				keyCount += inventory.getItem(i).getStackSize();
			}
		}
		infoLbl.setText(String.format("%d", keyCount));
		
		if(keyCount > 0) {
			titleLbl.setText("Open pot by using key?");
			this.getTitleLabel().setText("Open pot?");
			conf.setVisible(true);
			cancel.setVisible(true);
		} else {
			titleLbl.setText("No keys in inventory!");
			this.getTitleLabel().setText("No keys!");
			conf.setVisible(false);
			cancel.setVisible(false);
		}
	}
	
	public void open() {
		if(WorldUtil.closestEntityToPosition(playerManager.getPlayer().getPosX(), playerManager.getPlayer().getPosY(), 
				1.5f, Pot.class).isPresent()) {
			update();
			stage.addActor(this);
			open = true;
		}
	}
	
	public void close() {
		this.remove();
		open = false;
	}
	
	public void change() {
		if(open) {
			close();
		} else {
			open();
		}
	}
	
	public boolean isOpen() {
		return open;
	}
	
}
