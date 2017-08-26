package com.deco2800.hcg.util;

import com.deco2800.hcg.buffs.Buff;
import com.deco2800.hcg.managers.BuffManager;
import java.util.*;
import org.junit.*;
import org.lwjgl.Sys;

public class BuffManagerTest {

    private ArrayList<Buff> testBuffs = new ArrayList<>();
    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

    @Before
    public void testSetup() {

        testBuffs.clear();

        Buff buff1 = new Buff("Buff1", "A test buff", 100);
        Buff buff2 = new Buff("Buff2", "Another test buff", 20);
        Buff buff3 = new Buff("Buff3", "Buff number 3", 50);

        testBuffs.add(buff1);
        testBuffs.add(buff2);
        testBuffs.add(buff3);
    }

    @Test
    public void testAddBuff() {
        BuffManager manager = new BuffManager();

        Assert.assertTrue("Incorrect number of buffs in manager", manager.getBuffs().size() == 0);
        manager.addBuff(testBuffs.get(0));

        Assert.assertTrue("Incorrect number of buffs in manager", manager.getBuffs().size() == 1);
        Assert.assertTrue("Buff not added correctly", manager.getBuff("Buff1").get(0).equals(testBuffs.get(0)));

        manager.addBuff(testBuffs.get(1));
        manager.addBuff(testBuffs.get(1));

        Assert.assertTrue("Incorrect number of buffs in manager", manager.getBuffs().size() == 2);
        Assert.assertTrue("Buff not added correctly", manager.getBuff("Buff2").get(0).equals(testBuffs.get(1)));
    }

    @Test
    public void testRemoveBuff() {
        BuffManager manager = new BuffManager();

        manager.addBuff(testBuffs.get(0));
        manager.addBuff(testBuffs.get(1));
        manager.addBuff(testBuffs.get(2));

        Assert.assertTrue("Incorrect number of buffs in manager", manager.getBuffs().size() == 3);
        manager.removeBuff("Buff2");

        Assert.assertTrue("Incorrect number of buffs in manager", manager.getBuffs().size() == 2);
        manager.removeBuff("Buff2");

        Assert.assertTrue("Incorrect number of buffs in manager", manager.getBuffs().size() == 2);

        manager.clearBuffs();
        Assert.assertTrue("Incorrect number of buffs in manager", manager.getBuffs().size() == 0);
    }

    @Test
    public void testToString() {
        BuffManager manager = new BuffManager();

        manager.addBuff(testBuffs.get(0));
        manager.addBuff(testBuffs.get(1));
        manager.addBuff(testBuffs.get(2));

        String expectedString = "Buff1" + LINE_SEPARATOR
                + "Buff2" + LINE_SEPARATOR
                + "Buff3" + LINE_SEPARATOR;

        Assert.assertTrue("Incorrect toString message", manager.toString().equals(expectedString));
    }
}
