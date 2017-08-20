package com.deco2800.hcg.managers;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TimeManager extends Manager {

	private int day;
	private int month;
	private int year;
	private int timeElapsed;

	private int seconds;
	private int minutes;
	private int hours;

	private Label label;
	private int[] dayCount = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	/**
	 * Constructor
	 * Initializes day to 01/01/2047 and elapsed time to 0 on startup.
	 */
	public TimeManager() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.hours = 0;
		this.day = 1;
		this.month = 1;
		this.year = 2047;
		this.timeElapsed = 0;
		this.label = null;
	}

	/**
	 * Sets the day.
	 * 
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
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
	 * Sets the month.
	 * 
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
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
	 * Sets the year.
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
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
		this.day = day;
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
	 * Sets the minutes.
	 * 
	 * @param minutes
	 */
	public void setMinutes(int minutes) {
		this.month = month;
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
	 * Sets the hours.
	 * 
	 * @param hours
	 */
	public void setHours(int hours) {
		this.hours = hours;
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
	public void setTimeElapsed(int timeElapsed) {
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

	public void setLabel(Label label) {
		this.label = label;
	}

	/**
	 * Returns true iff the current year is a leap year, else false.
	 * 
	 * @return Returns a boolean to indicate whether the current year is a 
	 * leap year.
	 */
	public boolean is_leap_year() {
		if (this.year % 400 == 0) {
			return true;
		}
		if (this.year % 100 == 0) {
			return false;
		}
		if (this.year % 4 == 0) {
			return true;
		}
		return false;
	}

	/** 
	 * Increments the number of days by 1.
	 */
	public void nextDay() {
		if (this.month == 2 && this.day == 28 && this.is_leap_year()) {
			this.day = 1;
			this.month = 3;
			return;
		}
		if (this.day == dayCount[this.month - 1]) {
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
	 * Increments the number of seconds by 1.
	 */
	public void nextSecond() {
		if (this.seconds != 59) {
			this.seconds += 1;
			return;
		}

		this.seconds = 0;
		if (this.minutes != 59) {
			this.minutes += 1;
			return;
		}

		this.minutes = 0;
		if (this.hours != 24) {
			this.hours += 1;
			return;
		}

		this.hours = 0;
		this.nextDay();
	}	

	/**
	 * Debugging method for printing date to stdout
	 */
	public void print_date() {
		String dateTime = String.format("%02d/%02d/%02d %02d:%02d:%02d", 
			this.day, this.month, this.year, this.hours, this.minutes, 
			this.seconds);
		System.out.println(dateTime);
	}

	/**
	 * Handles incrementing time on tick event.
	 *
	 * @param count of all game ticks so far.
	 */
	public void onTick(long gameTickCount) {
		this.timeElapsed++;
		this.nextSecond();
		if (this.label != null) {
			label.setText(this.getDateTime());
		}
	}

	/**
	 * Returns a formatted date time string for the current date and time.
	 *
	 * @return Returns a string denoting the current date and time in the game.
	 */
	public String getDateTime() {
		String dateTime = String.format("%02d/%02d/%02d %02d:%02d:%02d", 
			this.day, this.month, this.year, this.hours, this.minutes, 
			this.seconds);
		return dateTime;		
	}

}