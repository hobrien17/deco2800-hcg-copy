package com.deco2800.hcg.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.entities.enemyentities.Snail;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;

public class SnailTest {
    Snail enemy;
    GameManager gameManager;
    
    World AbstractWorld;
    PlayerManager playerManager;

    @Before
    public void createBasicEnemy() {
      /*
      enemy = new Snail(5.0f,5.0f,0.0f, 0);
      // create mock game
      gameManager = GameManager.get();
      AbstractWorld = mock(World.class);
      
      TiledMap map = mock(TiledMap.class);
      when(AbstractWorld.getMap()).thenReturn(map);
      when(map.getLayers()).thenReturn(new MapLayers());
      
      gameManager.setWorld(AbstractWorld);
      playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
      */
    }
    
    @Test
    public void testLoot() {
        //enemy.setupLoot();
        //assertThat("MushroomTurret only has 1 drop.", enemy.loot().length, is(equalTo(1)));
        //assertThat("Item should be grass seed", enemy.randItem(), is(equalTo("grass_seed")));
    }
}
