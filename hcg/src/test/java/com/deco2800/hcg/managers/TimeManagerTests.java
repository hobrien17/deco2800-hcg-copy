package com.deco2800.hcg.managers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.junit.*;

public class TimeManagerTests {
	private TimeManager timeManager;
	private TimeManager timeManagerPauseTest;
	private TimeManager timeManagerNextDayTest;
	private TimeManager timeManagerNextSecondTest;
	private TimeManager timeManagerGetDateTest;

	@Before
	public void setUp() {
		timeManager = new TimeManager();

		// for (un)pauseTest
		timeManagerPauseTest = new TimeManager();

		// for nextDayTest
		timeManagerNextDayTest = new TimeManager();

		// for nextSecondTest
		timeManagerNextSecondTest = new TimeManager();

		// for getDateTest
		timeManagerGetDateTest = new TimeManager();
	}

	// setDateTime tests xxxxxx

	@Test (expected = IllegalArgumentException.class)
	public void setDateTimeInvalidSecond () {
		timeManager.setDateTime(-1, 0, 0, 1, 1, 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setDateTimeInvalidMinute () {
		timeManager.setDateTime(0, 62, 0, 1, 1, 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setDateTimeInvalidHour () {
		timeManager.setDateTime(34, 58, 29, 1, 1, 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setDateTimeInvalidDay () {
		timeManager.setDateTime(23, 23, 23, -4, 1, 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setDateTimeInvalidMonth () {
		timeManager.setDateTime(15, 35, 3, 5, 13, 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setDateTimeInvalidYear () {
		timeManager.setDateTime(0, 0, 0, 1, 1, -1);
	}

	@Test
	public void setDateTimeTest() {
		timeManager.setDateTime(0, 0, 0, 1, 1, 1);
		Assert.assertEquals("Second not correctly set.", 0, timeManager.getSeconds());
		Assert.assertEquals("Minute not correctly set.", 0, timeManager.getMinutes());
		Assert.assertEquals("Hour not correctly set.", 0, timeManager.getHours());
		Assert.assertEquals("Day not correctly set.", 1, timeManager.getDay());
		Assert.assertEquals("Month not correctly set.", 1, timeManager.getMonth());
		Assert.assertEquals("Year not correctly set.", 1, timeManager.getYear());
	}

	// getMonth
	@Test
	public void getMonthTest() {
		timeManager.setDateTime(31, 27, 11, 13, 1, 2000);
		Assert.assertEquals("getMonth not returning the correct month.", 1,
				timeManager.getMonth());
	}

	// getYear
	@Test
	public void getYearTest() {
		timeManager.setDateTime(31, 27, 11, 13, 1, 2000);
		Assert.assertEquals("getYear not returning the correct year.", 2000,
				timeManager.getYear());
	}

	// getMinutes
	@Test
	public void getMinutesTest() {
		timeManager.setDateTime(31, 27, 11, 13, 1, 2000);
		Assert.assertEquals("getMinutes not returning the correct minute.", 27,
				timeManager.getMinutes());
	}

	// getHours
	@Test
	public void getHoursTest() {
		timeManager.setDateTime(31, 27, 11, 13, 1, 2000);
		Assert.assertEquals("getHours not returning the correct hour.", 11,
				timeManager.getHours());
	}

	// nextDay
	@Test
	public void nextDayTest() {
		// typical case
		timeManager.setDateTime(31, 27, 11, 13, 1, 2000);
		timeManager.nextDay();
		Assert.assertEquals("nextDay not incrementing properly",
				14, timeManager.getDay());

		// February 28th on a leap year
		timeManager.setDateTime(31, 27, 11, 28, 2, 2004);
		timeManager.nextDay();
		Assert.assertEquals("nextDay 'day' not incrementing properly for leap year.",
				29, timeManager.getDay());
		Assert.assertEquals("nextDay 'month' not incrementing properly for leap year.",
				2, timeManager.getMonth());

		// June 30 (next month)
		timeManager.setDateTime(31, 27, 11, 30, 6, 2007);
		timeManager.nextDay();
		Assert.assertEquals("nextDay 'day' not incrementing properly for end of month case.",
				1, timeManager.getDay());
		Assert.assertEquals("nextDay 'month' not incrementing properly for end of month case.",
				7, timeManager.getMonth());

		// December 31st (next year)
		timeManager.setDateTime(31, 27, 11, 31, 12, 2007);
		timeManager.nextDay();
		Assert.assertEquals("nextDay 'day' not incrementing properly for end of year case.",
				1, timeManager.getDay());
		Assert.assertEquals("nextDay 'month' not incrementing properly for end of year case.",
				1, timeManager.getMonth());
		Assert.assertEquals("nextDay 'month' not incrementing properly for end of year case.",
				2008, timeManager.getYear());
	}

	// nextSecond
	@Test
	public void nextSecondTest() {
		// typical case
		timeManager.setDateTime(31, 27, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals("nextSecond 'second' not incrementing properly for typical case.",
				32, timeManager.getSeconds());

		// end of minute case
		timeManager.setDateTime(59, 27, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals("nextSecond 'second' not incrementing properly for end of minute case.",
				0, timeManager.getSeconds());
		Assert.assertEquals("nextSecond 'minute' not incrementing properly for end of minute case.",
				28, timeManager.getMinutes());

		// end of minute & hour case
		timeManager.setDateTime(59, 59, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals("nextSecond 'second' not incrementing properly for end of minute case.",
				0, timeManager.getSeconds());
		Assert.assertEquals("nextSecond 'minute' not incrementing properly for end of minute case.",
				0, timeManager.getMinutes());
		Assert.assertEquals("nextSecond 'hour' not incrementing properly for end of minute case.",
				12, timeManager.getHours());

		// end of minute, hour & day case
		timeManager.setDateTime(59, 59, 23, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals("nextSecond 'second' not incrementing properly for end of minute case.",
				0, timeManager.getSeconds());
		Assert.assertEquals("nextSecond 'minute' not incrementing properly for end of minute case.",
				0, timeManager.getMinutes());
		Assert.assertEquals("nextSecond 'hour' not incrementing properly for end of minute case.",
				0, timeManager.getHours());
		// nextDay already tested

	}

	// isNight
	@Test
	public void isNightTest() {
		// daytime typical
		timeManager.setDateTime(0,0, 0, 11, 1, 2001);
		Assert.assertEquals("It should currently be nighttime.", true, timeManager.isNight());

		// nighttime typical
		timeManager.setDateTime(0,0, 12, 11, 1, 2001);
		Assert.assertEquals("It should currently be daytime.", false, timeManager.isNight());

		// changing from night to day
		timeManager.setDateTime(59,59,4,12, 4, 2033);
		timeManager.nextSecond();
		Assert.assertEquals("checkNight not updating isNight while transitioning from night to day",
				false, timeManager.isNight());

		//changing from day to night
		timeManager.setDateTime(59,59,18,12, 4, 2033);
		timeManager.nextSecond();
		Assert.assertEquals("checkNight not updating isNight while transitioning from day to night",
				true, timeManager.isNight());
	}

	// onTick
	@Test
	public void onTickTest() {

	}

	// getTime
	@Test
	public void getTimeTest() {

	}


	@Test
	public void isLeapYearTest() throws Exception {
		// not leap year
		Assert.assertFalse(timeManager.isLeapYear(1999));

		// is leap year
		Assert.assertTrue(timeManager.isLeapYear(2016));

		// not leap year (because divisible by 100)
		Assert.assertFalse(timeManager.isLeapYear(1900));

		// not leap year (because divisible by 400)
		Assert.assertTrue(timeManager.isLeapYear(2000));
	}

	@Test
	public void pauseTimeTest() {
		// time is paused, nothing should increment during onTick method call
		timeManagerPauseTest.pauseTime();

		int temp = timeManagerPauseTest.getSeconds();
		int temp2 = timeManagerPauseTest.getTimeElapsed();

		// try and run time
		timeManagerPauseTest.onTick(0);

		Assert.assertEquals("Time is running whilst paused.",
				temp, timeManagerPauseTest.getSeconds());
		Assert.assertEquals("Time is running whilst paused.",
				temp2, timeManagerPauseTest.getTimeElapsed());
	}

	@Test
	public void unpauseTimeTest() {
		timeManagerPauseTest.unpauseTime();

		int temp = timeManagerPauseTest.getSeconds();
		int temp2 = timeManagerPauseTest.getTimeElapsed();

		// seconds should increment by 1 on tick
		timeManagerPauseTest.onTick(0);
		Assert.assertEquals("Time is not running correctly after being unpaused.", temp + 1,
				timeManagerPauseTest.getSeconds());
		Assert.assertEquals("Time is not running correctly after being unpaused.", temp2 + 1,
				timeManagerPauseTest.getTimeElapsed());
	}





	@Test
	public void getDateTest() {
		Assert.assertEquals("getDate string not printing correctly.",
				"01 January", timeManagerGetDateTest.getDate());
	}

	// setTimeElapsed (going to delete this method)

	// getTimeElapsed (going to delete this method)

	// setTimeLabel (not sure how to test this)

	// setDateLabel (not sure how to test this)

	// printDate (not sure how to test this)

	// GetDateTime (not sure how to test this)


}