package com.deco2800.hcg.managers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

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
	private int timeElapsed;

	private int seconds;
	private int minutes;
	private int hours;

	private Label timeLabel;
	private Label dateLabel;
	private int[] dayCount = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private boolean timePaused;
	private boolean isNight;

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
		this.timeElapsed = 0;
		this.timeLabel = null;
		this.dateLabel = null;
		this.timePaused = false; // this will need to be set to true when we have some sort of 'start screen' happening
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
	 * Gets the month.
	 * 
	 * @return Returns the month.
	 */
	public int getMonth() {
		return this.month;
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
	 * Sets the seconds.
	 * 
	 * @param seconds
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
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
	 * Gets the minutes.
	 * 
	 * @return Returns the minutes.
	 */
	public int getMinutes() {
		return this.minutes;
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
	 * Sets the elapsed (game) time.
	 * 
	 * @param timeElapsed
	 */
	private void setTimeElapsed(int timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	/**
	 * Gets the elapsed (game) time.
	 * 
	 * @return Returns the elapsed (game) time.
	 */
	public int getTimeElapsed() {
		return this.timeElapsed;
	}

	public void setTimeLabel(Label label) {
		this.timeLabel = label;
	}

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
		if (this.hours != 24) {
			this.hours++;
			// check if nighttime every hour
			checkNight();
			return;
		}

		this.hours = 0;
		this.nextDay();
	}

	/**
	 * Debugging method for printing date to stdout
	 */
	public void printDate() {
		String dateTime = String.format("%02d/%02d/%02d %02d:%02d:%02d",
				this.day, this.month, this.year, this.hours, this.minutes,
				this.seconds);
		System.out.println(dateTime);
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
	 * Nighttime between 7:00pm and 5:00am
	 */
	public void checkNight() {
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
		
		this.timeElapsed++;
		this.nextSecond();
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
		String month;
		switch (this.month) {
			case 1:
				month = "January";
				break;
			case 2:
				month = "February";
				break;
			case 3:
				month = "March";
				break;
			case 4:
				month = "April";
				break;
			case 5:
				month = "May";
				break;
			case 6:
				month = "June";
				break;
			case 7:
				month = "July";
				break;
			case 8:
				month = "August";
				break;
			case 9:
				month = "September";
				break;
			case 10:
				month = "October";
				break;
			case 11:
				month = "November";
				break;
			case 12:
				month = "December";
				break;
			default:
				// Indicates bug in TimeManager class
				System.out.println("Issue in TimeManager class");
				month = "Error";
		}

		return String.format("%02d %s", this.day, month);
	}

 }