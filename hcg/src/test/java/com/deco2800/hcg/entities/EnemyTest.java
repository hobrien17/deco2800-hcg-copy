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
}
