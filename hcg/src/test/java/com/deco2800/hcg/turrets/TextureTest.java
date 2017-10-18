package com.deco2800.hcg.turrets;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.entities.turrets.AbstractTurret;
import com.deco2800.hcg.entities.turrets.ExplosiveTurret;
import com.deco2800.hcg.entities.turrets.FireTurret;
import com.deco2800.hcg.entities.turrets.IceTurret;
import com.deco2800.hcg.entities.turrets.SunflowerTurret;

public class TextureTest extends TurretBaseTest {
	
	private static Map<Class<? extends AbstractTurret>, String> textures;
	
	private static void setupTextures() {
		textures = new HashMap<>();
		textures.put(SunflowerTurret.class, "sunflower_corpse");
		textures.put(ExplosiveTurret.class, "cactus_corpse_03");
		textures.put(IceTurret.class, "ice_corpse_03");
		textures.put(FireTurret.class, "fire_corpse");
	}
	
	@Test
	public void testTextures() {
		setupTextures();
		
		corpse = new BasicCorpse(5, 5, 0);
		assertEquals("An empty corpse should have the empty corpse sprite", "corpse", corpse.getTexture());
		corpse.plantInside(new Seed(Seed.Type.SUNFLOWER));
		assertEquals("The corpse should have the sunflower sprite", "sunflower_corpse", corpse.getTexture());
		assertEquals("SUNFLOWER", corpse.getTurret().getName().toUpperCase());

		corpse = new BasicCorpse(5, 5, 0);
		corpse.plantInside(new Seed(Seed.Type.WATER));
		assertEquals("The corpse should have the water sprite", "water_corpse", corpse.getTexture());
		assertEquals("LILY", corpse.getTurret().getName().toUpperCase());

		corpse = new BasicCorpse(5, 5, 0);
		corpse.plantInside(new Seed(Seed.Type.GRASS));
		assertEquals("The corpse should have the grass sprite", "grass_corpse", corpse.getTexture());
		assertEquals("GRASS", corpse.getTurret().getName().toUpperCase());

		corpse = new BasicCorpse(5, 5, 0);
		corpse.plantInside(new Seed(Seed.Type.FIRE));
		assertEquals("The corpse should have the fire sprite", "fire_corpse", corpse.getTexture());
		assertEquals("INFERNO", corpse.getTurret().getName().toUpperCase());

		corpse = new BasicCorpse(5, 5, 0);
		corpse.plantInside(new Seed(Seed.Type.EXPLOSIVE));
		for (int i = 0; i < 3; i++) {
			assertEquals("The corpse should have the first cactus sprite", "cactus_corpse_01", corpse.getTexture());
			corpse.getTurret().update(sw, i);
		}
		assertEquals("The corpse should have the second cactus sprite", "cactus_corpse_02", corpse.getTexture());
		corpse.getTurret().update(sw, 3);
		assertEquals("The corpse should have the third cactus sprite", "cactus_corpse_03", corpse.getTexture());
		assertEquals("CACTUS", corpse.getTurret().getName().toUpperCase());

		corpse = new BasicCorpse(5, 5, 0);
		corpse.plantInside(new Seed(Seed.Type.ICE));
		for (int i = 0; i < 3; i++) {
			assertEquals("The corpse should have the first ice sprite", "ice_corpse_01", corpse.getTexture());
			corpse.getTurret().update(sw, i);
		}
		assertEquals("The corpse should have the second ice sprite", "ice_corpse_02", corpse.getTexture());
		corpse.getTurret().update(sw, 3);
		assertEquals("The corpse should have the third ice sprite", "ice_corpse_03", corpse.getTexture());
		assertEquals("ICE", corpse.getTurret().getName().toUpperCase());
	}
}
