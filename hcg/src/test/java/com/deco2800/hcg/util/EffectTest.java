package com.deco2800.hcg.util;

import org.junit.Assert;
import org.junit.Test;

public class EffectTest {

    @Test
    public void effectConstructorTest() {
        Effect effect = new Effect("Test", 3, 5, 0, 500, 2000, 0);

        String expectedName = "Test";
        int expectedLevel = 3;
        int expectedDamage = 5;
        int expectedSlow = 0;
        int expectedCooldown = 500;
        int expectedDuration = 2000;
        int expectedDelay = 0;

        Assert.assertTrue("Effect name is incorrect", effect.getName().equals(expectedName));
        Assert.assertTrue("Effect level is incorrect", effect.getLevel() == expectedLevel);
        Assert.assertTrue("Effect damage is incorrect", effect.getDamage() == expectedDamage);
        Assert.assertTrue("Effect slow is incorrect", effect.getSlowAmount() == expectedSlow);
        Assert.assertTrue("Effect cooldown is incorrect", effect.getCooldown() == expectedCooldown);
        Assert.assertTrue("Effect duration is incorrect", effect.getDuration() == expectedDuration);
        Assert.assertTrue("Effect delay is incorrect", effect.getDelay() == expectedDelay);
    }
}
