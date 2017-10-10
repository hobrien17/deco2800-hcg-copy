package com.deco2800.hcg.worldMap;


import com.deco2800.hcg.worldmapui.LevelStore;
import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.WStringSeqHelper;

import static org.junit.Assert.*;
import com.deco2800.hcg.entities.worldmap.WorldStack;
import com.deco2800.hcg.worldmapui.WorldStackGenerator;
import java.util.*;
public class WorldStackGeneratorTest {

    WorldStackGenerator wsg;
    WorldStack ws;

    @Before
    public void setup() {
        LevelStore levels = new LevelStore();
        wsg = new WorldStackGenerator(levels.getLevels());
    }

    @Test
    public void testBasic() {
        ws = wsg.generateWorldStack();
        assertEquals(3, ws.getNumberOfWorlds());
    }

    @Test
    public void testSeed() {
    	wsg.setGeneratorSeed(50);
        ws = wsg.generateWorldStack();
        assertEquals(50, ws.getWorldStack().get(0).getWorldSeed());
    }
    
    @Test
    public void testRowColumn() {
    	ws = wsg.generateWorldStack(20, 20);
    	assertEquals(20, ws.getWorldStack().get(0).getWorldRows());
    	assertEquals(20, ws.getWorldStack().get(0).getWorldColumns());
    }
}
