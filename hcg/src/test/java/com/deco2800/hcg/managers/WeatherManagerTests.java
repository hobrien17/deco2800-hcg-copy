package com.deco2800.hcg.managers;

import java.util.ArrayList;

import org.junit.*;
import com.deco2800.hcg.types.Weathers;

public class WeatherManagerTests {
	private WeatherManager wmInstantiateTests;
	private WeatherManager wmTurnOnTests;
	private WeatherManager wmStopEffectTests;
	private WeatherManager wmStopAllEffectsTests;

	@Before
	public void setUp() {
		wmInstantiateTests = new WeatherManager();
		wmTurnOnTests = new WeatherManager();
		wmStopEffectTests = new WeatherManager();
		wmStopAllEffectsTests = new WeatherManager();
	}

	// Instantiate State Tests
	@Test
	public void instantiateStateTest() {
		// invalid input cases (MathHelper.java not tested yet so testing here
		// for own sanity)

		Assert.assertEquals("No effects should be on when instantiated",
				new ArrayList<Weathers>(), wmInstantiateTests.getOnEffects());
	}

	// turning on weather
	@Test
	public void turningOnWeather() {
		ArrayList<Weathers> testOnEffects = new ArrayList<Weathers>();

		for (Weathers weatherType : Weathers.values()) {
			testOnEffects.add(weatherType);

			wmTurnOnTests.setWeather(weatherType);

			Assert.assertEquals(
					"weather type " + weatherType + " not turned on correctly",
					testOnEffects, wmTurnOnTests.getOnEffects());
		}

		// Edge case: turning on weather that was already on
		wmTurnOnTests.setWeather(Weathers.RAIN);
		Assert.assertEquals("duplicate weather have been turned on",
				testOnEffects, wmTurnOnTests.getOnEffects());
	}

	@Test
	public void stopEffectTests() {
		ArrayList<Weathers> testOffEffects = new ArrayList<Weathers>();

		// turning on weather
		for (Weathers weatherType : Weathers.values()) {
			testOffEffects.add(weatherType);
			wmStopEffectTests.setWeather(weatherType);
		}

		// testing turning off
		for (Weathers weatherType : Weathers.values()) {
			testOffEffects.remove(weatherType);

			wmStopEffectTests.stopEffect(weatherType);

			Assert.assertEquals("weather type not turned off correctly",
					testOffEffects, wmStopEffectTests.getOnEffects());
		}

		Assert.assertTrue("No effects should be turned on",
				wmStopEffectTests.getOnEffects().isEmpty());
	}

	@Test
	public void stopAllEffectTest() {
		// turning on weather
		for (Weathers weatherType : Weathers.values()) {
			wmStopAllEffectsTests.setWeather(weatherType);
		}

		wmStopAllEffectsTests.stopAllEffect();
		
		Assert.assertTrue("duplicate weather have been turned on",
				wmStopAllEffectsTests.getOnEffects().isEmpty());
	}

}