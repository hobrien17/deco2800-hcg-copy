package com.deco2800.hcg.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * The ShopKeeper class represents a shop keeper in the game
 */
public class ShopKeeper implements Selectable{


    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void deselect() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Button getButton() {
        return null;
    }

    @Override
    public void buttonWasPressed() {
        throw new UnsupportedOperationException();
    }
}
