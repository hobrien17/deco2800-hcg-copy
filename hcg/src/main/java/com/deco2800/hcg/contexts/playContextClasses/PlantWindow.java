package com.deco2800.hcg.contexts.playcontextclasses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;

public class PlantWindow extends Window{
    private GameManager gameManager;
    private PlantManager plantManager;

    public PlantWindow(Skin skin)  {
        super("Plants",skin);
        gameManager = GameManager.get();
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);

        // Create the window for plant.
        plantManager.setPlantWindow(this, skin);
        plantManager.updateLabel();
        Button closeButton = new Button(skin.getDrawable("button-close"));
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                plantManager.setWindowVisible(false);
            }
        });
        closeButton.setColor(Color.GREEN);
        this.getTitleTable().add(closeButton);
        this.setMovable(false);
    }
}
