package com.deco2800.hcg.entities;

import static org.junit.Assert.assertEquals;

import com.deco2800.hcg.entities.Squirrel;
import org.junit.Before;
import org.junit.Test;

public class EnemyTest {
    Squirrel enemy;

    @Before
    public void createBasicEnemy() {
        // create enemy
        enemy = new Squirrel(5,5,0, 1);
    }

    @Test
    public void testHealth() {
        assertEquals(1000, enemy.getHealth());
        enemy.changeHealth(-600);
        assertEquals(400, enemy.getHealth());
        enemy.changeHealth(100);
        assertEquals(500, enemy.getHealth());
        enemy.changeHealth(-700);
        assertEquals(0, enemy.getHealth());
    }
}
