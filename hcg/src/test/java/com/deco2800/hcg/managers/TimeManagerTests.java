package com.deco2800.hcg.managers;

import org.junit.*;

public class TimeManagerTests {
	private TimeManager timeManager;
	private TimeManager timeManagerPauseTest;

	@Before
	public void setUp() {
		timeManager = new TimeManager();

		// for pause tests
		timeManagerPauseTest = new TimeManager();


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
	public void pauseTimeTest() throws Exception {
		// time is paused, nothing should increment during onTick method call
		timeManagerPauseTest.pauseTime();
		timeManagerPauseTest.onTick(0);
		Assert.assertEquals("Time is running whilst paused.", 0, timeManagerPauseTest.getSeconds());
		Assert.assertEquals("Time is running whilst paused.", 0, timeManagerPauseTest.getTimeElapsed());
	}

	@Test
	public void unpauseTimeTime() throws Exception {
		timeManagerPauseTest.unpauseTime();
		// seconds should increment by 1 on tick
		timeManagerPauseTest.onTick(0);
		Assert.assertEquals("Time is not running correctly after being unpaused.", 1,
				timeManagerPauseTest.getSeconds());
		Assert.assertEquals("Time is not running correctly after being unpaused.", 1,
				timeManagerPauseTest.getTimeElapsed());
	}






}