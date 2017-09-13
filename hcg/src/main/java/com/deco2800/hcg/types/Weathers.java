package com.deco2800.hcg.types;

/* An enum for interpreting the current weather, and interacting with 
WeatherManager. */
public enum Weathers{
	NONE(0), RAIN(1), SNOW(2), DROUGHT(3), STORM(4), SANDSTORM(5);

	private final int value;

	private Weathers(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}