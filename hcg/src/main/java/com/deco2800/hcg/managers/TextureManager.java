package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Texture manager acts as a cache between the file system and the renderers.
 * This allows all textures to be read into memory at the start of the game
 * saving file reads from being completed during rendering.
 *
 * With this in mind don't load textures you're not going to use. Textures that
 * are not used should probably (at some point) be removed from the list and
 * then read from disk when needed again using some type of reference counting
 *
 * @Author Tim Hadwen
 */
public class TextureManager extends Manager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TextureManager.class);

	/**
	 * A HashMap of all textures with string keys
	 */
	private Map<String, Texture> textureMap = new HashMap<String, Texture>();

	/**
	 * Constructor
	 * Currently loads up all the textures but probably shouldn't/doesn't
	 * need to.
	 */
	public TextureManager() {
		textureMap.put("grass", new Texture("resources/maps/environment/grass.png"));
		textureMap.put("ground", new Texture("resources/maps/environment/ground.png"));
		textureMap.put("spacman", new Texture("resources/sprites/player/spacman.png"));
		textureMap.put("spacman_swim", new Texture("resources/sprites/player/spacman_swim.png"));
		textureMap.put("hcg_character", new Texture("resources/sprites/player/hcg_character_hat_logo.png"));
		textureMap.put("hcg_character_swim", new Texture("resources/sprites/player/hcg_character_hat_swim.png"));
		textureMap.put("selected", new Texture("resources/sprites/misc/selected.png"));
		textureMap.put("selected_black", new Texture("resources/sprites/misc/selected_black.png"));
		textureMap.put("tower", new Texture("resources/sprites/misc/tower.png"));
		textureMap.put("squirrel", new Texture("resources/sprites/enemies/squirrel.png"));
		textureMap.put("battle_seed", new Texture("resources/sprites/seeds/battle_seed.png"));
		textureMap.put("explosive_seed", new Texture("resources/sprites/seeds/explosive_seed.png"));
		textureMap.put("fire_seed", new Texture("resources/sprites/seeds/fire_seed.png"));
		textureMap.put("gardening_seed", new Texture("resources/sprites/seeds/gardening_seed.png"));
		textureMap.put("grass_seed", new Texture("resources/sprites/seeds/grass_seed.png"));
		textureMap.put("ice_seed", new Texture("resources/sprites/seeds/ice_seed.png"));
		textureMap.put("water_seed", new Texture("resources/sprites/seeds/water_seed.png"));
		textureMap.put("plant_01", new Texture("resources/sprites/plants/plant_01.png"));
		textureMap.put("plant_02", new Texture("resources/sprites/plants/plant_02.png"));
		textureMap.put("sunflower_01", new Texture("resources/sprites/plants/sunflower_pot_01.png"));
		textureMap.put("sunflower_02", new Texture("resources/sprites/plants/sunflower_pot_02.png"));
		textureMap.put("sunflower_03", new Texture("resources/sprites/plants/sunflower_pot_03.png"));
		textureMap.put("cactus_01", new Texture("resources/sprites/plants/cactus_pot_01.png"));
		textureMap.put("cactus_02", new Texture("resources/sprites/plants/cactus_pot_02.png"));
		textureMap.put("cactus_03", new Texture("resources/sprites/plants/cactus_pot_03.png"));
		textureMap.put("lily_01", new Texture("resources/sprites/plants/waterlily_01.png"));
		textureMap.put("lily_02", new Texture("resources/sprites/plants/waterlily_02.png"));
		textureMap.put("lily_03", new Texture("resources/sprites/plants/waterlily_03.png"));
		textureMap.put("grass_01", new Texture("resources/sprites/plants/grass_pot_01.png"));
		textureMap.put("grass_02", new Texture("resources/sprites/plants/grass_pot_02.png"));
		textureMap.put("grass_03", new Texture("resources/sprites/plants/grass_pot_03.png"));
		textureMap.put("inferno_01", new Texture("resources/sprites/plants/inferno_01.png"));
		textureMap.put("inferno_02", new Texture("resources/sprites/plants/inferno_02.png"));
		textureMap.put("inferno_03", new Texture("resources/sprites/plants/inferno_03.png"));
		textureMap.put("pot", new Texture("resources/sprites/plants/pot.png"));
		textureMap.put("tree", new Texture("resources/sprites/plants/tree.png"));

		//NPCs
		textureMap.put("character_1", new Texture("resources/sprites/npc/character_1.png"));
		textureMap.put("character_2", new Texture("resources/sprites/npc/character_2.png"));
		textureMap.put("character_shop", new Texture("resources/sprites/npc/character_shop.png"));

		
		// Textures for the Main Menu and In-Game Menu
		textureMap.put("menu_title", new Texture("resources/ui/main_menu/menu_title.png"));
		textureMap.put("menu_no_button", new Texture("resources/ui/main_menu/menu_no_button.png"));
		textureMap.put("menu_options_button", new Texture("resources/ui/main_menu/menu_options_button.png"));
		textureMap.put("menu_play_button", new Texture("resources/ui/main_menu/menu_play_button.png"));
		textureMap.put("menu_quit_button", new Texture("resources/ui/main_menu/menu_quit_button.png"));
		textureMap.put("menu_quit_text", new Texture("resources/ui/main_menu/menu_quit_text.png"));
		textureMap.put("menu_resume_button", new Texture("resources/ui/main_menu/menu_resume_button.png"));
		textureMap.put("menu_yes_button", new Texture("resources/ui/main_menu/menu_yes_button.png"));
		textureMap.put("main_menu_background", new Texture("resources/ui/main_menu/main_menu_background.png"));

	}

	/**
	 * Gets a texture object for a given string id
	 * 
	 * @param id
	 *            Texture identifier
	 * @return Texture for given id
	 */
	public Texture getTexture(String id) {
		
		if (textureMap.containsKey(id)) {
			return textureMap.get(id);
		} else {
			LOGGER.error("No texture exists: " + id);
			System.err.println("No texture exists: " + id); //FIXME logger is currently broken
			return null;
		}

	}

	/**
	 * Saves a texture with a given id
	 * 
	 * @param id
	 *            Texture id
	 * @param filename
	 *            Filename within the assets folder
	 */
	public void saveTexture(String id, String filename) {
		LOGGER.info("Saving texture" + id + " with Filename " + filename);
		if (!textureMap.containsKey(id)) {
			textureMap.put(id, new Texture(filename));
		}
	}
}
