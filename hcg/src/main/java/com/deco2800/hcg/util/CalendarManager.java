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
	// In this case months will be 0-11 corresponding with Jan-Dec.
	private int initialMonth = 0;
	// days in months
	private int[] dayCount = {31,28,31,30,31,30,31,31,30,31,30,31};
	private int[] dayCountLeapYear = {31,29,31,30,31,30,31,31,30,31,30,31};

	public CalendarManager() {
		day = 0;
		hour = 0;
	}

	@Override
	public void onTick(long gameTickCount) {
		if (++hour >= 24) {
			day++;
			yearDay++;
			hour = 0;
		}
		
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
			if(!this.isLeapYear()) {
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
	 * getter method for 'proper' months past start month FLESH THIS OUT LIKE A
	 * SAUSAGE
	 * 
	 * @return months from start (int)
	 */
	public int getProperMonthsSinceStart() {
		int buffer = this.getDay();
		
		
		
		//I'm working on this dw.
		
		int currentWeek = this.getWeeksSinceStart();
		return currentWeek / 4;
	}

}
