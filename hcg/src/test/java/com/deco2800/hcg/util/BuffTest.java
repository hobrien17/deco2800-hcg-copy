package com.deco2800.hcg.util;

import com.deco2800.hcg.buffs.Buff;

import java.util.*;
import org.junit.*;

public class BuffTest {

    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

    @Test
    public void buffConstructorTest() {
        Buff buff = new Buff("Test Buff", "Buff description", 100);

        String expectedBuffName = "Test Buff";
        String expectedDescription = "Buff description";
        int expectedDuration = 100;

        Assert.assertTrue("Buff name is not correct",
                buff.getName().equals(expectedBuffName));
        Assert.assertTrue("Buff duration is not correct",
                buff.getDescription().equals(expectedDescription));
        Assert.assertTrue("Buff duration is not correct",
                buff.getDuration() == expectedDuration);
    }

    @Test
    public void buffGetEffectsTest() {
        Buff buff = new Buff("Test Buff", "Buff description", 100);

        ArrayList<String> expectedEffects = new ArrayList<>();
        expectedEffects.add("effect1");
        expectedEffects.add("effect2");

        buff.addEffect("effect1", 1.2f);
        buff.addEffect("effect2", 1.4f);

        ArrayList<String> buffEffects = buff.getEffects();

        Assert.assertTrue("Effects list is incorrect", expectedEffects.equals(buffEffects));
    }

    @Test
    public void buffGetEffectTest() {

        Buff buff = new Buff("Test Buff", "Buff description", 100);

        buff.addEffect("effect1", 1.2f);
        buff.addEffect("effect2", 1.4f);

        float expectedEffect1 = 1.2f;
        float expectedEffect2 = 1.4f;
        float expectedEffect3 = -1f;
        float epsilon = 0.0001f;

        // Test general effect getters
        Assert.assertTrue("Incorrect buff effect",
                Math.abs(buff.getEffect("effect1") - expectedEffect1)
                        < epsilon);
        Assert.assertTrue("Incorrect buff effect",
                Math.abs(buff.getEffect("effect2") - expectedEffect2)
                        < epsilon);

        // Test non-existent effect
        Assert.assertTrue("Incorrect buff effect",
                Math.abs(buff.getEffect("effect3") - expectedEffect3)
                        < epsilon);
    }

    @Test
    public void buffDurationTest() {
        Buff buff = new Buff("Test Buff", "Buff description", 100);

        buff.decrementDuration(25);

        Assert.assertTrue("Duration not decremented correctly", buff.getDuration() == 75);

        buff.decrementDuration(-100);

        Assert.assertTrue("Duration not decremented correctly", buff.getDuration() == 75);

        buff.decrementDuration( 200);

        Assert.assertTrue("Duration not decremented correctly", buff.getDuration() == 0);
    }

    @Test
    public void testToString() {
        Buff buff = new Buff("Test Buff", "Buff description", 100);

        String expectedString = "Buff: Test Buff" + LINE_SEPARATOR
                + "Duration: 100" + LINE_SEPARATOR
                + "Description: Buff description" + LINE_SEPARATOR;
        
        Assert.assertTrue("Incorrect toString message", buff.toString().equals(expectedString));
    }


}
