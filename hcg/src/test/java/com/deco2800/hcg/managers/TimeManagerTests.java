package com.deco2800.hcg.managers;

import org.junit.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TimeManagerTests {
	private TimeManager timeManager;
	private TimeManager timeManagerPauseTest;
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
		// invalid input cases (MathHelper.java not tested yet so testing here
		// for own sanity)
		timeManager.setDateTime(-1, 0, 0, 1, 1, 1);
		Assert.assertEquals("Invalid seconds input not clamped", 0,
				timeManager.getSeconds());

		timeManager.setDateTime(0, 62, 0, 1, 1, 1);
		Assert.assertEquals("Invalid minutes input not clamped", 59,
				timeManager.getMinutes());

		timeManager.setDateTime(34, 58, 29, 1, 1, 1);
		Assert.assertEquals("Invalid hours input not clamped", 23,
				timeManager.getHours());

		timeManager.setDateTime(23, 23, 23, -4, 1, 1);
		Assert.assertEquals("Invalid day input not clamped", 1,
				timeManager.getDay());

		timeManager.setDateTime(15, 35, 3, 5, 13, 1);
		Assert.assertEquals("Invalid month input not clamped", 12,
				timeManager.getMonth());

		timeManager.setDateTime(0, 0, 0, 1, 1, -1);
		Assert.assertEquals("Invalid year input not clamped", 1,
				timeManager.getYear());

		// typical case
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
		timeManager.setDateTime(30, 27, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals(
				"nextSecond 'second' not incrementing properly for typical case.",
				32, timeManager.getSeconds());

		// end of minute case
		timeManager.setDateTime(60, 27, 11, 12, 1, 2007);
		timeManager.nextSecond();
		Assert.assertEquals(
				"nextSecond 'second' not incrementing properly for end of minute case.",
				0, timeManager.getSeconds());
		Assert.assertEquals(
				"nextSecond 'minute' not incrementing properly for end of minute case.",
				28, timeManager.getMinutes());

		// end of minute & hour case
		timeManager.setDateTime(60, 59, 11, 12, 1, 2007);
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
		timeManager.setDateTime(60, 59, 23, 12, 1, 2007);
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

		// try and run time
		timeManagerPauseTest.onTick(0);

		Assert.assertEquals("Time is running whilst paused.", temp,
				timeManagerPauseTest.getSeconds());
	}

	@Test
	public void unpauseTimeTest() {
		timeManagerPauseTest.unpauseTime();

		int temp = timeManagerPauseTest.getSeconds();

		// seconds should increment by 1 on tick
		timeManagerPauseTest.onTick(0);
		Assert.assertEquals(
				"Time is not running correctly after being unpaused.", temp + 2,
				timeManagerPauseTest.getSeconds());
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
		timeManager.setDateTime(34, 53, 16, 3, 8, 2017);
		Assert.assertEquals("getDate string not printing correctly.", "16:53",
				timeManager.getTime());
	}

	// getDateTime test
	@Test
	public void getDateTimeTest() {
		timeManager.setDateTime(5, 6, 7, 8, 9, 2010);
		Assert.assertEquals("Incorrect date being printed",
				"08/09/2010 07:06:05", timeManager.getDateTime());
	}

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

		timeManagerNightDayTest.setDateTime(60, 59, 4, 0, 0, 0);

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
		timeManagerIncrementingMinTest.setDateTime(60, 0, 0, 0, 0, 0);

		Assert.assertEquals("first second of the minute", 60,
				timeManagerIncrementingMinTest.getSeconds());

		Assert.assertEquals("first second that minute == 1", 0,
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
		timeManagerIncrementingHrTest.setDateTime(60, 59, 0, 0, 0, 0);

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