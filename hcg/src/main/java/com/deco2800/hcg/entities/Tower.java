package com.deco2800.hcg.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Tower that can do things.
 *
 * @author leggy
 */
public class Tower extends AbstractEntity implements Clickable, Tickable,
        Selectable {
  
    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    boolean selected = false;

    /**
     * Constructor for the base
     *
     * @param posX The x-coordinate.
     * @param posY The y-coordinate.
     * @param posZ The z-coordinate.
     */
    public Tower(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
        this.setTexture("tower");
    }

    /**
     * On click handler
     */
    @Override
    public void onClick() {
        LOGGER.info("Base got clicked");

        if (!selected) {
            selected = true;
        }
    }

    /**
     * On Tick handler
     *
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {

        if (selected) {
            this.setTexture("tree_selected");
        } else {
            this.setTexture("tower");
        }
    }

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
        Button button = new TextButton("Make Peon",
                new Skin(Gdx.files.internal("resources/uiskin.json")));
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonWasPressed();
            }
        });
        return button;
    }

    @Override
    public void buttonWasPressed() {
        LOGGER.info("Button was pressed for " + this);
    }
}
