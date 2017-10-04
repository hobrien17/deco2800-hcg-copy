package com.deco2800.hcg.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.BulletType;
import com.deco2800.hcg.entities.bullets.GrassBullet;

public class GrassBulletTest {
	private GrassBullet grassBullet;
	private AbstractEntity abstractEntity;


	@Before
	public void setUp() {
		grassBullet = new GrassBullet(0,1,2,5,6,7,abstractEntity);
	}

	@Test
	public void getTypeTest(){
		Assert.assertEquals("grass Bullet type is not enum grass",
				grassBullet.getBulletType(), BulletType.GRASS);
	}	
	
	// @ToDo this is derp test. need to improve
}
