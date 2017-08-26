package com.deco2800.hcg.managers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * An 'Observeable' class that is used to time in game events
 *
 * @author Team 7 (Organic Java)
 */

public class StopwatchManager extends Manager implements TickableManager {

	private int ticksElapsed;

	private int seconds;
	private int minutes;
	
	private int timedMinutes;

	

	/**
	 * Constructor: Initializes the stopwatch with no time limit.
	 */
	public StopwatchManager() {
		this.minutes = 0;
		this.seconds = 0;
		
		this.timedMinutes = 0;

		this.ticksElapsed = 0;
		}
	
	
	/**
	 * 
	 * Set the time limit for the stopwatch
	 * 
	 * @param timeLimit
	 */
	public void startTimer(int timeLimit) {
		this.timedMinutes = timeLimit;
		}
	
	/**
	 * 
	 * Removes the time limit from the stopwatch
	 * 
	 */
	public void stopTimer() {
		this.timedMinutes = 0;
		}
	
	/**
	 * 
	 * Resets the stopwatch
	 * 
	 */
	public void resetStopwatch() {
		this.ticksElapsed = 0;
		}
	
	/**
	 * 
	 * Gets the current time of the stopwatch
	 * 
	 * Returns a float of the current minute
	 * 
	 */
	public float getStopwatchTime() {
		float result = this.minutes;
		float secondsFraction = this.seconds/60f;
		return result + secondsFraction;
		}


	/**
	 * Handles incrementing time on tick event.
	 *
	 * @param gameTickCount
	 *            of all game ticks so far.
	 */
	public void onTick(long gameTickCount) {

		
		this.ticksElapsed++;
		
		// as a minute is 50 ticks
		
		float secondsDecimal = ticksElapsed%50;
		secondsDecimal = (secondsDecimal/50)*60;
		
		
		this.minutes = ticksElapsed/50;
		this.seconds = (int)(secondsDecimal);

		
		if(this.timedMinutes != 0 && this.minutes >= this.timedMinutes) {
			setChanged();
		}
		
		if (hasChanged()){
			notifyObservers();
		}
		clearChanged();
	}
	


}