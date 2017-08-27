package com.deco2800.hcg.managers;

import org.junit.*;

public class StopwatchManagerTests {
	private StopwatchManager stopwatch;
	private StopwatchManager stopwatchClean;


	@Before
	public void setUp() {
		stopwatch = new StopwatchManager();
		stopwatchClean = new StopwatchManager();
	}

	@Test
	public void timeCalcTest(){
		
		
		//set the Stopwatch for one in game minute
		for(int i=0; i<50; i++) {
		stopwatch.onTick(i);
		}
		
		// See if the minute component follows (to avoid trickery 
		// with float equality)
		Assert.assertTrue((int) stopwatch.getStopwatchTime() == 1);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() != 1);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() == 0);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() == 2);
		
		//test to see if Stopwatch reset has worked. 
		stopwatch.resetStopwatch();
		
		Assert.assertTrue((int) stopwatch.getStopwatchTime() == 0);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() != 0);
		

	}
	
	@Test
	public void stopwatchResetTest(){
		
		
		//test to see if Stopwatch reset has worked. 
		stopwatch.resetStopwatch();
		
		Assert.assertTrue((int) stopwatch.getStopwatchTime() == 0);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() == 1);
		
		Assert.assertFalse((int) stopwatch.getStopwatchTime() != 0);
		

	}
	
	@Test
	public void stopwatchTimerTest(){
		
		//set the Stopwatch for one in game minute
		for(int i=0; i<50; i++) {
			stopwatch.onTick(i);
			stopwatchClean.onTick(i);
		}		
		
		// no timers set
		Assert.assertFalse(stopwatch.getStatus());
		Assert.assertFalse(stopwatchClean.getStatus());
		
		int timelimit = 2; // 2 minute limit
	
		stopwatch.startTimer(timelimit);
		stopwatchClean.startTimer(timelimit);
		
		// timers set, but not reached yet
		Assert.assertFalse(stopwatch.getStatus());
		Assert.assertFalse(stopwatchClean.getStatus());
		
		for(int i=0; i<50; i++) {
			stopwatch.onTick(i);
			stopwatchClean.onTick(i);
		}		
		
		// time elapsed
		Assert.assertTrue(stopwatch.getStatus());
		Assert.assertTrue(stopwatchClean.getStatus());

		// I'm trusting that reset works
		// switching order around a bit.
		
		stopwatchClean.resetStopwatch();
		stopwatch.resetStopwatch();
		
		stopwatchClean.startTimer(timelimit);
		stopwatch.startTimer(timelimit);
		
		for(int i=0; i<50; i++) {
			stopwatch.onTick(i);
			stopwatchClean.onTick(i);
		}		
		
		Assert.assertFalse(stopwatchClean.getStatus());
		Assert.assertFalse(stopwatch.getStatus());
		
		stopwatch.stopTimer();
		
		for(int i=0; i<50; i++) {
			stopwatch.onTick(i);
			stopwatchClean.onTick(i);
		}	
		
		Assert.assertTrue(stopwatchClean.getStatus());
		Assert.assertFalse(stopwatch.getStatus());

	}


}