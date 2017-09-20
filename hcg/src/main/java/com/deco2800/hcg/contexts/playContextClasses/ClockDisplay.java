package com.deco2800.hcg.contexts.playContextClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.deco2800.hcg.managers.*;


/**
 * Context representing the playable game itself. Most of the code here was
 * lifted directly out of Hardcor3Gard3ning.java PlayContext should only be
 * instantiated once.
 */
public class ClockDisplay extends Group {
    // Managers used by the display
    private GameManager gameManager;
    private TimeManager timeManager;

    private Label clockLabel;
    private Label dateLabel;
    private Skin skin;

    public ClockDisplay() {
        super();
        gameManager = GameManager.get();
        timeManager = (TimeManager) gameManager.getManager(TimeManager.class);
        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        /* Add clock. */
        Image clockImage = new Image(new
                Texture(Gdx.files.internal("resources/ui/clock_outline.png")));
        // clockImage.setPosition(stage.getWidth() - 215, 10);
        clockLabel = new Label(timeManager.getTime(), skin);
        dateLabel = new Label(timeManager.getDate(), skin);
        timeManager.setTimeLabel(clockLabel);
        timeManager.setDateLabel(dateLabel);

        /* Create clock GUI and add it to the stage */
        this.addActor(clockImage);
        clockLabel.setPosition(58, 95);
        clockLabel.setFontScale((float)2.1);
        dateLabel.setPosition(65, 60);
        dateLabel.setFontScale((float)0.9);
        this.addActor(clockLabel);
        this.addActor(dateLabel);

    }
}