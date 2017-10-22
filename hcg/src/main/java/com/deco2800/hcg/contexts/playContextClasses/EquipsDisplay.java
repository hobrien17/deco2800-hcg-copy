package com.deco2800.hcg.contexts.playContextClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.entities.bullets.BulletType;
import com.deco2800.hcg.weapons.WeaponType;

import java.util.Map;
import java.util.HashMap;

public class EquipsDisplay extends Group {

	private Player player;
	private GameManager gameManager;
	private Map<String, Image> sprites;
	private Label currentBulletCount;
	private TextureManager textureManager;
	private Inventory inventory;
	private LabelStyle style;

	public EquipsDisplay() {
		super();

        /*adding GameManager and obtaining player class*/
		gameManager = GameManager.get();
		PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
		player = playerManager.getPlayer();
		inventory = player.getInventory();
		textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

		sprites = new HashMap<>();
		addActors();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/ui/radial_menu/advent_pro.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 70;
		BitmapFont font = generator.generateFont(parameter);
		style = new LabelStyle(font, Color.valueOf("#FFFFFF"));
		generator.dispose();

		currentBulletCount = new Label("", style);
		currentBulletCount.setPosition(90, -40);

		this.addActor(sprites.get("Machinegun"));
		this.addActor(sprites.get("Sunflower"));
		this.addActor(currentBulletCount);
	}

	private int getCount(String texture) {
		int itemCount = 0;
		for(int i = 0; i < inventory.getNumItems(); i++) {
			if(inventory.getItem(i).getTexture().equals(texture)) {
				itemCount += inventory.getItem(i).getStackSize();
			}
		}
		return itemCount;
	}

	private void addActors() {
		sprites.put("Sunflower", new Image(textureManager.getTexture("sunflower_seed")));
		sprites.get("Sunflower").setPosition(10, -100);
		sprites.get("Sunflower").setSize(80, 200);
		sprites.put("Ice", new Image(textureManager.getTexture("ice_seed")));
		sprites.get("Ice").setPosition(10, -100);
		sprites.get("Ice").setSize(80, 200);
		sprites.put("Fire", new Image(textureManager.getTexture("fire_seed")));
		sprites.get("Fire").setPosition(10, -100);
		sprites.get("Fire").setSize(80, 200);
		sprites.put("Explosion", new Image(textureManager.getTexture("explosive_seed")));
		sprites.get("Explosion").setPosition(10, -100);
		sprites.get("Explosion").setSize(80, 200);
		sprites.put("Grass", new Image(textureManager.getTexture("grass_seed")));
		sprites.get("Grass").setPosition(10, -100);
		sprites.get("Grass").setSize(80, 200);
		sprites.put("Water", new Image(textureManager.getTexture("water_seed")));
		sprites.get("Water").setPosition(10, -100);
		sprites.get("Water").setSize(80, 200);
		sprites.put("Machinegun", new Image(textureManager.getTexture("machinegun_e")));
		sprites.get("Machinegun").setPosition(-15, 15);
		sprites.get("Machinegun").setSize(200, 200);
		sprites.put("Shotgun", new Image(textureManager.getTexture("shotgun_e")));
		sprites.get("Shotgun").setPosition(15, 25);
		sprites.get("Shotgun").setSize(200, 200);
		sprites.put("Multigun", new Image(textureManager.getTexture("multigun_e")));
		sprites.get("Multigun").setPosition(15, 25);
		sprites.get("Multigun").setSize(200, 200);
		sprites.put("Starfall", new Image(textureManager.getTexture("stargun_w")));
		sprites.get("Starfall").setPosition(0, 15);
		sprites.get("Starfall").setSize(200, 200);
	}

	/**
	 * method to update the health, health bar, stamina bar and level of the display
	 */
	public void update() {
        for (String key : sprites.keySet()) {
        	this.removeActor(sprites.get(key));
		}
		this.removeActor(currentBulletCount);

		WeaponType wpnType = player.getEquippedWeapon().getWeaponType();
        if (wpnType == WeaponType.MACHINEGUN) {
        	this.addActor(sprites.get("Machinegun"));
		} else if (wpnType == WeaponType.SHOTGUN) {
        	this.addActor(sprites.get("Shotgun"));
		} else if (wpnType == WeaponType.MULTIGUN) {
			this.addActor(sprites.get("Multigun"));
		} else if (wpnType == WeaponType.STARGUN) {
			this.addActor(sprites.get("Starfall"));
		}

		int count = 0;
		BulletType bltType = player.getEquippedWeapon().getBulletType();
		if (bltType == BulletType.BASIC) {
			this.addActor(sprites.get("Sunflower"));
			count += getCount("sunflower_seed");
		} else if (bltType == BulletType.ICE) {
			this.addActor(sprites.get("Ice"));
			count += getCount("ice_seed");
		} else if (bltType == BulletType.FIRE) {
			this.addActor(sprites.get("Fire"));
			count += getCount("fire_seed");
		} else if (bltType == BulletType.EXPLOSION) {
			this.addActor(sprites.get("Explosion"));
			count += getCount("explosive_seed");
		} else if (bltType == BulletType.GRASS) {
			this.addActor(sprites.get("Grass"));
			count += getCount("grass_seed");
		} else if (bltType == BulletType.HOMING) {
			this.addActor(sprites.get("Water"));
			count += getCount("water_seed");
		}
		currentBulletCount = new Label(String.valueOf(count), style);
		currentBulletCount.setPosition(90, -40);
		this.addActor(currentBulletCount);
	}

}

