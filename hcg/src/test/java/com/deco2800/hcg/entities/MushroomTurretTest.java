package com.deco2800.hcg.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.managers.TimeManager;
import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.entities.enemyentities.MushroomTurret;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;

public class MushroomTurretTest {
    MushroomTurret enemy;
    GameManager gameManager;
    
    World AbstractWorld;
    PlayerManager playerManager;

    @Before
    public void createBasicEnemy() {
      // create new mushroom turret enemy
      enemy = new MushroomTurret(5.0f,5.0f,0.0f, 0);
      // create mock game
      gameManager = GameManager.get();
      AbstractWorld = mock(World.class);
      gameManager.setWorld(AbstractWorld);
      playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
    }
    
    @Test
    public void testLoot() {
        enemy.setupLoot();
        assertThat("MushroomTurret only has 1 drop.", enemy.loot().length, is(equalTo(1)));
        assertThat("Item should be fire seed", enemy.randItem(), is(equalTo("fire_seed")));
    }

    @Test
    public void testTimer() {
        StopwatchManager stopwatchManager = (StopwatchManager) gameManager.getManager(StopwatchManager.class);
        TimeManager timeManager = (TimeManager) gameManager.getManager(TimeManager.class);
        gameManager.getWorld().addEntity(enemy);
        for (int i = 0; i < 4; i++) {
            assertThat("MushroomTurret texture incorrect.", enemy.getTexture(), is(equalTo("mushroom")));
            enemy.update(stopwatchManager, i);
        }
        assertThat("MushroomTurret texture incorrect.", enemy.getTexture(), is(equalTo("tower")));
    }
}
