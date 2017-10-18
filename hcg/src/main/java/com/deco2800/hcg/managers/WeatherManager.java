package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import java.util.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worlds.World;


/**
 * A class to manage the game's internal system of weather. Weather can be set
 * using the methods in this class Import com.deco2800.hcg.types.Weathers as
 * well as this one to use the weather system.
 *
 * @author Team 7 (Organic Java)
 */

public class WeatherManager extends Manager {

	SoundManager soundManager;
	ShaderManager shaderManager;
	ParticleEffect weather;
	ParticleEffectActor weatherActor;


	// onEffects: a list of effects that are currently on in the game.
	ArrayList<Weathers> onEffects;

	/**
	 * Constructor for weather manager
	 */
	public WeatherManager() {
		soundManager = (SoundManager) GameManager.get()
				.getManager(SoundManager.class);
		shaderManager = (ShaderManager) GameManager.get().getManager(ShaderManager.class);

		onEffects = new ArrayList<Weathers>();
		weather = new ParticleEffect();
		weather.start();
		weatherActor = new ParticleEffectActor();
		weatherActor.add(weather, true);
	}

	/**
	 * Sets up visuals given a valid .p file for a weather effect.
	 * 
	 * @param fileName
	 *            filename of particle file image in "resources/particles/"
	 * 
	 * @require fileName refers to a .p file && weather has been declared &&
	 *          filename is in the path"resources/particles/"
	 */
	private void setUp(String fileName) {
		weather.load(Gdx.files.internal("resources/particles/" + fileName),
				Gdx.files.internal("resources/particles/"));
		this.resize();
	}

	/**
	 * Resize the current weather effect to fit the current screen.
	 */
	public void resize() {
		for (int emitter = 0; emitter < weather.getEmitters().size; emitter++) {
			World world = GameManager.get().getWorld();

			int width = 100; // default values in case world == null
			int height = 100; // default values in case world == null

			if (world == null) {
				System.out.println("woops");
			} else {
				width = (world.getWidth());
				height = (world.getLength());
				System.out.println(width); // in case we're curious
				System.out.println(height);
			}

			ParticleEmitter newEmitter = weather.getEmitters()
					.get(emitter);
			//newEmitter.setPosition(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
			newEmitter.setPosition(0,0); // I figure the world map probably gets put at 0,0 right?

			// idk what these do but MAGIC NUMBERS EVERYWHERE TAKE THAT JOEL FENWICK >:D
			newEmitter.getSpawnHeight().setLowMax(3000);
			newEmitter.getSpawnHeight().setLowMin(1000);
			newEmitter.getSpawnWidth().setLowMax(2000);
			newEmitter.getSpawnWidth().setLowMin(4000);

			// how to set min/max particles: DO YOUR BEST
			// max ~ 6000 is light, max ~ 12000 is heavy (this is right anymore idk??)

			// these values are (really) arbitrary and need to be tweaked
			newEmitter.setMaxParticleCount(height*400 + 10000);
			newEmitter.setMinParticleCount(height*400/6 + 8000);

			// just a thought what is the size of each tile?!?!?! because i feel like world.getWidth
			// gives you the tile width? which would make all sporadic math redundant
			// whats the difference between min and max
			newEmitter.getSpawnWidth().setHighMax(width*400);
			newEmitter.getSpawnWidth().setHighMin(width*400/6);

			// whats the difference between min and max
			newEmitter.getSpawnHeight().setHighMax(height*400);
			newEmitter.getSpawnHeight().setHighMin(height*400/6);



		}
	}

	/**
	 * Changes weather to given weather effect. If the provided error type is
	 * not valid, nothing happens.
	 * 
	 * @param weatherType:
	 *            int representation of desired weather type
	 */
	public void setWeather(Weathers weatherType) {
		if (onEffects.contains(weatherType)) {
			return;
		}
		ParticleEmitter emitter = new ParticleEmitter();
		// weather.getEmitters
		if (weatherType == Weathers.NONE){
			stopAllEffect();
			return;
		}
		switch (weatherType) {
		case RAIN:
			setUp("2dRain.p");
			shaderManager.setOvercast(0.3f);
			break;
		case SNOW:
			setUp("2dSnow.p");
			shaderManager.setOvercast(0.2f);
			break;
		case SANDSTORM:
			setUp("2dSandstorm.p");
			shaderManager.setOvercast(0f);
			break;
		case WIND:
			setUp("2dWind.p");
			shaderManager.setOvercast(0f);
			break;
		case DROUGHT:
			setUp("2dDrought.p");
			shaderManager.setOvercast(0f);
			break;
		case STORM:
			setUp("2dStorm.p");
			shaderManager.setOvercast(0.9f);
			break;
		}
		
		soundManager.ambientLoopSound(enumToSoundFile(weatherType));
		onEffects.add(weatherType);
	}

	/**
	 * Turns off all weather effects.
	 */
	public void stopAllEffect() {
		for (Weathers weatherType : onEffects) {
			soundManager.stopSound(enumToSoundFile(weatherType));
		}

		weather.dispose();
		onEffects.clear();
	}

	/**
	 * Turns off specific weather effect if turned on, else do nothing
	 * 
	 * @param weatherType:
	 *            weather type you would like to turn off
	 * 
	 * @ensure weatherManager.getOnEffects() does not contain weathers
	 */
	public void stopEffect(Weathers weatherType) {
		if (!onEffects.contains(weatherType)) {
			return;
		}

		soundManager.stopSound(enumToSoundFile(weatherType));
		onEffects.remove(weatherType);
		weather.dispose();
	}

	/**
	 * getter method for all current effects turned on.
	 * 
	 * @return effects: a list of integers representing the emitters currently
	 *         off and on in the game. E.g. index RAIN would be 1 if rain is
	 *         currently on, 0 otherwise.
	 */
	public ArrayList<Weathers> getOnEffects() {
		return onEffects;
	}

	/**
	 * Getter method for the particle effect actor that handles the front end
	 * weather gui.
	 * 
	 * @return particle effect actor that handles the front end weather gui
	 */
	public ParticleEffectActor getActor() {
		return weatherActor;
	}

	/**
	 * Helper method to get the sound file name relating to the weatherType
	 * 
	 * @param weatherType:
	 *            type of weather
	 * @return string format of the sound file corresponding to the weatherType
	 * 
	 * @require weatherType != None
	 * 
	 */
	private String enumToSoundFile(Weathers weatherType) {
		switch (weatherType) {
		case RAIN:
			return "weatherRain";
		case SNOW:
			return "weatherSnow";
		case SANDSTORM:
			return "weatherSandStorm";
		case WIND:
			return "weatherWind";
		case DROUGHT:
			return "weatherDrought";
		case STORM:
			return "weatherStorm";
		default:
			return "";
		}
	}
}
