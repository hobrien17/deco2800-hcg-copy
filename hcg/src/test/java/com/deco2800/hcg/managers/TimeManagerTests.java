package com.deco2800.hcg.managers;

import org.junit.*;

public class TimeManagerTests {
	private TimeManager timeManager;
	private TimeManager timeManagerPauseTest;
	private TimeManager timeManagerNextDayTest;
	private TimeManager timeManagerNextSecondTest;

	@Before
	public void setUp() {
		timeManager = new TimeManager();

		// for (un)pauseTest
		timeManagerPauseTest = new TimeManager();

		// for nextDayTest
		timeManagerNextDayTest = new TimeManager();

		// for nextSecondTest
		timeManagerNextSecondTest = new TimeManager();
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
		timeManagerPauseTest.onTick(0);
		Assert.assertEquals("Time is running whilst paused.",
				0, timeManagerPauseTest.getSeconds());
		Assert.assertEquals("Time is running whilst paused.",
				0, timeManagerPauseTest.getTimeElapsed());
	}

	@Test
	public void unpauseTimeTest() {
		timeManagerPauseTest.unpauseTime();
		// seconds should increment by 1 on tick
		timeManagerPauseTest.onTick(0);
		Assert.assertEquals("Time is not running correctly after being unpaused.", 1,
				timeManagerPauseTest.getSeconds());
		Assert.assertEquals("Time is not running correctly after being unpaused.", 1,
				timeManagerPauseTest.getTimeElapsed());
	}

	@Test
	public void nextDayTest() {
		// timeManagerNextDayTest.days should increment from 1 to 2
		timeManagerNextDayTest.nextDay();
		Assert.assertEquals("nextDay not incrementing properly",
				2, timeManagerNextDayTest.getDay());
	}

	@Test
	public void nextSecondTest() {
		// timeManagerNextSecondTest.seconds should increment from 0 to 1
		timeManagerNextSecondTest.nextSecond();
		Assert.assertEquals("nextSecond not incrementing properly.",
				1, timeManagerNextSecondTest.getSeconds());
	}






}