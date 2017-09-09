package com.deco2800.hcg.managers;

import org.junit.*;

import com.deco2800.hcg.BaseTest;

//import uk.org.lidalia.slf4jtest.TestLogger;
//import uk.org.lidalia.slf4jtest.TestLoggerFactory;
//import static uk.org.lidalia.slf4jtest.LoggingEvent.info;

import static java.util.Arrays.asList;
//import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SoundManagerTests extends BaseTest {
	/*private SoundManager soundManager = new SoundManager();;

	private static final TestLogger TEST_LOGGER = TestLoggerFactory.getTestLogger(SoundManager.class);

	// play exist sound effect
	@Test
	public void testPlaySound1() {
		soundManager.playSound("ice");
		assertThat("playSound do not work properly: ice should exist in the soundMap)", TEST_LOGGER.getLoggingEvents(),
				is(asList(info("Playing sound effect: " + "ice"))));
	}

	// play nonexistent sound effect
	@Test
	public void testPlaySound2() {
		soundManager.playSound("name000");
		assertThat("playSound do not work properly: unreferenced sound should not be played)",
				TEST_LOGGER.getLoggingEvents(), is(asList(info("No reference to sound effect: " + "name000"))));
	}

	// loop exist sound effect
	@Test
	public void testLoopSound1() {
		soundManager.loopSound("ground");
		assertThat("loopSound does not work properly: ground should exist in the soundMap)",
				TEST_LOGGER.getLoggingEvents(), is(asList(info("Playing sound effect, looping : " + "ground"))));

	}

	// loop nonexistent sound effect
	@Test
	public void testLoopSound2() {
		soundManager.loopSound("name111");
		assertThat("playSound do not work properly: unreferenced sound should not be looped)",
				TEST_LOGGER.getLoggingEvents(), is(asList(info("No reference to sound effect: " + "name111"))));
	}

	// stop exist sound effect --- 1
	@Test
	public void testStopSound1() {
		soundManager.stopSound("ice");
		assertThat("playSound does not work properly: ice should exist in the soundMap)",
				TEST_LOGGER.getLoggingEvents(), is(asList(info("Stop sound effect: " + "ice"))));
	}

	// stop exist sound effect --- 2
	@Test
	public void testStopSound2() {
		soundManager.stopSound("ground");
		assertThat("playSound does not work properly: ground should exist in the soundMap)",
				TEST_LOGGER.getLoggingEvents(), is(asList(info("Stop sound effect: " + "ground"))));
	}

	// stop nonexistent sound effect
	@Test
	public void testStopSound3() {
		soundManager.stopSound("name222");
		assertThat("playSound do not work properly: unreferenced sound should not be stoped)",
				TEST_LOGGER.getLoggingEvents(), is(asList(info("No reference to sound effect: " + "name222"))));
	}

	@After
	public void clearLoggers() {
		TestLoggerFactory.clear();
	}*/
}
