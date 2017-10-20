package com.deco2800.hcg.quests;

import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worldmapui.LevelStore;
import com.deco2800.hcg.worldmapui.MapGenerator;
import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class QuestManagerTest {

	
	@Test
	public void testConstructor(){
        QuestManager qm = new QuestManager();

        //Create the tutorial world
		LevelStore ls  = new LevelStore();
		MapGenerator mg = new MapGenerator(ls.getLevels());
		mg.setGeneratorSeed(31); //Tutorial world

		WorldMap wm = mg.generateWorldMap(1);
		GameManager.get().setWorldMap(wm);

        qm.loadAllQuests();
	}

}