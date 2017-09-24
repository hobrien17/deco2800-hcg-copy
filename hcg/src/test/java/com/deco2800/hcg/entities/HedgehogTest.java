package com.deco2800.hcg.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.BaseTest;
import com.deco2800.hcg.entities.enemyentities.Hedgehog;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

import com.deco2800.hcg.worlds.World;

public class HedgehogTest extends BaseTest {
    Hedgehog enemy;
    GameManager gameManager;
    
    World AbstractWorld;
    PlayerManager playerManager;

    @Before
    public void createBasicHedgehog() {
      // create a new hedgehog
      enemy = new Hedgehog(5.0f, 5.0f, 0.0f, 0);
      // create mock game
      gameManager = GameManager.get();
      AbstractWorld = mock(World.class);
      gameManager.setWorld(AbstractWorld);
      playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
    }
    
    @Test
    public void testSetHedgeHogStatus() {
        Player player = new Player(0, 0, 0);
        gameManager.getWorld().addEntity(player);
        playerManager.setPlayer(player);
        enemy.setHedgehogStatus();
        assertThat("Status is incorrect", enemy.getStatus(), is(equalTo(2)));
    }
    
    @Test
    public void testGetChargeStatus() {
        assertThat("Get status method is incorrect", enemy.getChargeStatus(), is(equalTo(false)));
    }
    
    @Test
    public void testChargeStatus() {
        enemy.setChargeStatus(true);
        assertThat("ChargedAtPlayer status is wrong", enemy.getChargeStatus(), is(equalTo(true)));
    }
    
    @Test
    public void testLoot() {
        enemy.setupLoot();
        assertThat("Hedgehog only has 1 drop.", enemy.loot().length, is(equalTo(1)));
        assertThat("Item should be explosive seed", enemy.randItem(), is(equalTo("explosive_seed")));
    }
}
