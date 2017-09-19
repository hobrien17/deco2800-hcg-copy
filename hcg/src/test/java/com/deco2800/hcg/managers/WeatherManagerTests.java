package com.deco2800.hcg.managers;

import java.util.ArrayList;

import org.junit.*;
import com.deco2800.hcg.types.Weathers;

public class WeatherManagerTests {
	private WeatherManager wmInstantiateTests;
	private WeatherManager wmTurnOnTests;

	@Before
	public void setUp() {
		wmInstantiateTests = new WeatherManager();
		wmTurnOnTests = new WeatherManager();
	}

	// Instantiate State Tests
	@Test
	public void instantiateStateTest() {
		// invalid input cases (MathHelper.java not tested yet so testing here
		// for own sanity)

		Assert.assertEquals("No effects should be on when instantiated",
				new ArrayList<Weathers>(), wmInstantiateTests.getOnEffects());
	}

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
	}

}