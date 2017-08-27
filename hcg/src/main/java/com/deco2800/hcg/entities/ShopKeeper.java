package com.deco2800.hcg.entities;

import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.trading.Shop;

public class ShopKeeper implements Selectable{
	boolean selected;

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void deselect() {
    	selected = false;
    }

    @Override
    public Button getButton() {
        return null;
    }

    @Override
    public void buttonWasPressed() {
    	
    }
}
