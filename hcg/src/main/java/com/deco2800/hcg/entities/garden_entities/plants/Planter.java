package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.observers.KeyUpObserver;

public class Planter implements KeyUpObserver {

	@Override
	public void notifyKeyUp(int keycode) {
		if(keycode == 8) {
			System.out.println("You pressed the 1 key!");
		}
	}

}
