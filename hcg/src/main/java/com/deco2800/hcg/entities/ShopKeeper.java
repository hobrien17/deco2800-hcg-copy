package com.deco2800.hcg.entities;

import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.trading.Shop;

public class ShopKeeper implements Selectable{


    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void deselect() {

    }

    @Override
    public Button getButton() {
        return null;
    }

    @Override
    public void buttonWasPressed() {
    	
    }
}
