package com.deco2800.hcg.managers;

import org.junit.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TimeManagerTests {
	private TimeManager timeManager;
	private TimeManager timeManagerPauseTest;
	private TimeManager timeManagerNextDayTest;
	private TimeManager timeManagerNextSecondTest;
	private TimeManager timeManagerGetDateTest;
	private TimeManager timeManagerNightDayTest;
	private TimeManager timeManagerIncrementingMinTest;
	private TimeManager timeManagerIncrementingHrTest;
	private TimeManager timeManagerCalanderIncrementingTest;


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

		// for NightDayTest
		timeManagerNightDayTest = new TimeManager();
		timeManagerNightDayTest.pauseTime();

		// for second to minute tests
		timeManagerIncrementingMinTest = new TimeManager();
		timeManagerIncrementingMinTest.pauseTime();

		// for minute to hour tests
		timeManagerIncrementingHrTest = new TimeManager();
		timeManagerIncrementingHrTest.pauseTime();

		// for Calander Incrementing Test
		timeManagerCalanderIncrementingTest = new TimeManager();
		timeManagerCalanderIncrementingTest.pauseTime();

	}

	// setDateTimeTests
	@Test
	public void setDateTimeTest() {
		// invalid input cases
		boolean invalidSeconds = false;
		try {
			timeManager.setDateTime(-1, 0, 0, 1, 1, 1);
		} catch (IllegalArgumentException e) {
			invalidSeconds = true;
		}
		Assert.assertEquals("Invalid seconds input not recognised", true, invalidSeconds);

		boolean invalidMinutes = false;
		try {
			timeManager.setDateTime(0, 62, 0, 1, 1, 1);
		} catch (IllegalArgumentException e) {
			invalidMinutes = true;
		}
		Assert.assertEquals("Invalid minutes input not recognised", true, invalidMinutes);

		boolean invalidHours = false;
		try {
			timeManager.setDateTime(34, 58, 29, 1, 1, 1);
		} catch (IllegalArgumentException e) {
			invalidHours = true;
		}
		Assert.assertEquals("Invalid hours input not recognised", true, invalidHours);

		boolean invalidDay = false;
		try {
			timeManager.setDateTime(23, 23, 23, -4, 1, 1);
		} catch (IllegalArgumentException e) {
			invalidDay = true;
		}
		Assert.assertEquals("Invalid day input not recognised", true, invalidDay);

		boolean invalidMonth = false;
		try {
			timeManager.setDateTime(15, 35, 3, 5, 13, 1);
		} catch (IllegalArgumentException e) {
			invalidMonth = true;
		}
		Assert.assertEquals("Invalid month input not recognised", true, invalidMonth);

		boolean invalidYear = false;
		try {
			timeManager.setDateTime(0, 0, 0, 1, 1, -1);
		} catch (IllegalArgumentException e) {
			invalidYear = true;
		}
		Assert.assertEquals("Invalid year input not recognised", true, invalidYear);
		//typical case
		timeManager.setDateTime(0, 0, 0, 1, 1, 1);
		Assert.assertEquals("Second not correctly set.", 0,
				timeManager.getSeconds());
		Assert.assertEquals("Minute not correctly set.", 0,
				timeManager.getMinutes());
		Assert.assertEquals("Hour not correctly set.", 0,
				timeManager.getHours());
		Assert.assertEquals("Day not correctly set.", 1, timeManager.getDay());
		Assert.assertEquals("Month not correctly set.", 1,
				timeManager.getMonth());
		Assert.assertEquals("Year not correctly set.", 1,
				timeManager.getYear());
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
		Assert.assertEquals("nextDay not incrementing properly", 14,
				timeManager.getDay());

		// February 28th on a leap year
		timeManager.setDateTime(31, 27, 11, 28, 2, 2004);
		timeManager.nextDay();
		Assert.assertEquals(
				"nextDay 'day' not incrementing properly for leap year.", 29,
				timeManager.getDay());
		Assert.assertEquals(
				"nextDay 'month' not incrementing properly for leap year.", 2,
				timeManager.getMonth());

		// June 30 (next month)
		timeManager.setDateTime(31, 27, 11, 30, 6, 2007);
		timeManager.nextDay();
		Assert.assertEquals(
				"nextDay 'day' not incrementing properly for end of month case.",
				1, timeManager.getDay());
		Assert.assertEquals(
				"nextDay 'month' not incrementing properly for end of month case.",
				7, timeManager.getMonth());

		// December 31st (next year)
		timeManager.setDateTime(31, 27, 11, 31, 12, 2007);
		timeManager.nextDay();
		Assert.assertEquals(
				"nextDay 'day' not incrementing properly for end of year case.",
				1, timeManager.getDay());
		Assert.assertEquals(
				"nextDay 'month' not incrementing properly for end of year case.",
				1, timeManager.getMonth());
		Assert.assertEquals(
				"nextDay 'month' not incrementing properly for end of year case.",
				2008, timeManager.getYear());
	}

	// nextSecond
	@Test
	public void nextSecondTest() {
		// typical case
		timeManager.setDateTime(31, 27, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals(
				"nextSecond 'second' not incrementing properly for typical case.",
				32, timeManager.getSeconds());

		// end of minute case
		timeManager.setDateTime(59, 27, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals(
				"nextSecond 'second' not incrementing properly for end of minute case.",
				0, timeManager.getSeconds());
		Assert.assertEquals(
				"nextSecond 'minute' not incrementing properly for end of minute case.",
				28, timeManager.getMinutes());

		// end of minute & hour case
		timeManager.setDateTime(59, 59, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals(
				"nextSecond 'second' not incrementing properly for end of minute case.",
				0, timeManager.getSeconds());
		Assert.assertEquals(
				"nextSecond 'minute' not incrementing properly for end of minute case.",
				0, timeManager.getMinutes());
		Assert.assertEquals(
				"nextSecond 'hour' not incrementing properly for end of minute case.",
				12, timeManager.getHours());

		// end of minute, hour & day case
		timeManager.setDateTime(59, 59, 23, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals(
				"nextSecond 'second' not incrementing properly for end of minute case.",
				0, timeManager.getSeconds());
		Assert.assertEquals(
				"nextSecond 'minute' not incrementing properly for end of minute case.",
				0, timeManager.getMinutes());
		Assert.assertEquals(
				"nextSecond 'hour' not incrementing properly for end of minute case.",
				0, timeManager.getHours());
		// nextDay already tested

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

		Assert.assertEquals("Time is running whilst paused.", temp,
				timeManagerPauseTest.getSeconds());
		Assert.assertEquals("Time is running whilst paused.", temp2,
				timeManagerPauseTest.getTimeElapsed());
	}

	@Test
	public void unpauseTimeTest() {
		timeManagerPauseTest.unpauseTime();

		int temp = timeManagerPauseTest.getSeconds();
		int temp2 = timeManagerPauseTest.getTimeElapsed();

		// seconds should increment by 1 on tick
		timeManagerPauseTest.onTick(0);
		Assert.assertEquals(
				"Time is not running correctly after being unpaused.", temp + 1,
				timeManagerPauseTest.getSeconds());
		Assert.assertEquals(
				"Time is not running correctly after being unpaused.",
				temp2 + 1, timeManagerPauseTest.getTimeElapsed());
	}

	// getDate test
	@Test
	public void getDateTest() {
		Assert.assertEquals("getDate string not printing correctly.",
				"01 January", timeManagerGetDateTest.getDate());
	}

	// getTime test
	@Test
	public void getTimeTest() {
		timeManager.setDateTime(34, 53,16, 3, 8,2017);
		Assert.assertEquals("getDate string not printing correctly.",
				"03 August", timeManager.getDate());
	}

	// getDateTime test
	@Test
	public void printDateTest() {
		timeManager.setDateTime(5, 6, 7, 8, 9,2010);
		Assert.assertEquals("Incorrect date being printed", "08/09/2010 07:06:05",
				timeManager.getDateTime());
	}

	// GetDateTime (not sure how to test this)

	@Test
	public void calanderIncrementingTest() {
		Assert.assertEquals("month is not intalised to jan", 1,
				timeManagerCalanderIncrementingTest.getMonth());

		// changing calendar to Feb 01
		for (int i = 0; i < 31; i++) {
			timeManagerCalanderIncrementingTest.nextDay();
		}

		// testing nextDay increments months correctly (general case jan -> feb)
		Assert.assertEquals(
				"nextDay not incrementing properly when changing months", 2,
				timeManagerCalanderIncrementingTest.getMonth());

		Assert.assertEquals(
				"nextDay not incrementing properly when changing months", 1,
				timeManagerCalanderIncrementingTest.getDay());

		// testing edge case increments month correctly (edge case: feb -> mar)
		int numIncrements = 0;

		if (timeManagerCalanderIncrementingTest
				.isLeapYear(timeManagerCalanderIncrementingTest.getYear())) {
			numIncrements = 29;
		} else {
			numIncrements = 28;
		}

		// change time to Mar 01
		for (int i = 0; i < numIncrements; i++) {
			timeManagerCalanderIncrementingTest.nextDay();
		}

		// testing calender increments if it is a leap year correctly
		Assert.assertEquals(
				"nextDay not incrementing properly when changing months", 3,
				timeManagerCalanderIncrementingTest.getMonth());

		Assert.assertEquals(
				"nextDay not incrementing properly when changing months", 1,
				timeManagerCalanderIncrementingTest.getDay());

	}

	@Test
	public void NightDayTest() {
		Assert.assertEquals("night is not intiated to true", true,
				timeManagerNightDayTest.isNight());

		// get to a second before 5am (change over to day)
		for (int i = 0; i < 17999; i++) {
			timeManagerNightDayTest.nextSecond();
		}

		// edge case: last second that it is night
		Assert.assertEquals("last second that it is night is not", true,
				timeManagerNightDayTest.isNight());

		// edge case: the first second that it is day
		timeManagerNightDayTest.nextSecond();
		Assert.assertEquals("first second that is day is not", false,
				timeManagerNightDayTest.isNight());
	}

	@Test
	public void incrementingMinTest() {
		// get to a second before 5am (change over to day)
		for (int i = 0; i < 59; i++) {
			timeManagerIncrementingMinTest.nextSecond();
		}

		// edge case: last second that it is minute 0 and 59 seconds
		Assert.assertEquals("last second of the minute", 59,
				timeManagerIncrementingMinTest.getSeconds());

		Assert.assertEquals("last second that minute == 0", 0,
				timeManagerIncrementingMinTest.getMinutes());

		timeManagerIncrementingMinTest.nextSecond();

		// edge case: first second that it is minute 1 and 0 seconds
		Assert.assertEquals("first second of the minute", 0,
				timeManagerIncrementingMinTest.getSeconds());

		Assert.assertEquals("first second that minute == 1", 1,
				timeManagerIncrementingMinTest.getMinutes());
	}

	@Test
	public void incrementingHrTest() {
		// get to a 00:59:59 (hr:min:sec)
		for (int i = 0; i < 3599; i++) {
			timeManagerIncrementingHrTest.nextSecond();
		}

		// edge case: last second where hr is 0
		Assert.assertEquals("last second where hr == 0", 59,
				timeManagerIncrementingHrTest.getSeconds());

		Assert.assertEquals("last second that minute == 59", 59,
				timeManagerIncrementingHrTest.getMinutes());

		Assert.assertEquals("last second that hr == 0", 0,
				timeManagerIncrementingHrTest.getHours());

		timeManagerIncrementingHrTest.nextSecond();

		// edge case: first second that it is minute 1 and 0 seconds
		Assert.assertEquals("0th second of the first hr", 0,
				timeManagerIncrementingHrTest.getSeconds());

		Assert.assertEquals("0th minute of the first hr", 0,
				timeManagerIncrementingHrTest.getMinutes());

		Assert.assertEquals("first second that hr == 1", 1,
				timeManagerIncrementingHrTest.getHours());
	}







	// setTimeLabel (not sure how to test this)

	// setDateLabel (not sure how to test this)

}