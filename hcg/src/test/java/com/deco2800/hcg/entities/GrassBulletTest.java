package com.deco2800.hcg.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.entities.bullets.BulletType;
import com.deco2800.hcg.entities.bullets.GrassBullet;

public class GrassBulletTest {
	private GrassBullet grassBullet;
	private AbstractEntity abstractEntity;

	static final double EPSILON = 0.00001;
	
	@Before
	public void setUp() {
		grassBullet = new GrassBullet(0,1,2,5,6,7,abstractEntity);
	}

	@Test
	public void intialiseTest(){
		Assert.assertEquals(grassBullet.getPosX(), 0, EPSILON);
		Assert.assertEquals(grassBullet.getPosY(), 1, EPSILON);
		Assert.assertEquals(grassBullet.getPosZ(), 2, EPSILON);
		
		//Assert.assertEquals(grassBullet.getZLength(), 6, EPSILON);
		//Assert.assertEquals(grassBullet.getYLength(), 6, EPSILON);
		//Assert.assertEquals(grassBullet.getZLength(), 7, EPSILON);
		
		Assert.assertEquals("grass Bullet type is not enum grass",
				grassBullet.getBulletType(), BulletType.GRASS);
	}	
	
	
	// @ToDo this is derp test. need to improve
}
