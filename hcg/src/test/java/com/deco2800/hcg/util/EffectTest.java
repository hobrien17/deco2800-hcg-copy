package com.deco2800.hcg.util;

import org.junit.Assert;
import org.junit.Test;

public class EffectTest {

    @Test
    public void effectConstructorTest() {
        Effect effect = new Effect("Test", 3, 5, 0, 500, 2, 0, null);

        String expectedName = "Test";
        int expectedLevel = 3;
        int expectedDamage = 5;
        int expectedSpeed = 0;
        int expectedCooldown = 500;
        int expectedDuration = 2;
        int expectedDelay = 0;

        Assert.assertTrue("Effect name is incorrect", effect.getName().equals(expectedName));
        Assert.assertTrue("Effect level is incorrect", effect.getLevel() == expectedLevel);
        Assert.assertTrue("Effect damage is incorrect", effect.getDamage() == expectedDamage);
        Assert.assertTrue("Effect speedModifier is incorrect", effect.getSpeedModifier() == expectedSpeed);
        Assert.assertTrue("Effect cooldown is incorrect", effect.getCooldown() ==  expectedCooldown);
        Assert.assertTrue("Effect duration is incorrect", effect.getDuration() == expectedDuration);
        Assert.assertTrue("Effect delay is incorrect", effect.getDelay() == expectedDelay);
    }

    @Test(expected = NullPointerException.class)
    public void effectConstructorNullPointerTest() {
        Effect effect = new Effect(null, 3, 5, 0, 500, 2000, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void effectConstructorIllegalArgumentTest() {
        Effect effect1 = new Effect("", 3, 5, 0, 500, 2, 0, null);
        Effect effect2 = new Effect("Test", -1, 5, 0, 500, 2, 0, null);
        Effect effect5 = new Effect("Test", 3, 5, -1.1f, 500, 2, 0, null);
        Effect effect6 = new Effect("Test", 3, 5, 0, -1, 2, 0, null);
        Effect effect7 = new Effect("Test", 3, 5, 0, 500, -1, 0, null);
        Effect effect8 = new Effect("Test", 3, 5, 0, 500, 2000, -1, null);
    }

    @Test
    public void effectApplicationTest() {
        Effect effect = new Effect("Test", 3, 5, 0, 500, 2, 0, null);
        int expectedUses = 1;
        int expectedUses2 = 2;

        Assert.assertTrue("Effect should not be on cooldown", !effect.onCooldown());

        effect.startCooldownTimer();

        Assert.assertTrue("Effect should be on cooldown", effect.onCooldown());

        effect.resetCooldownTimer();

        Assert.assertTrue("Effect should not be on cooldown", !effect.onCooldown());

        effect.decrementUses();

        Assert.assertTrue("Effect did not decrement use count", effect.getUseCount() == expectedUses);

        effect.resetUseCounter();

        Assert.assertTrue("Effect did not reset use count", effect.getUseCount() == expectedUses2);
    }

    @Test
    public void effectEqualsTest() {
        Effect effect = new Effect("Test", 3, 5, 0, 500, 2, 0, null);
        Effect effect2 = new Effect("Test", 3, 5, 0, 500, 2, 0, null);
        Effect effect3 = new Effect("Test2", 3, 5, 0, 500, 2, 0, null);

        Assert.assertTrue("Effects are not equal", effect.equals(effect2));

        Assert.assertTrue("Effects are equals", !effect.equals(effect3));
    }

    @Test
    public void effectHashCodeTest() {
        Effect effect = new Effect("Test", 3, 5, 0, 500, 2, 0, null);
        Effect effect2 = new Effect("Test", 3, 5, 0, 500, 2, 0, null);

        Assert.assertTrue("Effects' hash codes are not equal", effect.hashCode() == effect2.hashCode());
    }
}
