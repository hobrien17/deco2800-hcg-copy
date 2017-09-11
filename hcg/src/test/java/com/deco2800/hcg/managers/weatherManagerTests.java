package com.deco2800.hcg.managers;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class weatherManagerTests {
	private WeatherManager allEffects;

	@Before
	public void setUp() {
		allEffects = new WeatherManager();
	}

	@Test
	public void allEffectsTest() {

		ArrayList<ParticleEffect> allEffectsList = allEffects.getAllEffects();
		Assert.assertEquals("length of allEffects not correct", 1,
				allEffectsList.size());
	}
}