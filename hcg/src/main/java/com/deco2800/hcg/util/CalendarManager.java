package com.deco2800.hcg.util;

import java.util.ArrayList;
import java.util.Optional;
import com.deco2800.hcg.entities.Tickable;

/**
 * A utility class for the calendar.
 * 
 * i.e. Mechanisms to store an accumulation of time in units such as
 * days/months/years.
 * 
 * Created by Group 7 (Organic Java) on 14/08/17.
 */

public class CalendarManager implements Tickable {

	// Initialising variables

	// current day
	private int day;

	// current day in year
	private int yearDay;

	// current hour
	private int hour;

	// the following are for if we want to simulate 'proper' months and stuff.

	// initial year - make sure to confirm this with other teams.
	// I have set it to 2047 for now.
	private int initialYear = 2047;
	private int currentYear = initialYear;

	// initial month - same deal as initial year.
	// however, I would recommend leaving it at 0/Jan, to avoid
	// overly complex code
	// In this case months will be 0-11 corresponding with Jan-Dec.
	private int initialMonth = 0;
	private int currentMonth = initialMonth;
	// days in months
	private int[] dayCount = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private int[] dayCountLeapYear = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public CalendarManager() {
		day = 0;
		hour = 0;
	}

	@Override
	// at a tickrate of 50Hz, we might need to do some ~mathematics~ here?
	// cause if my understanding is correct, we'll end up going 50 hours a second
	// lol
	public void onTick(long gameTickCount) {
		if (++hour >= 24) {
			day++;
			yearDay++;
			hour = 0;
		}

		incrementCalendarMonths();
		incrementYear();

	}

	/*
	 * Helper method, checks if current year is a leap year
	 */
	private boolean isLeapYear() {
		if (currentYear % 4 == 0) {
			if (currentYear % 100 == 0) {
				if (currentYear % 400 == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/*
	 * Helper method, increments year with conditions
	 * 
	 * my code is quite spaghetti, sorry
	 * 
	 */
	private void incrementYear() {
		if (yearDay >= 365) {
			if (!this.isLeapYear()) {
				currentYear++;
				yearDay = 0;
			} else {
				if (yearDay > 365) {
					currentYear++;
					yearDay = 0;
				}
			}
		}
	}
	
	/*
	 * helper method to increment months
	 */
	private void incrementCalendarMonths() {
		int bufferDay = yearDay;
		int currentMonth = this.getCurrentCalendarMonth();
		int dayCounter = 0;
		
		if(this.isLeapYear()) {
			for (int i = 0; i < currentMonth; i++) {
				dayCounter = dayCounter + dayCountLeapYear[i];
			}
		} else {
			for (int i = 0; i < currentMonth; i++) {
				dayCounter = dayCounter + dayCount[i];
			}
		}
		
		if (bufferDay >= dayCounter) {
			currentMonth++;
		}	
	}
	

	/**
	 * getter method for current day
	 * 
	 * @return current Day (int)
	 */
	public int getDay() {
		return day;
	}

	/**
	 * getter method for current hour
	 * 
	 * @return current hour (int)
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * getter method for current year
	 * 
	 * @return current year (int)
	 */
	public int getYear() {
		return currentYear;
	}

	/**
	 * getter method for weeks since start A week is defined as 7 days (duh)
	 * 
	 * @return weeks from start (int)
	 */
	public int getWeeksSinceStart() {
		int currentDay = this.getDay();
		// integer div is wizardry I tell you
		return currentDay / 7;
	}

	/**
	 * getter method for 'simple' months past start month For this method, months
	 * are defined in chunks of 4 weeks, as opposed to 28/29/30/31 days, hence the
	 * simple moniker.
	 * 
	 * @return months from start (int)
	 */
	public int getSimpleMonthsSinceStart() {
		int currentWeek = this.getWeeksSinceStart();
		return currentWeek / 4;
	}

	/**
	 * getter method to get what calendar month it is
	 * 
	 * @return calendar month 0-11 (int), if an error has occurred, 999 will be
	 *         returned.
	 */
	public int getCurrentCalendarMonth() {

		// setting up buffer variables so I don't
		// accidentally f-up the proper ones
		int dayBuffer = yearDay;
		int dayCounter = -1; // to account for

		if (!this.isLeapYear()) {
			for (int i = 0; i < dayCountLeapYear.length; i++) {
				dayCounter = dayCounter + dayCountLeapYear[i];
				if (dayCounter >= dayBuffer) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < dayCount.length; i++) {
				dayCounter = dayCounter + dayCount[i];
				if (dayCounter >= dayBuffer) {
					return i;
				}
			}
		}

		return 999;

	}
	


}
