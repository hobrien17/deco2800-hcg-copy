package com.deco2800.hcg.util;

import java.util.Optional;
import com.deco2800.hcg.entities.Tickable;

/**
 * A utility class for the calender. 
 * 
 * i.e. Mechanisms to store an accumulation of time in units 
 * such as days/months/years.
 * 
 * Created by Group 7 (Organic Java) on 14/08/17.
 */

public class CalendarManager implements Tickable {
	// current day
	private int day;
	
	// current hour
	private int hour;
	
	public CalendarManager(){
		day = 0;
		hour = 0;
	}
	
	@Override
	public void onTick(long gameTickCount) {
		if(++hour >= 24){
			day++;
			hour = 0;
		}
	}
	
	/**
	 * getter method for current day 
	 * @return current Day (int) 
	 */
	public int getDay(){
		return day;
	}
	
	/**
	 * getter method for current hour 
	 * @return current hour (int) 
	 */
	public int getHour(){
		return hour;
	}
	
	
}
