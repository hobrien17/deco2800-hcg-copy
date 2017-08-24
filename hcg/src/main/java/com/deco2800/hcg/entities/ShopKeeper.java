package com.deco2800.hcg.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

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
