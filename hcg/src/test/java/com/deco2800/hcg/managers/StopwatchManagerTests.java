package com.deco2800.hcg.managers;

import org.junit.*;

public class StopwatchManagerTests {
	private StopwatchManager stopwatch;


	@Before
	public void setUp() {
		stopwatch = new StopwatchManager();
	}

	@Test
	public void timeCalcTest() throws Exception {
		
		for(int i=0; i<50; i++) {
		stopwatch.onTick(i);
		}
		
		// 1 min
		Assert.assertTrue((int) stopwatch.getStopwatchTime() == 1);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() == 0);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() == 2);
		
		stopwatch.resetStopwatch();
		
		Assert.assertTrue((int) stopwatch.getStopwatchTime() == 0);

	}


}