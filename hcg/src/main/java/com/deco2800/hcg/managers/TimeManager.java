package com.deco2800.hcg.managers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.deco2800.hcg.util.MathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An 'Observer' class to manage the game's internal system of time. Time is initialised to
 * 01/01/2047 00:00:00 when the game is run and increments at the rate of 1 second per game tick
 * (approximately 1 minute real time is equal to 1 hour in the game). Time can be accessed
 * publicly but not changed, only paused and unpaused.
 *
 * @author Team 7 (Organic Java)
 */

public class TimeManager extends Manager implements TickableManager {

	private int day;
	private int month;
	private int year;

	private int seconds;
	private int minutes;
	private int hours;

	private Label timeLabel;
	private Label dateLabel;
	private int[] dayCount = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private boolean timePaused;
	private boolean isNight;

	static final Logger LOGGER =
			LoggerFactory.getLogger(TimeManager.class);

	/**
	 * Constructor: Initializes day to 01/01/2047 and elapsed time to 0 on
	 * startup.
	 */
	public TimeManager() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.day = 1;
		this.month = 1;
		this.year = 2047;
		this.isNight = true;
		this.timeLabel = null;
		this.dateLabel = null;
		this.timePaused = false;
	}

	/**
	 * Gets the year.
	 *
	 * @return Returns the year.
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * Gets the month.
	 *
	 * @return Returns the month.
	 */
	public int getMonth() {
		return this.month;
	}

	/**
	 * Gets the day.
	 * 
	 * @return Returns the day.
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * Gets the hours.
	 *
	 * @return Returns the hours.
	 */
	public int getHours() {
		return this.hours;
	}

	/**
	 * Gets the minutes.
	 *
	 * @return Returns the minutes.
	 */
	public int getMinutes() {
		return this.minutes;
	}

	/**
	 * Gets the seconds.
	 *
	 * @return Returns the seconds.
	 */
	public int getSeconds() {
		return this.seconds;
	}

	/**
	 * Setter method to change the time in the gui
	 * 
	 * @param label:
	 *            the text to change the time label in the gui
	 */
	public void setTimeLabel(Label label) {
		this.timeLabel = label;
	}

	/**
	 * Setter method to change the date in the gui
	 * 
	 * @param label:
	 *            the text to change the date label in the gui
	 */
	public void setDateLabel(Label label) {
		this.dateLabel = label;
	}

	/**
	 * Returns true iff the current year is a leap year, else false.
	 * 
	 * @return Returns a boolean to indicate whether the current year is a leap
	 *         year.
	 */
	public boolean isLeapYear(int year) {
		if (year % 400 == 0) {
			return true;
		} else if (year % 100 == 0) {
			return false;
		} else if (year % 4 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Increments the number of days by 1.
	 */
	public void nextDay() {
		if (this.month == 2 && this.day == 28 && this.isLeapYear(this.year)) {
			this.day++;
			return;
		}
		if (this.day >= dayCount[this.month - 1]) {
			this.day = 1;
			if (this.month == 12) {
				this.month = 1;
				this.year += 1;
			} else {
				this.month += 1;
			}
			return;
		}
		this.day += 1;
	}

	/**
	 * Increments the number of seconds by 1. If seconds are 60, resets seconds
	 * to 0, and increments minutes. If minutes are 60, resets minutes to 0, and
	 * increments hour. If hours are 24, resets hours to 0 and increments day.
	 */
	public void nextSecond() {
		setChanged();
		if (this.seconds != 59) {
			this.seconds++;
			return;
		}

		this.seconds = 0;
		if (this.minutes != 59) {
			this.minutes++;
			return;
		}

		this.minutes = 0;
		if (this.hours != 23) {
			this.hours++;
			// check if nighttime every hour
			checkNight();
			return;
		}

		this.hours = 0;
		this.nextDay();
	}

	/**
	 * Pause the time.
	 */
	public void pauseTime() {
		timePaused = true;
	}

	/**
	 * Unpause the time.
	 */
	public void unpauseTime() {
		timePaused = false;
	}

	/**
	 * Return true iff nighttime.
	 */
	public boolean isNight() {
		return isNight;
	}

	/**
	 * Updates internal boolean tracking day/night cycle.
	 *
	 * <p>Nighttime between 7:00pm and 5:00am.</p>
	 */
	private void checkNight() {
		if (this.hours > 18 || this.hours < 5) {
			isNight = true;
			return;
		}
		isNight = false;
	}

	/**
	 * Handles incrementing time on tick event.
	 *
	 * @param gameTickCount
	 *            of all game ticks so far.
	 */
	public void onTick(long gameTickCount) {
		if (timePaused) {
			return;
		}
		
		for(int i = 0; i < 30; i++) {
		    this.nextSecond();
		}	
		if (this.timeLabel != null) {
			this.timeLabel.setText(this.getTime());
		}
		if (this.dateLabel != null) {
			this.dateLabel.setText(this.getDate());
		}
		
		if (hasChanged()){
			notifyObservers();
		}
		clearChanged();
	}

	/**
	 * Returns a formatted date time string for the current date and time.
	 *
	 * @return Returns a string denoting the current date and time in the game.
	 */
	public String getDateTime() {
		return String.format("%02d/%02d/%02d %02d:%02d:%02d",
				this.day, this.month, this.year, this.hours, this.minutes,
				this.seconds);
	}

	/**
	 * Returns a formatted time string for the current game time (hh:mm).
	 *
	 * @return Returns a string denoting the current time in the game.
	 */
	public String getTime() {
		return String.format("%02d:%02d", this.hours, this.minutes);
	}

	/**
	 * Returns a formatted date string for the current game date.
	 *
	 * @return Returns a string denoting the current date in the game.
	 */
	public String getDate() {
		// My computer is not recognizing Strings in HashMaps so for now this 
		// is going to be a switch

		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December"};

		return String.format("%02d %s", this.day, months[this.getMonth()-1]);
	}

	/**
	 * Sets the date and time (FOR TESTING PURPOSES ONLY).
	 *
	 * @param second
	 * 			The second to be set (0 <= second < 60).
	 *
	 * @param minute
	 * 			The minute to be set (0 <= minute < 60).
	 *
	 * @param hour
	 * 			The hour to be set (0 <= hour < 24).
	 *
	 * @param day
	 * 			The day to be set (0 <= day <= 31).
	 *
	 * @param month
	 * 			The month to be set (0 <= month <= 12).
	 *
	 * @param year
	 * 			The year to be set (0 < year ).
	 */
	public void setDateTime(int second, int minute, int hour, int day, int month, int year) {

		// clamp inputs just in case they're not valid numbers
				this.seconds = MathHelper.clamp(second, 0, 59);
				this.minutes = MathHelper.clamp(minute, 0, 59);
				this.hours = MathHelper.clamp(hour, 0, 23);
				this.day = MathHelper.clamp(day, 1, 31);
				this.month = MathHelper.clamp(month, 1, 12);
				this.year = MathHelper.clamp(year, 1, 10000);
				checkNight();

	}

}