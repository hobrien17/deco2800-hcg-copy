package com.deco2800.hcg.contexts.playContextClasses;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;

public class PlantWindow extends Window{
    private GameManager gameManager;
    private PlantManager plantManager;

    public PlantWindow(Skin skin)  {
        super("Plants",skin);
        gameManager = GameManager.get();
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);

        /* Create the window for plant. */
        plantManager.setPlantWindow(this, skin);
        plantManager.updateLabel();
        this.setMovable(false);
    }
}
