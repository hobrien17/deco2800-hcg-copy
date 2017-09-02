package com.deco2800.hcg.entities.turrets;

import java.util.Observer;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;

public abstract class AbstractTurret extends AbstractEntity implements Observer {
	
	private String name;
	
	public AbstractTurret(String name) {
		super(5, 5, 0, 0.7f, 0.7f, 1, 1f, 1f, false);
		this.name = name;
		StopwatchManager manager = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
	}
	
}
