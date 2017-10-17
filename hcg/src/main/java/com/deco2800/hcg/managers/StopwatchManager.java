package com.deco2800.hcg.managers;

/**
 * An 'Observeable' class that is used to time in game events
 *
 * @author Team 7 (Organic Java)
 */

public class StopwatchManager extends Manager implements TickableManager {

	private int ticksElapsed;
	private boolean timerFinished;

	private int seconds;
	private int minutes;

	private float delay;
	private float timedMinutes;

	private float secondsFloat;
	private float minutesFloat;

	final double EPS = 0.000001;

	/**
	 * Constructor: Initializes the stopwatch with no time limit.
	 */
	public StopwatchManager() {
		this.minutes = 0;
		this.seconds = 0;

		this.timedMinutes = 0;
		this.timerFinished = false;
		this.ticksElapsed = 0;
	}

	/**
	 * Set the time limit for the stopwatch
	 * 
	 * @param timeLimit
	 *
	 */
	public void startTimer(int timeLimit) {
		this.delay = timeLimit;
		this.timedMinutes = timeLimit;
	}

	/**
	 * Set a more precise time limit for the stopwatch
	 *
	 * @param timeLimit
	 *
	 */
	public void startTimerFloat(float timeLimit) {
		this.delay = timeLimit;
		this.timedMinutes = timeLimit;
	}

	/**
	 * Removes the time limit from the stopwatch
	 */
	public void stopTimer() {
		this.timedMinutes = 0;
	}

	/**
	 * Resets the stopwatch
	 */
	public void resetStopwatch() {
		this.ticksElapsed = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.minutesFloat = 0;
		this.secondsFloat = 0;
		this.timerFinished = false;
	}

	/**
	 * Returns a boolean for the status of the timer (True if time has finished)
	 * 
	 * @return bool: true if the timer has finished, false if not or no timer
	 *         actually started.
	 */
	public boolean getStatus() {
		return timerFinished;
	}

	/**
	 * Gets the current time of the stopwatch
	 * 
	 * @return the float of the current minute
	 */
	public float getStopwatchTime() {
		float result = this.minutes;

		float secondsFraction = this.seconds / 60f;

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

		minutesFloat = ticksElapsed / 25f;
		secondsFloat = (ticksElapsed / 25f) * 60f;

		// converting 50 tick minutes to 60 seconds
		float secondsDecimal = ticksElapsed % 25;
		secondsDecimal = (secondsDecimal / 25) * 60;

		// as a minute is 25 ticks
		this.minutes = ticksElapsed / 25;
		this.seconds = (int) (secondsDecimal);

		if (((this.timedMinutes >= EPS) || (this.timedMinutes <= -EPS)) 
				&& this.minutesFloat > this.timedMinutes) {
			this.timerFinished = true;
			setChanged();
			timedMinutes += delay;
		}

		if (hasChanged()) {
			notifyObservers(getStopwatchTime());

		}
		clearChanged();
	}
}