package com.deco2800.hcg.managers;

import org.junit.*;

public class TimeManagerTests {
	private TimeManager timeManager;
	private TimeManager timeManagerTest2;

	@Before
	public void setUp() {
		timeManager = new TimeManager();

		// for pause test
		timeManagerTest2 = new TimeManager();
		timeManagerTest2.setSeconds(53);

	}

	@Test
	public void isLeapYearTest() throws Exception {
		// not leap year
		timeManager.setYear(1999);
		Assert.assertFalse(timeManager.isLeapYear());

		// is leap year
		timeManager.setYear(2016);
		Assert.assertTrue(timeManager.isLeapYear());

		// not leap year (because divisible by 100)
		timeManager.setYear(1900);
		Assert.assertFalse(timeManager.isLeapYear());

		// not leap year (because divisible by 400)
		timeManager.setYear(2000);
		Assert.assertTrue(timeManager.isLeapYear());
	}

	@Test
	public void pauseTimeTest() throws Exception {
		timeManagerTest2.pauseTime();
		int n = timeManagerTest2.getSeconds();
		timeManagerTest2.onTick(0);
		Assert.assertEquals("Time is running whilst paused.", n, timeManagerTest2.getSeconds());
	}

	@Test
	public void unpauseTimeTime() throws Exception {
		int n = timeManagerTest2.getSeconds();
		n++;
		timeManagerTest2.unpauseTime();
		timeManagerTest2.onTick(0);
		Assert.assertEquals("Time is not running correctly after being unpaused.", n,
				timeManagerTest2.getSeconds());
	}






}