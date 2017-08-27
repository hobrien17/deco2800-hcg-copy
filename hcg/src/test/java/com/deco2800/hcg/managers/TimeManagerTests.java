package com.deco2800.hcg.managers;

import org.junit.*;

public class TimeManagerTests {
	private TimeManager timeManager;
	private TimeManager timeManagerPauseTest;
	private TimeManager timeManagerNextDayTest;
	private TimeManager timeManagerCalanderIncrementingTest;
	private TimeManager timeManagerNextSecondTest;
	private TimeManager timeManagerGetDateTest;
	private TimeManager timeManagerNightDayTest;

	@Before
	public void setUp() {
		timeManager = new TimeManager();

		// for (un)pauseTest
		timeManagerPauseTest = new TimeManager();

		// for nextDayTest
		timeManagerNextDayTest = new TimeManager();

		// for Calander Incrementing Test
		timeManagerCalanderIncrementingTest = new TimeManager();

		// for nextSecondTest
		timeManagerNextSecondTest = new TimeManager();

		// for getDateTest
		timeManagerGetDateTest = new TimeManager();
		
		// for NightDayTest
		timeManagerNightDayTest = new TimeManager();
		timeManagerNightDayTest.pauseTime();
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

	@Test
	public void nextDayTest() {
		// timeManagerNextDayTest.days should increment from 1 to 2
		timeManagerNextDayTest.nextDay();
		Assert.assertEquals("nextDay not incrementing properly", 2,
				timeManagerNextDayTest.getDay());
	}

	@Test
	public void nextSecondTest() {
		// timeManagerNextSecondTest.seconds should increment from 0 to 1
		timeManagerNextSecondTest.nextSecond();
		Assert.assertEquals("nextSecond not incrementing properly.", 1,
				timeManagerNextSecondTest.getSeconds());
	}

	@Test
	public void getDateTest() {
		Assert.assertEquals("getDate string not printing correctly.",
				"01 January", timeManagerGetDateTest.getDate());
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
		Assert.assertEquals("night is not intiated to true",
				true, timeManagerNightDayTest.isNight());
		

		// get to a second before 5am (change over to day)
		for (int i = 0; i<17999; i++){
			timeManagerNightDayTest.nextSecond();
		}
		
		// edge case: last second that it is night
		Assert.assertEquals("fudf",
				true, timeManagerNightDayTest.isNight());
		
		// edge case: the first second that it is day
		timeManagerNightDayTest.nextSecond();
		Assert.assertEquals("fudf",
				false, timeManagerNightDayTest.isNight());
	}	
}