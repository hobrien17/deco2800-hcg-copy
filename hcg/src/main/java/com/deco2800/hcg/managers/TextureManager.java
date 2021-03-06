package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
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

		// Corpse textures
		textureMap.put("corpse", new Texture("resources/sprites/dead_enemies/deadant.png"));
		textureMap.put("sunflower_corpse", new Texture("resources/sprites/dead_enemies/sunflower.png"));
		textureMap.put("cactus_corpse_01", new Texture("resources/sprites/dead_enemies/cactus01.png"));
		textureMap.put("cactus_corpse_02", new Texture("resources/sprites/dead_enemies/cactus02.png"));
		textureMap.put("cactus_corpse_03", new Texture("resources/sprites/dead_enemies/cactus03.png"));
		textureMap.put("ice_corpse_01", new Texture("resources/sprites/dead_enemies/ice01.png"));
		textureMap.put("ice_corpse_02", new Texture("resources/sprites/dead_enemies/ice02.png"));
		textureMap.put("ice_corpse_03", new Texture("resources/sprites/dead_enemies/ice03.png"));
		textureMap.put("water_corpse", new Texture("resources/sprites/dead_enemies/waterlily.png"));
		textureMap.put("grass_corpse", new Texture("resources/sprites/dead_enemies/grass.png"));
		textureMap.put("fire_corpse", new Texture("resources/sprites/dead_enemies/inferno.png"));
		textureMap.put("explosion", new Texture("resources/sprites/bullets/explosion.png"));

		// Miscellaneous textures
		textureMap.put("error", new Texture("resources/misc/error.png"));
		textureMap.put("loot_beam", new Texture("resources/sprites/loot_beam/loot_beam_2.0.png"));
		textureMap.put("lightmap", new Texture("resources/sprites/light/lightmap.png"));

		try {
			textureMap.put("grass", new Texture("resources/maps/textures/deprecated/grass.png"));
			textureMap.put("ground", new Texture("resources/maps/textures/deprecated/ground.png"));
			textureMap.put("hcg_character", new Texture("resources/sprites/player/hcg_character_hat_logo.png"));
			textureMap.put("hcg_character_swim", new Texture("resources/sprites/player/hcg_character_hat_swim.png"));
			textureMap.put("hcg_character_sink", new Texture("resources/sprites/player/hcg_character_hat_sink.png"));
			textureMap.put("selected", new Texture("resources/sprites/misc/selected.png"));
			textureMap.put("selected_black", new Texture("resources/sprites/misc/selected_black.png"));
			textureMap.put("tower", new Texture("resources/sprites/misc/tower.png"));
			textureMap.put("battle_seed", new Texture("resources/sprites/seeds/battle_seed.png"));
			textureMap.put("battle_seed_green", new Texture("resources/sprites/seeds/battle_seed_green.png"));
			textureMap.put("battle_seed_red", new Texture("resources/sprites/seeds/battle_seed_red.png"));
			textureMap.put("battle_seed_blue", new Texture("resources/sprites/seeds/battle_seed_blue.png"));
			textureMap.put("battle_seed_grey", new Texture("resources/sprites/seeds/battle_seed_grey.png"));
			textureMap.put("battle_seed_white", new Texture("resources/sprites/seeds/battle_seed_white.png"));
			textureMap.put("explosive_seed", new Texture("resources/sprites/seeds/explosive_seed.png"));
			textureMap.put("fire_seed", new Texture("resources/sprites/seeds/fire_seed.png"));
			textureMap.put("sunflower_seed", new Texture("resources/sprites/seeds/gardening_seed.png"));
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
			textureMap.put("ice_01", new Texture("resources/sprites/plants/Christmas_01.png"));
			textureMap.put("ice_02", new Texture("resources/sprites/plants/Christmas_02.png"));
			textureMap.put("ice_03", new Texture("resources/sprites/plants/Christmas_03.png"));
			textureMap.put("pot", new Texture("resources/sprites/plants/pot.png"));
			textureMap.put("pot_locked", new Texture("resources/sprites/plants/locked_pot.png"));
			textureMap.put("tree", new Texture("resources/sprites/plants/tree.png"));
			textureMap.put("fireball_up", new Texture("resources/sprites/bullets/fireball_up.png"));
			textureMap.put("fireball_down", new Texture("resources/sprites/bullets/fireball_down.png"));
			textureMap.put("fireball_left", new Texture("resources/sprites/bullets/fireball_left.png"));
			textureMap.put("fireball_right", new Texture("resources/sprites/bullets/fireball_right.png"));
			textureMap.put("sunflower_alt", new Texture("resources/sprites/plants/sunflower_alt.png"));
			textureMap.put("fertiliser", new Texture("resources/sprites/gardentools/fertiliser.png"));
			textureMap.put("bug_spray", new Texture("resources/sprites/gardentools/bugspray.png"));
			textureMap.put("trowel", new Texture("resources/sprites/gardentools/trowel.png"));
			textureMap.put("shovel", new Texture("resources/sprites/gardentools/shovel.png"));
			textureMap.put("hoe", new Texture("resources/sprites/gardentools/hoe.png"));
			textureMap.put("key_icon", new Texture("resources/sprites/plants/key.png"));

			// Enemy textures
			textureMap.put("antE", new Texture("resources/sprites/enemies/ant_E_1.png"));
			textureMap.put("antN", new Texture("resources/sprites/enemies/ant_N_1.png"));
			textureMap.put("antNE", new Texture("resources/sprites/enemies/ant_NE_1.png"));
			textureMap.put("antNW", new Texture("resources/sprites/enemies/ant_NW_1.png"));
			textureMap.put("antS", new Texture("resources/sprites/enemies/ant_S_1.png"));
			textureMap.put("antSE", new Texture("resources/sprites/enemies/ant_SE_1.png"));
			textureMap.put("antSW", new Texture("resources/sprites/enemies/ant_SW_1.png"));
			textureMap.put("antW", new Texture("resources/sprites/enemies/ant_W_1.png"));
			textureMap.put("antE2", new Texture("resources/sprites/enemies/ant_E_2.png"));
			textureMap.put("antN2", new Texture("resources/sprites/enemies/ant_N_2.png"));
			textureMap.put("antNE2", new Texture("resources/sprites/enemies/ant_NE_2.png"));
			textureMap.put("antNW2", new Texture("resources/sprites/enemies/ant_NW_2.png"));
			textureMap.put("antS2", new Texture("resources/sprites/enemies/ant_S_2.png"));
			textureMap.put("antSE2", new Texture("resources/sprites/enemies/ant_SE_2.png"));
			textureMap.put("antSW2", new Texture("resources/sprites/enemies/ant_SW_2.png"));
			textureMap.put("antW2", new Texture("resources/sprites/enemies/ant_W_2.png"));
			textureMap.put("mushroom0", new Texture("resources/sprites/enemies/Mushroom_0.png"));
			textureMap.put("mushroom1", new Texture("resources/sprites/enemies/Mushroom_1.png"));
			textureMap.put("mushroom2", new Texture("resources/sprites/enemies/Mushroom_2.png"));
			textureMap.put("mushroom3", new Texture("resources/sprites/enemies/Mushroom_3.png"));
			textureMap.put("mushroom4", new Texture("resources/sprites/enemies/Mushroom_4.png"));
			textureMap.put("mushroom5", new Texture("resources/sprites/enemies/Mushroom_5.png"));
			textureMap.put("hedgehogS", new Texture("resources/sprites/enemies/armadillo_S_1.png"));
			textureMap.put("hedgehogS2", new Texture("resources/sprites/enemies/armadillo_S_2.png"));
			textureMap.put("hedgehogE", new Texture("resources/sprites/enemies/armadillo_E_1.png"));
			textureMap.put("hedgehogE2", new Texture("resources/sprites/enemies/armadillo_E_2.png"));
			textureMap.put("hedgehogW", new Texture("resources/sprites/enemies/armadillo_W_1.png"));
			textureMap.put("hedgehogW2", new Texture("resources/sprites/enemies/armadillo_W_2.png"));
			textureMap.put("hedgehogN", new Texture("resources/sprites/enemies/armadillo_N_1.png"));
			textureMap.put("hedgehogN2", new Texture("resources/sprites/enemies/armadillo_N_2.png"));
			textureMap.put("hedgeballNS", new Texture("resources/sprites/enemies/armaball_NS_1.png"));
			textureMap.put("hedgeballNS2", new Texture("resources/sprites/enemies/armaball_NS_2.png"));
			textureMap.put("hedgeballWE", new Texture("resources/sprites/enemies/armaball_WE_1.png"));
			textureMap.put("hedgeballWE2", new Texture("resources/sprites/enemies/armaball_WE_2.png"));
			textureMap.put("snailE", new Texture("resources/sprites/enemies/snail_E.png"));
			textureMap.put("snailN", new Texture("resources/sprites/enemies/snail_N.png"));
			textureMap.put("snailS", new Texture("resources/sprites/enemies/snail_S.png"));
			textureMap.put("snailW", new Texture("resources/sprites/enemies/snail_W.png"));
			textureMap.put("squirrelS", new Texture("resources/sprites/enemies/Squirrel_S_1.png"));
			textureMap.put("squirrelS2", new Texture("resources/sprites/enemies/Squirrel_S_2.png"));
			textureMap.put("squirrelE", new Texture("resources/sprites/enemies/Squirrel_E_1.png"));
			textureMap.put("squirrelE2", new Texture("resources/sprites/enemies/Squirrel_E_2.png"));
			textureMap.put("squirrelW", new Texture("resources/sprites/enemies/Squirrel_W_1.png"));
			textureMap.put("squirrelW2", new Texture("resources/sprites/enemies/Squirrel_W_2.png"));
			textureMap.put("squirrelN", new Texture("resources/sprites/enemies/Squirrel_N_1.png"));
			textureMap.put("squirrelN2", new Texture("resources/sprites/enemies/Squirrel_N_2.png"));
			textureMap.put("crabS", new Texture("resources/sprites/enemies/Crab_S_1.png"));
			textureMap.put("crabS2", new Texture("resources/sprites/enemies/Crab_S_2.png"));
			textureMap.put("crabE", new Texture("resources/sprites/enemies/Crab_E_1.png"));
			textureMap.put("crabE2", new Texture("resources/sprites/enemies/Crab_E_2.png"));
			textureMap.put("crabW", new Texture("resources/sprites/enemies/Crab_W_1.png"));
			textureMap.put("crabW2", new Texture("resources/sprites/enemies/Crab_W_2.png"));
			textureMap.put("crabN", new Texture("resources/sprites/enemies/Crab_N_1.png"));
			textureMap.put("crabN2", new Texture("resources/sprites/enemies/Crab_N_2.png"));
			textureMap.put("Tree25", new Texture("resources/sprites/enemies/Tree_25.png"));
			textureMap.put("Tree50", new Texture("resources/sprites/enemies/Tree_50.png"));
			textureMap.put("Tree75", new Texture("resources/sprites/enemies/Tree_75.png"));
			textureMap.put("Tree100", new Texture("resources/sprites/enemies/Tree_100.png"));


			// Corpse textures
			textureMap.put("corpse", new Texture("resources/sprites/dead_enemies/deadant.png"));
			textureMap.put("sunflower_corpse", new Texture("resources/sprites/dead_enemies/sunflower.png"));
			textureMap.put("cactus_corpse_01", new Texture("resources/sprites/dead_enemies/cactus01.png"));
			textureMap.put("cactus_corpse_02", new Texture("resources/sprites/dead_enemies/cactus02.png"));
			textureMap.put("cactus_corpse_03", new Texture("resources/sprites/dead_enemies/cactus03.png"));
			textureMap.put("ice_corpse_01", new Texture("resources/sprites/dead_enemies/ice01.png"));
			textureMap.put("ice_corpse_02", new Texture("resources/sprites/dead_enemies/ice02.png"));
			textureMap.put("ice_corpse_03", new Texture("resources/sprites/dead_enemies/ice03.png"));
			textureMap.put("water_corpse", new Texture("resources/sprites/dead_enemies/waterlily.png"));
			textureMap.put("grass_corpse", new Texture("resources/sprites/dead_enemies/grass.png"));
			textureMap.put("fire_corpse", new Texture("resources/sprites/dead_enemies/inferno.png"));
			textureMap.put("explosion", new Texture("resources/sprites/bullets/explosion.png"));
			
			// Weapon textures
			textureMap.put("multigun_n", new Texture("resources/sprites/weapons/multigun_n.png"));
            textureMap.put("multigun_ne", new Texture("resources/sprites/weapons/multigun_ne.png"));
            textureMap.put("multigun_e", new Texture("resources/sprites/weapons/multigun_e.png"));
            textureMap.put("multigun_se", new Texture("resources/sprites/weapons/multigun_se.png"));
            textureMap.put("multigun_s", new Texture("resources/sprites/weapons/multigun_s.png"));
            textureMap.put("multigun_sw", new Texture("resources/sprites/weapons/multigun_sw.png"));
            textureMap.put("multigun_w", new Texture("resources/sprites/weapons/multigun_w.png"));
            textureMap.put("multigun_nw", new Texture("resources/sprites/weapons/multigun_nw.png"));
            textureMap.put("shotgun_n", new Texture("resources/sprites/weapons/shotgun_n.png"));
            textureMap.put("shotgun_ne", new Texture("resources/sprites/weapons/shotgun_ne.png"));
            textureMap.put("shotgun_e", new Texture("resources/sprites/weapons/shotgun_e.png"));
            textureMap.put("shotgun_se", new Texture("resources/sprites/weapons/shotgun_se.png"));
            textureMap.put("shotgun_s", new Texture("resources/sprites/weapons/shotgun_s.png"));
            textureMap.put("shotgun_sw", new Texture("resources/sprites/weapons/shotgun_sw.png"));
            textureMap.put("shotgun_w", new Texture("resources/sprites/weapons/shotgun_w.png"));
            textureMap.put("shotgun_nw", new Texture("resources/sprites/weapons/shotgun_nw.png"));
            textureMap.put("machinegun_n", new Texture("resources/sprites/weapons/machinegun_n.png"));
            textureMap.put("machinegun_ne", new Texture("resources/sprites/weapons/machinegun_ne.png"));
            textureMap.put("machinegun_e", new Texture("resources/sprites/weapons/machinegun_e.png"));
            textureMap.put("machinegun_se", new Texture("resources/sprites/weapons/machinegun_se.png"));
            textureMap.put("machinegun_s", new Texture("resources/sprites/weapons/machinegun_s.png"));
            textureMap.put("machinegun_sw", new Texture("resources/sprites/weapons/machinegun_sw.png"));
            textureMap.put("machinegun_w", new Texture("resources/sprites/weapons/machinegun_w.png"));
            textureMap.put("machinegun_nw", new Texture("resources/sprites/weapons/machinegun_nw.png"));
            textureMap.put("stargun_n", new Texture("resources/sprites/weapons/stargun_n.png"));
            textureMap.put("stargun_ne", new Texture("resources/sprites/weapons/stargun_ne.png"));
            textureMap.put("stargun_e", new Texture("resources/sprites/weapons/stargun_e.png"));
            textureMap.put("stargun_se", new Texture("resources/sprites/weapons/stargun_se.png"));
            textureMap.put("stargun_s", new Texture("resources/sprites/weapons/stargun_s.png"));
            textureMap.put("stargun_sw", new Texture("resources/sprites/weapons/stargun_sw.png"));
            textureMap.put("stargun_w", new Texture("resources/sprites/weapons/stargun_w.png"));
            textureMap.put("stargun_nw", new Texture("resources/sprites/weapons/stargun_nw.png"));

			// Miscellaneous textures
			textureMap.put("error", new Texture("resources/misc/error.png"));
			textureMap.put("blank", new Texture("resources/misc/blank.png"));

			//Terrain objects
			textureMap.put("wallblock", new Texture("resources/terrain_objects/wallblock.png"));
			textureMap.put("boulder", new Texture("resources/terrain_objects/boulder.png"));
			textureMap.put("rock", new Texture("resources/terrain_objects/rock.png"));
			textureMap.put("iceboulder", new Texture("resources/terrain_objects/iceboulder.png"));
			textureMap.put("house", new Texture("resources/terrain_objects/house.png"));
			textureMap.put("sludgebarrel", new Texture("resources/terrain_objects/sludge-barrel.png"));
			textureMap.put("concretewall", new Texture("resources/terrain_objects/concretewall.png"));
			textureMap.put("largeTree", new Texture("resources/terrain_objects/tree_dead_01.png"));
			textureMap.put("houseWRoof", new Texture("resources/terrain_objects/abandoned_house_01.png"));
			textureMap.put("houseWORoof", new Texture("resources/terrain_objects/busted_house_01.png"));
			textureMap.put("swing", new Texture("resources/terrain_objects/broken_swing.png"));
			textureMap.put("picketFenceNS", new Texture("resources/terrain_objects/picket_fence_northsouth.png"));
			textureMap.put("picketFenceEW", new Texture("resources/terrain_objects/picket_fence_eastwest.png"));
            textureMap.put("invisible", new Texture("resources/terrain_objects/invisible.png"));
            textureMap.put("barbeque", new Texture("resources/terrain_objects/barbeque.png"));
            textureMap.put("warningsign", new Texture("resources/terrain_objects/danger_sign_post.png"));
			textureMap.put("volcano", new Texture("resources/terrain_objects/volcano_01.png"));
            textureMap.put("car", new Texture("resources/terrain_objects/carwreck_01.png"));

			// terrain tiles
			textureMap.put("poisontile", new Texture("resources/maps/textures/deprecated/sludgebubbling1.png"));
            
			// weather puddles
			textureMap.put("icepuddle", new Texture("resources/puddles/ice_puddle.png"));
            textureMap.put("icepuddle2", new Texture("resources/puddles/ice_puddle_v2.png"));
            textureMap.put("icepuddle3", new Texture("resources/puddles/ice_puddle_v3.png"));
            textureMap.put("rainpuddle", new Texture("resources/puddles/rain_puddle.png"));
            textureMap.put("rainpuddle2", new Texture("resources/puddles/rain_puddle_v2.png"));
            textureMap.put("rainpuddle3", new Texture("resources/puddles/rain_puddle_v3.png"));
            textureMap.put("sandstormpuddle", new Texture("resources/puddles/sandstorm_puddle.png"));
            textureMap.put("sandstormpuddle2", new Texture("resources/puddles/sandstorm_puddle_v2.png"));
            textureMap.put("sandstormpuddle3", new Texture("resources/puddles/sandstorm_puddle_v3.png"));
            textureMap.put("crackspuddle", new Texture("resources/puddles/cracks.png"));
            textureMap.put("crackspuddle2", new Texture("resources/puddles/cracks_v2.png"));
            textureMap.put("crackspuddle3", new Texture("resources/puddles/cracks_v3.png"));
            textureMap.put("exit", new Texture("resources/maps/textures/exit.png"));

            
			//trees
			textureMap.put("tree_leafy", new Texture("resources/terrain_objects/tree_01.png"));
			textureMap.put("tree_leafless", new Texture("resources/terrain_objects/tree_dead_01.png"));
			textureMap.put("tree_budding", new Texture("resources/terrain_objects/tree_budding_01.png"));
			textureMap.put("tree_fruiting", new Texture("resources/terrain_objects/tree_fruit_01.png"));
			textureMap.put("tree_snowy", new Texture("resources/terrain_objects/tree_snow_01.png"));
			textureMap.put("tree_flaming", new Texture("resources/terrain_objects/tree_flame_01.png"));

			// mushrooms
			textureMap.put("mushroom_tree_thick", new Texture("resources/terrain_objects/mushroom1.png"));
			textureMap.put("mushroom_tree_thin", new Texture("resources/terrain_objects/mushroom2.png"));

			//NPCs
			textureMap.put("character_1", new Texture("resources/sprites/npc/Quest_1_SouthEast.png"));

			//White Shirt NPC
			textureMap.put("character_1_SouthEast", new Texture("resources/sprites/npc/Quest_1_SouthEast.png"));
			textureMap.put("character_1_South", new Texture("resources/sprites/npc/Quest_1_South.png"));
			textureMap.put("character_1_SouthWest", new Texture("resources/sprites/npc/Quest_1_SouthWest.png"));
			textureMap.put("character_1_West", new Texture("resources/sprites/npc/Quest_1_West.png"));
			textureMap.put("character_1_NorthWest", new Texture("resources/sprites/npc/Quest_1_NorthWest.png"));
			textureMap.put("character_1_North", new Texture("resources/sprites/npc/Quest_1_North.png"));
			textureMap.put("character_1_NorthEast", new Texture("resources/sprites/npc/Quest_1_NorthEast.png"));
			textureMap.put("character_1_East", new Texture("resources/sprites/npc/Quest_1_East.png"));

			//Purple Shirt NPC
			textureMap.put("character_2", new Texture("resources/sprites/npc/Quest_2_SouthEast.png"));
			textureMap.put("character_2_SouthEast", new Texture("resources/sprites/npc/Quest_2_SouthEast.png"));
			textureMap.put("character_2_South", new Texture("resources/sprites/npc/Quest_2_South.png"));
			textureMap.put("character_2_SouthWest", new Texture("resources/sprites/npc/Quest_2_SouthWest.png"));
			textureMap.put("character_2_West", new Texture("resources/sprites/npc/Quest_2_West.png"));
			textureMap.put("character_2_NorthWest", new Texture("resources/sprites/npc/Quest_2_NorthWest.png"));
			textureMap.put("character_2_North", new Texture("resources/sprites/npc/Quest_2_North.png"));
			textureMap.put("character_2_NorthEast", new Texture("resources/sprites/npc/Quest_2_NorthEast.png"));
			textureMap.put("character_2_East", new Texture("resources/sprites/npc/Quest_2_East.png"));

			textureMap.put("character_shop", new Texture("resources/sprites/npc/Shopkeeper_1.png"));
			textureMap.put("character_shop_2", new Texture("resources/sprites/npc/Shopkeeper_2.png"));
			textureMap.put("character_shop_3", new Texture("resources/sprites/npc/Shopkeeper_3.png"));

			//Players
			textureMap.put("player_0_stand", new Texture("resources/sprites/player/player_0_stand.png"));
			textureMap.put("player_0_move1", new Texture("resources/sprites/player/player_0_move1.png"));
			textureMap.put("player_0_move2", new Texture("resources/sprites/player/player_0_move2.png"));
			textureMap.put("player_1_stand", new Texture("resources/sprites/player/player_1_stand.png"));
			textureMap.put("player_1_move1", new Texture("resources/sprites/player/player_1_move1.png"));
			textureMap.put("player_1_move2", new Texture("resources/sprites/player/player_1_move2.png"));
			textureMap.put("player_2_stand", new Texture("resources/sprites/player/player_2_stand.png"));
			textureMap.put("player_2_move1", new Texture("resources/sprites/player/player_2_move1.png"));
			textureMap.put("player_2_move2", new Texture("resources/sprites/player/player_2_move2.png"));
			textureMap.put("player_3_stand", new Texture("resources/sprites/player/player_3_stand.png"));
			textureMap.put("player_3_move1", new Texture("resources/sprites/player/player_3_move1.png"));
			textureMap.put("player_3_move2", new Texture("resources/sprites/player/player_3_move2.png"));
			textureMap.put("player_4_stand", new Texture("resources/sprites/player/player_4_stand.png"));
			textureMap.put("player_4_move1", new Texture("resources/sprites/player/player_4_move1.png"));
			textureMap.put("player_4_move2", new Texture("resources/sprites/player/player_4_move2.png"));
			textureMap.put("player_5_stand", new Texture("resources/sprites/player/player_5_stand.png"));
			textureMap.put("player_5_move1", new Texture("resources/sprites/player/player_5_move1.png"));
			textureMap.put("player_5_move2", new Texture("resources/sprites/player/player_5_move2.png"));
			textureMap.put("player_6_stand", new Texture("resources/sprites/player/player_6_stand.png"));
			textureMap.put("player_6_move1", new Texture("resources/sprites/player/player_6_move1.png"));
			textureMap.put("player_6_move2", new Texture("resources/sprites/player/player_6_move2.png"));
			textureMap.put("player_7_stand", new Texture("resources/sprites/player/player_7_stand.png"));
			textureMap.put("player_7_move1", new Texture("resources/sprites/player/player_7_move1.png"));
			textureMap.put("player_7_move2", new Texture("resources/sprites/player/player_7_move2.png"));

			//Multiplayer in game sprites
			//player1 "Craig"
			textureMap.put("player1_0_stand", new Texture("resources/sprites/player/player1-standing-7.png"));
			textureMap.put("player1_0_move1", new Texture("resources/sprites/player/player1-walking-7a.png"));
			textureMap.put("player1_0_move2", new Texture("resources/sprites/player/player1-walking-7b.png"));
			textureMap.put("player1_1_stand", new Texture("resources/sprites/player/player1-standing-1.png"));
			textureMap.put("player1_1_move1", new Texture("resources/sprites/player/player1-walking-1a.png"));
			textureMap.put("player1_1_move2", new Texture("resources/sprites/player/player1-walking-1b.png"));
			textureMap.put("player1_2_stand", new Texture("resources/sprites/player/player1-standing-5.png"));
			textureMap.put("player1_2_move1", new Texture("resources/sprites/player/player1-walking-5a.png"));
			textureMap.put("player1_2_move2", new Texture("resources/sprites/player/player1-walking-5b.png"));
			textureMap.put("player1_3_stand", new Texture("resources/sprites/player/player1-standing-4.png"));
			textureMap.put("player1_3_move1", new Texture("resources/sprites/player/player1-walking-4a.png"));
			textureMap.put("player1_3_move2", new Texture("resources/sprites/player/player1-walking-4b.png"));
			textureMap.put("player1_4_stand", new Texture("resources/sprites/player/player1-standing-8.png"));
			textureMap.put("player1_4_move1", new Texture("resources/sprites/player/player1-walking-8a.png"));
			textureMap.put("player1_4_move2", new Texture("resources/sprites/player/player1-walking-8b.png"));
			textureMap.put("player1_5_stand", new Texture("resources/sprites/player/player1-standing-2.png"));
			textureMap.put("player1_5_move1", new Texture("resources/sprites/player/player1-walking-2a.png"));
			textureMap.put("player1_5_move2", new Texture("resources/sprites/player/player1-walking-2b.png"));
			textureMap.put("player1_6_stand", new Texture("resources/sprites/player/player1-standing-6.png"));
			textureMap.put("player1_6_move1", new Texture("resources/sprites/player/player1-walking-6a.png"));
			textureMap.put("player1_6_move2", new Texture("resources/sprites/player/player1-walking-6b.png"));
			textureMap.put("player1_7_stand", new Texture("resources/sprites/player/player1-standing-3.png"));
			textureMap.put("player1_7_move1", new Texture("resources/sprites/player/player1-walking-3a.png"));
			textureMap.put("player1_7_move2", new Texture("resources/sprites/player/player1-walking-3b.png"));

			//player 2 "Moe"
			textureMap.put("player2_0_stand", new Texture("resources/sprites/player/player2-standing-7.png"));
			textureMap.put("player2_0_move1", new Texture("resources/sprites/player/player2-walking-7a.png"));
			textureMap.put("player2_0_move2", new Texture("resources/sprites/player/player2-walking-7b.png"));
			textureMap.put("player2_1_stand", new Texture("resources/sprites/player/player2-standing-1.png"));
			textureMap.put("player2_1_move1", new Texture("resources/sprites/player/player2-walking-1a.png"));
			textureMap.put("player2_1_move2", new Texture("resources/sprites/player/player2-walking-1b.png"));
			textureMap.put("player2_2_stand", new Texture("resources/sprites/player/player2-standing-5.png"));
			textureMap.put("player2_2_move1", new Texture("resources/sprites/player/player2-walking-5a.png"));
			textureMap.put("player2_2_move2", new Texture("resources/sprites/player/player2-walking-5b.png"));
			textureMap.put("player2_3_stand", new Texture("resources/sprites/player/player2-standing-4.png"));
			textureMap.put("player2_3_move1", new Texture("resources/sprites/player/player2-walking-4a.png"));
			textureMap.put("player2_3_move2", new Texture("resources/sprites/player/player2-walking-4b.png"));
			textureMap.put("player2_4_stand", new Texture("resources/sprites/player/player2-standing-8.png"));
			textureMap.put("player2_4_move2", new Texture("resources/sprites/player/player2-walking-8b.png"));
			textureMap.put("player2_4_move1", new Texture("resources/sprites/player/player2-walking-8a.png"));
			textureMap.put("player2_5_stand", new Texture("resources/sprites/player/player2-standing-2.png"));
			textureMap.put("player2_5_move1", new Texture("resources/sprites/player/player2-walking-2a.png"));
			textureMap.put("player2_5_move2", new Texture("resources/sprites/player/player2-walking-2b.png"));
			textureMap.put("player2_6_stand", new Texture("resources/sprites/player/player2-standing-6.png"));
			textureMap.put("player2_6_move1", new Texture("resources/sprites/player/player2-walking-6a.png"));
			textureMap.put("player2_6_move2", new Texture("resources/sprites/player/player2-walking-6b.png"));
			textureMap.put("player2_7_stand", new Texture("resources/sprites/player/player2-standing-3.png"));
			textureMap.put("player2_7_move1", new Texture("resources/sprites/player/player2-walking-3a.png"));
			textureMap.put("player2_7_move2", new Texture("resources/sprites/player/player2-walking-3b.png"));
			//player3
			textureMap.put("player3_0_stand", new Texture("resources/sprites/player/player3-standing-7.png"));
			textureMap.put("player3_0_move1", new Texture("resources/sprites/player/player3-walking-7a.png"));
			textureMap.put("player3_0_move2", new Texture("resources/sprites/player/player3-walking-7b.png"));
			textureMap.put("player3_1_stand", new Texture("resources/sprites/player/player3-standing-1.png"));
			textureMap.put("player3_1_move1", new Texture("resources/sprites/player/player3-walking-1a.png"));
			textureMap.put("player3_1_move2", new Texture("resources/sprites/player/player3-walking-1b.png"));
			textureMap.put("player3_2_stand", new Texture("resources/sprites/player/player3-standing-5.png"));
			textureMap.put("player3_2_move1", new Texture("resources/sprites/player/player3-walking-5a.png"));
			textureMap.put("player3_2_move2", new Texture("resources/sprites/player/player3-walking-5b.png"));
			textureMap.put("player3_3_stand", new Texture("resources/sprites/player/player3-standing-4.png"));
			textureMap.put("player3_3_move1", new Texture("resources/sprites/player/player3-walking-4a.png"));
			textureMap.put("player3_3_move2", new Texture("resources/sprites/player/player3-walking-4b.png"));
			textureMap.put("player3_4_stand", new Texture("resources/sprites/player/player3-standing-8.png"));
			textureMap.put("player3_4_move2", new Texture("resources/sprites/player/player3-walking-8b.png"));
			textureMap.put("player3_4_move1", new Texture("resources/sprites/player/player3-walking-8a.png"));
			textureMap.put("player3_5_stand", new Texture("resources/sprites/player/player3-standing-2.png"));
			textureMap.put("player3_5_move1", new Texture("resources/sprites/player/player3-walking-2a.png"));
			textureMap.put("player3_5_move2", new Texture("resources/sprites/player/player3-walking-2b.png"));
			textureMap.put("player3_6_stand", new Texture("resources/sprites/player/player3-standing-6.png"));
			textureMap.put("player3_6_move1", new Texture("resources/sprites/player/player3-walking-6a.png"));
			textureMap.put("player3_6_move2", new Texture("resources/sprites/player/player3-walking-6b.png"));
			textureMap.put("player3_7_stand", new Texture("resources/sprites/player/player3-standing-3.png"));
			textureMap.put("player3_7_move1", new Texture("resources/sprites/player/player3-walking-3a.png"));
			textureMap.put("player3_7_move2", new Texture("resources/sprites/player/player3-walking-3b.png"));

			//player4
			textureMap.put("player4_0_stand", new Texture("resources/sprites/player/player4-standing-7.png"));
			textureMap.put("player4_0_move1", new Texture("resources/sprites/player/player4-walking-7a.png"));
			textureMap.put("player4_0_move2", new Texture("resources/sprites/player/player4-walking-7b.png"));
			textureMap.put("player4_1_stand", new Texture("resources/sprites/player/player4-standing-1.png"));
			textureMap.put("player4_1_move1", new Texture("resources/sprites/player/player4-walking-1a.png"));
			textureMap.put("player4_1_move2", new Texture("resources/sprites/player/player4-walking-1b.png"));
			textureMap.put("player4_2_stand", new Texture("resources/sprites/player/player4-standing-5.png"));
			textureMap.put("player4_2_move1", new Texture("resources/sprites/player/player4-walking-5a.png"));
			textureMap.put("player4_2_move2", new Texture("resources/sprites/player/player4-walking-5b.png"));
			textureMap.put("player4_3_stand", new Texture("resources/sprites/player/player4-standing-4.png"));
			textureMap.put("player4_3_move1", new Texture("resources/sprites/player/player4-walking-4a.png"));
			textureMap.put("player4_3_move2", new Texture("resources/sprites/player/player4-walking-4b.png"));
			textureMap.put("player4_4_stand", new Texture("resources/sprites/player/player4-standing-8.png"));
			textureMap.put("player4_4_move2", new Texture("resources/sprites/player/player4-walking-8b.png"));
			textureMap.put("player4_4_move1", new Texture("resources/sprites/player/player4-walking-8a.png"));
			textureMap.put("player4_5_stand", new Texture("resources/sprites/player/player4-standing-2.png"));
			textureMap.put("player4_5_move1", new Texture("resources/sprites/player/player4-walking-2a.png"));
			textureMap.put("player4_5_move2", new Texture("resources/sprites/player/player4-walking-2b.png"));
			textureMap.put("player4_6_stand", new Texture("resources/sprites/player/player4-standing-6.png"));
			textureMap.put("player4_6_move1", new Texture("resources/sprites/player/player4-walking-6a.png"));
			textureMap.put("player4_6_move2", new Texture("resources/sprites/player/player4-walking-6b.png"));
			textureMap.put("player4_7_stand", new Texture("resources/sprites/player/player4-standing-3.png"));
			textureMap.put("player4_7_move1", new Texture("resources/sprites/player/player4-walking-3a.png"));
			textureMap.put("player4_7_move2", new Texture("resources/sprites/player/player4-walking-3b.png"));

			//player5
			textureMap.put("player5_0_stand", new Texture("resources/sprites/player/player5-standing-7.png"));
			textureMap.put("player5_0_move1", new Texture("resources/sprites/player/player5-walking-7a.png"));
			textureMap.put("player5_0_move2", new Texture("resources/sprites/player/player5-walking-7b.png"));
			textureMap.put("player5_1_stand", new Texture("resources/sprites/player/player5-standing-1.png"));
			textureMap.put("player5_1_move1", new Texture("resources/sprites/player/player5-walking-1a.png"));
			textureMap.put("player5_1_move2", new Texture("resources/sprites/player/player5-walking-1b.png"));
			textureMap.put("player5_2_stand", new Texture("resources/sprites/player/player5-standing-5.png"));
			textureMap.put("player5_2_move1", new Texture("resources/sprites/player/player5-walking-5a.png"));
			textureMap.put("player5_2_move2", new Texture("resources/sprites/player/player5-walking-5b.png"));
			textureMap.put("player5_3_stand", new Texture("resources/sprites/player/player5-standing-4.png"));
			textureMap.put("player5_3_move1", new Texture("resources/sprites/player/player5-walking-4a.png"));
			textureMap.put("player5_3_move2", new Texture("resources/sprites/player/player5-walking-4b.png"));
			textureMap.put("player5_4_stand", new Texture("resources/sprites/player/player5-standing-8.png"));
			textureMap.put("player5_4_move2", new Texture("resources/sprites/player/player5-walking-8b.png"));
			textureMap.put("player5_4_move1", new Texture("resources/sprites/player/player5-walking-8a.png"));
			textureMap.put("player5_5_stand", new Texture("resources/sprites/player/player5-standing-2.png"));
			textureMap.put("player5_5_move1", new Texture("resources/sprites/player/player5-walking-2a.png"));
			textureMap.put("player5_5_move2", new Texture("resources/sprites/player/player5-walking-2b.png"));
			textureMap.put("player5_6_stand", new Texture("resources/sprites/player/player5-standing-6.png"));
			textureMap.put("player5_6_move1", new Texture("resources/sprites/player/player5-walking-6a.png"));
			textureMap.put("player5_6_move2", new Texture("resources/sprites/player/player5-walking-6b.png"));
			textureMap.put("player5_7_stand", new Texture("resources/sprites/player/player5-standing-3.png"));
			textureMap.put("player5_7_move1", new Texture("resources/sprites/player/player5-walking-3a.png"));
			textureMap.put("player5_7_move2", new Texture("resources/sprites/player/player5-walking-3b.png"));

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
			textureMap.put("menu_multiplayer_button", new Texture("resources/ui/main_menu/menu_multiplayer_button.png"));
			textureMap.put("menu_go_button", new Texture("resources/ui/main_menu/menu_go_button.png"));
			textureMap.put("menu_host_button", new Texture("resources/ui/main_menu/menu_host_button.png"));
			textureMap.put("menu_back_button", new Texture("resources/ui/main_menu/menu_back_button.png"));
			textureMap.put("menu_instructions_button", new Texture("resources/ui/main_menu/menu_instructions_button.png"));
			textureMap.put("instructions_back_button", new Texture("resources/ui/main_menu/instructions_back_button.png"));
			textureMap.put("instructions_text", new Texture("resources/ui/main_menu/instructions_text.png"));
			textureMap.put("instructions_title", new Texture("resources/ui/main_menu/instructions_title.png"));
			textureMap.put("toggle_mute_button", new Texture("resources/ui/main_menu/mute_button.png"));
			textureMap.put("toggle_shaders_button", new Texture("resources/ui/main_menu/shaders_button.png"));
			textureMap.put("toggle_mute_button_greyscale", new Texture("resources/ui/main_menu/mute_button_greyscale.png"));
			textureMap.put("toggle_shaders_button_greyscale", new Texture("resources/ui/main_menu/shaders_button_greyscale.png"));
			textureMap.put("custom_menu", new Texture("resources/ui/main_menu/custom_menu_background.png"));

			// Texture for the Radial Menu
			textureMap.put("sunflower_btn", new Texture("resources/ui/radial_menu/sunflower.png"));
			textureMap.put("fire_btn", new Texture("resources/ui/radial_menu/fire.png"));
			textureMap.put("explosive_btn", new Texture("resources/ui/radial_menu/explosive.png"));
			textureMap.put("water_btn", new Texture("resources/ui/radial_menu/water.png"));
			textureMap.put("ice_btn", new Texture("resources/ui/radial_menu/ice.png"));
			textureMap.put("grass_btn", new Texture("resources/ui/radial_menu/grass.png"));
			textureMap.put("bugspray_btn", new Texture("resources/ui/radial_menu/bugspray.png"));
			textureMap.put("fertiliser_btn", new Texture("resources/ui/radial_menu/fertiliser.png"));
			textureMap.put("menuClose", new Texture("resources/ui/radial_menu/menu_close.png"));
			textureMap.put("radialOutline", new Texture("resources/ui/radial_menu/radialOutline.png"));
			textureMap.put("machineGun", new Texture("resources/ui/radial_menu/machine_gun.png"));
			textureMap.put("shotgun", new Texture("resources/ui/radial_menu/shotgun.png"));
			textureMap.put("scatterGun", new Texture("resources/ui/radial_menu/scatter_gun.png"));
			textureMap.put("starfall", new Texture("resources/ui/radial_menu/starfall.png"));
			textureMap.put("key", new Texture("resources/ui/radial_menu/key_button.png"));
			textureMap.put("magicMushroom", new Texture("resources/ui/radial_menu/magicmushroom_button.png"));
			textureMap.put("smallMushroom", new Texture("resources/ui/radial_menu/smallmushroom_button.png"));
			textureMap.put("shovel_btn", new Texture("resources/ui/radial_menu/shovel_button.png"));
			textureMap.put("hoe_btn", new Texture("resources/ui/radial_menu/hoe_button.png"));
			textureMap.put("trowel_btn", new Texture("resources/ui/radial_menu/trowel_button.png"));
			textureMap.put("snag_btn", new Texture("resources/ui/radial_menu/sausage_button.png"));
			textureMap.put("sausage_btn", new Texture("resources/ui/radial_menu/hotdog_button.png"));

			//PlayerStatusHud
			textureMap.put("player_normal_border", new Texture("resources/ui/player_status_hud/" +
					"player_normal_border.png"));
			textureMap.put("health_bar_pixel", new Texture("resources/ui/player_status_hud/health_bar_pixel.png"));

			//Perks General
			textureMap.put("perk_border_inactive", new Texture("resources/ui/perks/perk_border_inactive.png"));
			textureMap.put("perk_border_maxed", new Texture("resources/ui/perks/perk_border_maxed.png"));
			textureMap.put("druid_title", new Texture("resources/ui/perks/druid_title.png"));
			textureMap.put("survivalist_title", new Texture("resources/ui/perks/survivalist_title.png"));
			textureMap.put("fungal_title", new Texture("resources/ui/perks/fungal_title.png"));


			//Perk Images
			textureMap.put("I_am_groot", new Texture("resources/ui/perks/I_am_groot.png"));
			textureMap.put("Splinter_is_coming", new Texture("resources/ui/perks/Splinter_is_coming.png"));
			textureMap.put("Full-Petal_Alchemist", new Texture("resources/ui/perks/Full-Petal_Alchemist.png"));
			textureMap.put("Guns_and_Roses", new Texture("resources/ui/perks/Guns_and_Roses.png"));
			textureMap.put("Run_Fungus,_Run!", new Texture("resources/ui/perks/Run_Fungus,_Run!.png"));
			textureMap.put("Holly_Moley", new Texture("resources/ui/perks/Holly_Moley.png"));
			textureMap.put("Kale-ra-te", new Texture("resources/ui/perks/Kale-ra-te.png"));
			textureMap.put("Thor-n", new Texture("resources/ui/perks/Thor-n.png"));
			textureMap.put("The_Fungal_Countdown", new Texture("resources/ui/perks/The_Fungal_Countdown.png"));
			textureMap.put("Whoa_Black_Betty,_Bramble-am", new Texture("resources/ui/perks/Whoa_Black_Betty,_Bramble-am.png"));
			textureMap.put("Last_But_Not_Yeast", new Texture("resources/ui/perks/Last_But_Not_Yeast.png"));
			textureMap.put("Saving_Graves", new Texture("resources/ui/perks/Saving_Graves.png"));
			textureMap.put("Fungicidal_Maniac", new Texture("resources/ui/perks/Fungicidal_Maniac.png"));

			//Shop textures
			textureMap.put("shop_title", new Texture("resources/ui/shop_ui/shop_title.png"));
			textureMap.put("buy_bag", new Texture("resources/ui/shop_ui/buy_bag.png"));
			textureMap.put("sell_bag", new Texture("resources/ui/shop_ui/sell_bag.png"));
			textureMap.put("shop_buy_button", new Texture("resources/ui/shop_ui/shop_buy.png"));
			textureMap.put("shop_sell_button", new Texture("resources/ui/shop_ui/shop_sell.png"));
			textureMap.put("player_title", new Texture("resources/ui/shop_ui/player_title.png"));
			textureMap.put("shop_inventory", new Texture("resources/ui/shop_ui/shop_inventory.png"));
			textureMap.put("item_background", new Texture("resources/ui/shop_ui/square.png"));
			textureMap.put("shop_exit", new Texture("resources/ui/shop_ui/shop_exit.png"));
			textureMap.put("selected", new Texture("resources/ui/shop_ui/selected.png"));
			textureMap.put("tooltip", new Texture("resources/ui/tooltip.png"));
			textureMap.put("error_shop", new Texture("resources/ui/shop_ui/error.png"));

			//World map textures
			textureMap.put("wm_blue_bg", new Texture("resources/worldmap/blue_background.png"));
			textureMap.put("wm_green_bg", new Texture("resources/worldmap/green_background.png"));
			textureMap.put("completed_node", new Texture("resources/worldmap/completed_node_small.png"));
			textureMap.put("discovered_node", new Texture("resources/worldmap/discovered_node_small.png"));
			textureMap.put("fungi_node", new Texture("resources/worldmap/fungi_node_small.png"));
			textureMap.put("safe_node", new Texture("resources/worldmap/safe_node_small.png"));
			textureMap.put("forest_completed_node", new Texture("resources/worldmap/forest_completed_node_small.png"));
			textureMap.put("forest_discovered_node",
					new Texture("resources/worldmap/forest_discovered_node_small.png"));
			textureMap.put("forest_boss_node", new Texture("resources/worldmap/forest_boss_node_small.png"));
			textureMap.put("forest_safe_node", new Texture("resources/worldmap/forest_safe_node_small.png"));
			textureMap.put("waste_completed_node", new Texture("resources/worldmap/waste_completed_node_small.png"));
			textureMap.put("waste_discovered_node", new Texture("resources/worldmap/waste_discovered_node_small.png"));
			textureMap.put("waste_boss_node", new Texture("resources/worldmap/waste_boss_node_small.png"));
			textureMap.put("waste_safe_node", new Texture("resources/worldmap/waste_safe_node_small.png"));
			textureMap.put("black_px", new Texture("resources/worldmap/black_px.png"));
			textureMap.put("ws_forest", new Texture("resources/worldmap/ws_forest.png"));
			textureMap.put("ws_fungi", new Texture("resources/worldmap/ws_fungi.png"));
			textureMap.put("ws_urban", new Texture("resources/worldmap/ws_urban.png"));
			textureMap.put("ws_forest_locked", new Texture("resources/worldmap/ws_forest_locked.png"));
			textureMap.put("ws_fungi_locked", new Texture("resources/worldmap/ws_fungi_locked.png"));
			textureMap.put("ws_forest_completed", new Texture("resources/worldmap/ws_forest_completed.png"));
			textureMap.put("ws_fungi_completed", new Texture("resources/worldmap/ws_fungi_completed.png"));
			textureMap.put("ws_urban_completed", new Texture("resources/worldmap/ws_urban_completed.png"));
			textureMap.put("ws_purp_bg", new Texture("resources/worldmap/ws_purp_background.png"));
			textureMap.put("player_map", new Texture("resources/worldmap/player_map.png"));
			textureMap.put("ws_main_bg", new Texture("resources/worldmap/bg_world.png"));
			textureMap.put("wm_urban_bg", new Texture("resources/worldmap/bg_urban.png"));
			textureMap.put("wm_forest_bg", new Texture("resources/worldmap/bg_forest.png"));
			textureMap.put("wm_waste_bg", new Texture("resources/worldmap/bg_waste.png"));
			textureMap.put("ws_blackout_bg", new Texture("resources/worldmap/bg_blackout.png"));

			//Item textures
			textureMap.put("red_potion", new Texture("resources/sprites/potions/potion_round_red.png"));
			textureMap.put("green_potion", new Texture("resources/sprites/potions/potion_round_green.png"));
			textureMap.put("purple_potion", new Texture("resources/sprites/potions/potion_round_purple.png"));
			textureMap.put("blue_potion", new Texture("resources/sprites/potions/potion_round_blue.png"));
			textureMap.put("magic_mushroom", new Texture("resources/sprites/mushrooms/mushroom_fully_grown.png"));
			textureMap.put("small_mushroom", new Texture("resources/sprites/mushrooms/mushroom_half_grown.png"));
			textureMap.put("seed", new Texture("resources/placeholderassets/placeholderseed.png"));
			textureMap.put("bunnings_snag", new Texture("resources/sprites/food/sausage.png"));
			textureMap.put("bunnings_snag_and_bread", new Texture("resources/sprites/food/hotdog.png"));

			//Multiplayer UI Textures
			//Multiplayer Lobby
			textureMap.put("chat_background", new Texture("resources/ui/multiplayer_ui/chatbar.png"));
			textureMap.put("multi_menu_background", new Texture("resources/ui/multiplayer_ui/menu_background.png"));
			textureMap.put("menu_add_button", new Texture("resources/ui/multiplayer_ui/menu_add_button.png"));
			textureMap.put("lobby_title", new Texture("resources/ui/multiplayer_ui/lobby_title.png"));
			textureMap.put("lobby_start_button", new Texture("resources/ui/multiplayer_ui/lobby_start_button.png"));
			textureMap.put("lobby_send_button", new Texture("resources/ui/multiplayer_ui/lobby_send_button.png"));
			textureMap.put("lobby_image_frame", new Texture("resources/ui/multiplayer_ui/lobby_image_frame.png"));
			textureMap.put("lobby_back_button", new Texture("resources/ui/multiplayer_ui/lobby_back_button.png"));
			textureMap.put("lobby_separator", new Texture("resources/ui/multiplayer_ui/multiplayer_separator.png"));
			//Server Browser
			textureMap.put("server_host_button", new Texture("resources/ui/multiplayer_ui/menu_host_button.png"));
			textureMap.put("server_join_button", new Texture("resources/ui/multiplayer_ui/menu_join_button.png"));
			textureMap.put("server_refresh_button", new Texture("resources/ui/multiplayer_ui/menu_refresh_button.png"));
			textureMap.put("multiplayer_title", new Texture("resources/ui/multiplayer_ui/multiplayer_title.png"));
			textureMap.put("transparentUI", new Texture("resources/ui/multiplayer_ui/transparentUI.png"));
			textureMap.put("MPcharacter_title", new Texture("resources/ui/multiplayer_ui/MPcharacter_title.png"));
			textureMap.put("player1", new Texture("resources/ui/multiplayer_ui/player1.png"));
			textureMap.put("player1head", new Texture("resources/ui/multiplayer_ui/player1head.png"));
			textureMap.put("player2", new Texture("resources/ui/multiplayer_ui/player2.png"));
			textureMap.put("player2head", new Texture("resources/ui/multiplayer_ui/player2head.png"));
			textureMap.put("player3", new Texture("resources/ui/multiplayer_ui/player3.png"));
			textureMap.put("player3head", new Texture("resources/ui/multiplayer_ui/player3head.png"));
			textureMap.put("player4", new Texture("resources/ui/multiplayer_ui/player4.png"));
			textureMap.put("player4head", new Texture("resources/ui/multiplayer_ui/player4head.png"));
			textureMap.put("player5", new Texture("resources/ui/multiplayer_ui/player5.png"));
			textureMap.put("player5head", new Texture("resources/ui/multiplayer_ui/player5head.png"));

			//Conversation textures
			textureMap.put("conversation_context", new Texture("resources/ui/conversations/raw_background.png"));
			textureMap.put("npc_face", new Texture("resources/sprites/npc/Quest_1_SouthEast.png"));

			//Character creation textures
			textureMap.put("ccMale1", new Texture("resources/sprites/player/character_creation/male1.png"));
			textureMap.put("ccMale2", new Texture("resources/sprites/player/character_creation/male2.png"));
			textureMap.put("ccMale3", new Texture("resources/sprites/player/character_creation/male3.png"));
			textureMap.put("ccFemale1", new Texture("resources/sprites/player/character_creation/female1.png"));
			textureMap.put("ccFemale2", new Texture("resources/sprites/player/character_creation/female2.png"));
			textureMap.put("ccFemale3", new Texture("resources/sprites/player/character_creation/female3.png"));
			textureMap.put("ccWindow_Background_White", new Texture("resources/ui/character_creation/window_background_white.png"));
			textureMap.put("ccWindow_BorderSmaller_White", new Texture("resources/ui/character_creation/borderSmaller.png"));
			textureMap.put("ccWindow_Border_White", new Texture("resources/ui/character_creation/border.png"));
            textureMap.put("ccTitle", new Texture("resources/ui/character_creation/heading_title.png"));
            textureMap.put("ccAttributes", new Texture("resources/ui/character_creation/heading_attributes.png"));
            textureMap.put("ccSkills", new Texture("resources/ui/character_creation/heading_skills.png"));
            textureMap.put("ccStats", new Texture("resources/ui/character_creation/heading_stats.png"));
            textureMap.put("ccCharacter", new Texture("resources/ui/character_creation/heading_character.png"));
            textureMap.put("ccPreview", new Texture("resources/ui/character_creation/heading_preview.png"));
            textureMap.put("ccDone", new Texture("resources/ui/character_creation/heading_done.png"));
            
            // Death context textures
            textureMap.put("you_died", new Texture("resources/ui/death/you_died.png"));
            textureMap.put("deadman", new Texture("resources/ui/death/deadman.png"));

		} catch (GdxRuntimeException e) {
			throw new ResourceLoadException(e);
		}
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