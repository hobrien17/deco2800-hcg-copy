package com.deco2800.hcg.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EnemyTest {
    Squirrel enemy;

    @Before
    public void createBasicEnemy() {
      // create enemy
      enemy = new Squirrel(5,5,0, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidArgument() {
        enemy = new Squirrel(5,5,0,-121);
    }

    @Test
    public void testHealth() {
        assertThat("Enemy did not start with health given.", enemy.getHealth(),
                is(equalTo(1000)));
        enemy.changeHealth(-600);
        assertThat("Enemy did not give expected value when health changed by -600.", enemy.getHealth(),
                is(equalTo(400)));
        enemy.changeHealth(100);
        assertThat("Enemy did not give expected value when health changed by 100.", enemy.getHealth(),
                is(equalTo(500)));
        enemy.changeHealth(-700);
        assertThat("Enemy did not give expected value when health changed by -700.", enemy.getHealth(),
                is(equalTo(0)));
    }

    @Test
    public void testLoot() {
        enemy.setupLoot();
        assertThat("Loot rarity not valid.", enemy.checkLootRarity(), is(equalTo(true)));
        assertThat("Basic enemy should only have 1 type of loot.", enemy.getLoot().length,
                is(equalTo(1)));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTakeDamageException() {
        enemy.takeDamage(-100);
    }
    
    @Test 
    public void testTakeDamangeNoHealthLeft() {
        enemy.takeDamage(2000);
        assertThat("Enemy health is not reduced to 0", enemy.getHealth(), is(equalTo(0)));
    }
    
    @Test
    public void testTakeDamageNormalCase() {
        enemy.takeDamage(100);
        assertThat("Enemy health is not 900 after taking the damage", enemy.getHealth(), is(equalTo(900)));
    }
    
    @Test
    public void testOtherGetterMethods() {
        assertThat("Enemy ID is not equal the given ID", enemy.getID(), is(equalTo(1)));
        assertThat("Enemy Strength is not what it is given", enemy.getStrength(), is(equalTo(5)));
        assertThat("Enemy level is not what is given", enemy.getLevel(), is(equalTo(1)));
    }
    
    @Test
    public void testSetLevel() {
        enemy.setLevel(2);
        assertThat("Enemy level is not changed", enemy.getLevel(), is(equalTo(2)));
    }
}
