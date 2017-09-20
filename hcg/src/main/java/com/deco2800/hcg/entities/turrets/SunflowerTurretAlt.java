package com.deco2800.hcg.entities.turrets;

import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.corpse_entities.Corpse;

public class SunflowerTurretAlt extends SunflowerTurret {

	public SunflowerTurretAlt(Corpse master) {
		super(master);
		this.ammo = 3;
	}
	
	@Override
	public String getThisTexture() {
		return "sunflower_alt";
	}

}
