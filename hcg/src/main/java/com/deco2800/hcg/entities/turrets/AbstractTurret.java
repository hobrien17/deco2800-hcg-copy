package com.deco2800.hcg.entities.turrets;

import com.deco2800.hcg.entities.AbstractEntity;

public abstract class AbstractTurret extends AbstractEntity {
	
	private String name;
	
	public AbstractTurret(String name) {
		super(5, 5, 0, 0.7f, 0.7f, 1, 1f, 1f, false);
		this.name = name;
	}
	
}
