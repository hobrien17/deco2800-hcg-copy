package com.deco2800.hcg.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import com.deco2800.hcg.entities.enemy_entities.Squirrel;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.DemoWorld;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EnemyTest {
    Squirrel enemy;
    GameManager gameManager;
    DemoWorld demoWorld;
    PlayerManager playerManager;

    @Before
    public void createBasicEnemy() {
      // create enemy
      enemy = new Squirrel(5.0f,5.0f,0.0f, 0);
      // create mock game
      gameManager = GameManager.get();
      demoWorld = mock(DemoWorld.class);
      gameManager.setWorld(demoWorld);
      playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidArgument() {
        enemy = new Squirrel(5,5,0,-121);
    }

    @Test
    public void testHealth() {
        assertThat("Enemy did not start with health given.", enemy.getHealthCur(),
                is(equalTo(1000)));
        enemy.changeHealth(-600);
        assertThat("Enemy did not give expected value when health changed by -600.", enemy.getHealthCur(),
                is(equalTo(400)));
        enemy.changeHealth(100);
        assertThat("Enemy did not give expected value when health changed by 100.", enemy.getHealthCur(),
                is(equalTo(500)));
        enemy.changeHealth(-700);
        assertThat("Enemy did not give expected value when health changed by -700.", enemy.getHealthCur(),
                is(equalTo(0)));
    }

    @Test
    public void testLoot() {
        enemy.setupLoot();
        assertThat("Loot rarity not valid.", enemy.checkLootRarity(), is(equalTo(true)));
        assertThat("Basic enemy should only have 1 type of loot.", enemy.getLoot().length,
                is(equalTo(1)));
        assertThat("Basic Enemy only has 1 drop.", enemy.loot().length, is(equalTo(1)));
        Map<String, Double> expectedRarity = new HashMap<>();
        expectedRarity.put("sunflower_seed", 1.0);
        assertThat("Loot rarity should only have sunflower seed", enemy.getRarity(), is(equalTo(expectedRarity)));
        assertThat("Item should be sunflower seed", enemy.randItem(), is(equalTo("sunflower_seed")));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTakeDamageException() {
        enemy.takeDamage(-100);
    }
    
    @Test 
    public void testTakeDamangeNoHealthLeft() {
        enemy.takeDamage(2000);
        assertThat("Enemy health is not reduced to 0", enemy.getHealthCur(), is(equalTo(0)));
    }
    
    @Test
    public void testTakeDamageNormalCase() {
        enemy.takeDamage(100);
        assertThat("Enemy health is not 900 after taking the damage", enemy.getHealthCur(), is(equalTo(900)));
    }
    
    @Test
    public void testOtherGetterMethods() {
        assertThat("Enemy ID is not equal the given ID", enemy.getID(), is(equalTo(0)));
        assertThat("Enemy Strength is not what it is given", enemy.getStrength(), is(equalTo(5)));
        assertThat("Enemy level is not what is given", enemy.getLevel(), is(equalTo(1)));
    }
    
    @Test
    public void testSetLevel() {
        enemy.setLevel(2);
        assertThat("Enemy level is not changed", enemy.getLevel(), is(equalTo(2)));
    }

    @Test
    public void testStatus() {
        enemy.setStatus(1);
        assertThat("Status was not status given", enemy.getStatus(), is(equalTo(1)));
    }
    @Test
    public void testMovement() {
        enemy.setMove(3,3);
        assertThat("PosX is not the given position", enemy.getPosX(), is(equalTo(3.0f)));
        assertThat("PosY is not the given position", enemy.getPosY(), is(equalTo(3.0f)));
        gameManager.getWorld().addEntity(enemy);
        Player player = new Player(0, 0, 0);
        gameManager.getWorld().addEntity(player);
        playerManager.setPlayer(player);
        enemy.onTick(0);
        assertThat("Status was not status given", enemy.getStatus(), is(equalTo(2)));
        assertThat("Player PosX was incorrect", enemy.getLastPlayerX(), is(equalTo(0.0f)));
        assertThat("Player PosY was incorrect", enemy.getLastPlayerY(), is(equalTo(0.0f)));
    }
}
