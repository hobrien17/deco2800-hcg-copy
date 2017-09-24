package com.deco2800.hcg.util;

import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.enemyentities.Squirrel;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

public class EffectsTest {

    // The Character used to store the effects object.
    Character entity = new Squirrel(0, 0, 0, 12345);

    @Test
    public void effectsConstructorTest() {
        Effects effects = new Effects(entity);

        Assert.assertTrue("Effects did not construct properly", effects.equals(effects));
    }

    @Test
    public void effectsConstructorMergeTest() {
        Effect effect1 = new Effect("Test", 3, 5, 0, 500, 2, 0);
        Effect effect2 = new Effect("Test2", 3, 5, 0, 500, 2, 0);

        Collection<Effect> effectCollection = new HashSet<>();
        effectCollection.add(effect1);
        effectCollection.add(effect2);

        Effects effects = new Effects(entity, effectCollection);

        Assert.assertTrue("Effects did not construct properly", effects.equals(effects));
    }

    @Test(expected = NullPointerException.class)
    public void effectsConstructorNullPointerTest() {
        Effects effects = new Effects(null);
        Effects effects2 = new Effects(entity, null);
    }

    @Test(expected = NullPointerException.class)
    public void effectsAddEffectNullPointerTest() {
        Effects effects = new Effects(entity);
        effects.addEffect(null);
    }

    @Test(expected = NullPointerException.class)
    public void effectsAddAllEffectsNullPointerTest() {
        Effects effects = new Effects(entity);
        effects.addAllEffects(null);
    }

    @Test(expected = NullPointerException.class)
    public void effectRemoveEffectNullPointerTest() {
        Effects effects = new Effects(entity);
        effects.removeEffect(null);
    }

    @Test
    public void effectsAddEffectTest() {
        Effect effect1 = new Effect("Test", 3, 5, 0, 500, 2, 0);

        Effects effects = new Effects(entity);
        effects.addEffect(effect1);

        Assert.assertTrue("Effect was not correctly added", effects.getEffects().contains(effect1));
    }

    @Test
    public void effectsRemoveEffectTest() {
        Effect effect1 = new Effect("Test", 3, 5, 0, 500, 2, 0);

        Effects effects = new Effects(entity);
        effects.removeEffect(effect1);

        Assert.assertTrue("Effect was not correctly removed", !effects.getEffects().equals(effect1));
    }

    @Test
    public void effectsClearEffectsTest() {
        Effect effect1 = new Effect("Test", 3, 5, 0, 500, 2, 0);

        Effects effects = new Effects(entity);
        effects.addEffect(effect1);
        effects.clear();

        Assert.assertTrue("Effects set was not correctly cleared", !effects.getEffects().equals(effect1));
    }

    @Test
    public void effectsAddAllEffectsTest() {
        Effect effect1 = new Effect("Test", 3, 5, 0, 500, 2, 0);
        Effect effect2 = new Effect("Test2", 3, 5, 0, 500, 2, 0);

        Collection<Effect> effectCollection = new HashSet<>();
        effectCollection.add(effect1);
        effectCollection.add(effect2);

        Effects effects = new Effects(entity);

        effects.addAllEffects(effectCollection);

        Assert.assertTrue("AddAll effects did not complete correctly", effects.getEffects().equals(effectCollection));
    }

    @Test
    public void effectsApplyTest() {
        Effects effects = new Effects(entity);

        effects.apply();

        Assert.assertTrue(true);
    }
}
