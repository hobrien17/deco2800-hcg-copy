package com.deco2800.hcg.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.entities.enemyentities.Crab;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;

public class CrabTest {
    Crab enemy;
    GameManager gameManager;
    
    World AbstractWorld;
    PlayerManager playerManager;

    @Before
    public void createBasicEnemy() {
      // create new crab enemy
      enemy = new Crab(5.0f,5.0f,0.0f, 0);
      // create mock game
      gameManager = GameManager.get();
      AbstractWorld = mock(World.class);
      gameManager.setWorld(AbstractWorld);
      playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
    }
    
    @Test
    public void testLoot() {
        enemy.setupLoot();
        assertThat("Crab only has 1 drop.", enemy.getLoot().size(), is(equalTo(1)));
        assertThat("Item should be water seed", enemy.randItem(), is(equalTo(new LootWrapper("water_seed", 1.0f))));
    }
}
