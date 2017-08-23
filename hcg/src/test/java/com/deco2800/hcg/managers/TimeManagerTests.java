package com.deco2800.hcg.managers;

import org.junit.*;

public class TimeManagerTests {
	private TimeManager timeManager;

	@Before
	public void setUp() {
		timeManager = new TimeManager();
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
}