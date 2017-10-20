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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.stackable.Key;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.util.WorldUtil;

public class PotUnlockDisplay extends Window {
	
	private GameManager gameManager;
	private PlayerManager playerManager;
	private SoundManager soundManager;
	private Inventory inventory;
	
	private Stage stage;
	private Skin skin;
	private boolean open;
	
	private Table infoTbl;
	private Table btns;
	private Table containerL;
	private Table containerR;
	private Label infoLbl;
	private Label titleLbl;
	private Label xLbl;
	private Button conf;
	private Button cancel;
	
	private static final float WIDTH = 300f;
	private static final float HEIGHT = 200f;
	
	public PotUnlockDisplay(Stage stage, Skin skin) {
		super("Unlock Pot?", skin);
		this.stage = stage;
		this.skin = skin;
		gameManager = GameManager.get();
		playerManager = (PlayerManager)gameManager.getManager(PlayerManager.class);
		soundManager = (SoundManager)gameManager.getManager(SoundManager.class);
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
		this.add(infoTbl);
		infoTbl.add(keyImage).size(50, 50);
		infoTbl.add(xLbl);
		infoTbl.add(infoLbl);
		this.row();
		this.add(titleLbl);
		this.row();
		
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
                	soundManager.playSound("key");
                	((Pot)closest.get()).unlock();
                	inventory.removeItem(new Key());
                	close();
                }
            }
        });
		
		btns = new Table();
		this.row();
		this.add(btns).pad(15).width(270);
		containerL = new Table();
		containerR = new Table();
		btns.add(containerL).width(133).align(Align.left);
		btns.add(containerR).width(135).align(Align.right);
		containerL.add(conf);
		containerR.add(cancel);
		
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
		} else {
			titleLbl.setText("No keys in inventory!");
			this.getTitleLabel().setText("No keys!");
			conf.setVisible(false);
		}
	}
	
	public void open() {
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(playerManager.getPlayer().getPosX(), 
				playerManager.getPlayer().getPosY(), 1.5f, Pot.class);
		if (closest.isPresent() && ((Pot)closest.get()).isLocked()) {
			update();
			stage.addActor(this);
			open = true;
		}
	}
	
	public void close() {
		this.remove();
		open = false;
	}
	
	public boolean isOpen() {
		return open;
	}
	
}
