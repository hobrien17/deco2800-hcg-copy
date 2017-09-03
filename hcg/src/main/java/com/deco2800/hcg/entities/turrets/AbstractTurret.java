package com.deco2800.hcg.entities.turrets;

import java.util.Observer;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;

public abstract class AbstractTurret implements Observer {
	
	private String name;
	private Corpse master;
	
	public AbstractTurret(Corpse master, String name) {
		this.name = name;
		this.master = master;
		StopwatchManager manager = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
	}
	
	public Corpse getCorpse() {
		return master;
	}
	
	public abstract String getThisTexture();
	
}
