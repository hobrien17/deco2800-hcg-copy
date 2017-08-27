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
		Assert.assertTrue("Stopwatch not returning correct time value",
				(int) stopwatch.getStopwatchTime() == 1);
		
		Assert.assertFalse("Stopwatch not returning correct time value",
				(int) stopwatch.getStopwatchTime() != 1);
		
		Assert.assertFalse("Stopwatch not returning correct time value",
				(int) stopwatch.getStopwatchTime() == 0);
		
		Assert.assertFalse("Stopwatch not returning correct time value",
				(int) stopwatch.getStopwatchTime() == 2);	

	}
	
	@Test
	public void stopwatchResetTest(){
		
		
		//test to see if Stopwatch reset has worked. 
		stopwatch.resetStopwatch();
		
		Assert.assertTrue("Stopwatch has not reset",
				(int) stopwatch.getStopwatchTime() == 0);
		
		Assert.assertFalse("Stopwatch has not reset",
				(int) stopwatch.getStopwatchTime() == 1);
		
		Assert.assertFalse("Stopwatch has not reset",
				(int) stopwatch.getStopwatchTime() != 0);
		

	}
	
	@Test
	public void stopwatchTimerTest(){
		
		//set the Stopwatch for one in game minute
		for(int i=0; i<50; i++) {
			stopwatch.onTick(i);
			stopwatchClean.onTick(i);
		}		
		
		// no timers set
		Assert.assertFalse("Stopwatch should not have timer set",
				stopwatch.getStatus());
		Assert.assertFalse("Stopwatch should not have timer set",
				stopwatchClean.getStatus());
		
		int timelimit = 2; // 2 minute limit
	
		stopwatch.startTimer(timelimit);
		stopwatchClean.startTimer(timelimit);
		
		// timers set, but not reached yet
		Assert.assertFalse("Timer should not be finished",
				stopwatch.getStatus());
		Assert.assertFalse("Timer should not be finished",
				stopwatchClean.getStatus());
		
		for(int i=0; i<50; i++) {
			stopwatch.onTick(i);
			stopwatchClean.onTick(i);
		}		
		
		// time elapsed
		Assert.assertTrue("Timer should be finished",
				stopwatch.getStatus());
		Assert.assertTrue("Timer should be finished",
				stopwatchClean.getStatus());

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
		
		Assert.assertFalse("Stopwatch should be reset",
				stopwatchClean.getStatus());
		Assert.assertFalse("Stopwatch should be reset",
				stopwatch.getStatus());
		
		stopwatch.stopTimer();
		
		for(int i=0; i<50; i++) {
			stopwatch.onTick(i);
			stopwatchClean.onTick(i);
		}	
		
		Assert.assertTrue("Stopwatch should be finished",
				stopwatchClean.getStatus());
		Assert.assertFalse("Timer should be reset",
				stopwatch.getStatus());

	}


}