package com.deco2800.hcg.contexts.playContextClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.deco2800.hcg.entities.garden_entities.plants.Planter;
import com.deco2800.hcg.items.*;


public class RadialDisplay extends Group {

    private PlantManager plantManager;
    private TextureManager textureManager;
    private GameManager gameManager;
    private Planter planter;

    private ImageButton normalButton;
    private ImageButton explosiveButton;
    private ImageButton fireButton;
    private ImageButton grassButton;
    private ImageButton iceButton;
    private ImageButton waterButton;
    private ImageButton xButton;
    private ImageButton fertiliserButton;
    private ImageButton sprayButton;
    private ImageButton normalButtonHover;
    private ImageButton explosiveButtonHover;
    private ImageButton fireButtonHover;
    private ImageButton grassButtonHover;
    private ImageButton iceButtonHover;
    private ImageButton waterButtonHover;
    private ImageButton xButtonHover;
    private ImageButton fertiliserButtonHover;
    private ImageButton sprayButtonHover;
    private Image radialOutline;

    private Stage stageInput;
    private Skin skin;

    private Group radialDisplay;

    public RadialDisplay(Stage stage) {

        gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);
        planter = new Planter();
        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        stageInput = stage;

        radialOutline = new Image(textureManager.getTexture("radialOutline"));

        radialDisplay = new Group();

        normalButton = new ImageButton(new Image(textureManager.getTexture("normalButton")).getDrawable());
        explosiveButton = new ImageButton(new Image(textureManager.getTexture("explosiveButton")).getDrawable());
        fireButton = new ImageButton(new Image(textureManager.getTexture("fireButton")).getDrawable());
        grassButton = new ImageButton(new Image(textureManager.getTexture("grassButton")).getDrawable());
        iceButton = new ImageButton(new Image(textureManager.getTexture("iceButton")).getDrawable());
        waterButton = new ImageButton(new Image(textureManager.getTexture("waterButton")).getDrawable());
        xButton = new ImageButton(new Image(textureManager.getTexture("menuClose")).getDrawable());
        fertiliserButton = new ImageButton(new Image(textureManager.getTexture("fertiliserButton")).getDrawable());
        sprayButton = new ImageButton(new Image(textureManager.getTexture("sprayButton")).getDrawable());

        normalButtonHover = new ImageButton(new Image(textureManager.getTexture("normalButtonHover")).getDrawable());
        explosiveButtonHover = new ImageButton(new Image(textureManager.getTexture("explosiveButtonHover")).getDrawable());
        fireButtonHover = new ImageButton(new Image(textureManager.getTexture("fireButtonHover")).getDrawable());
        grassButtonHover = new ImageButton(new Image(textureManager.getTexture("grassButtonHover")).getDrawable());
        iceButtonHover = new ImageButton(new Image(textureManager.getTexture("iceButtonHover")).getDrawable());
        waterButtonHover = new ImageButton(new Image(textureManager.getTexture("waterButtonHover")).getDrawable());
        xButtonHover = new ImageButton(new Image(textureManager.getTexture("xButtonHover")).getDrawable());
        fertiliserButtonHover = new ImageButton(new Image(textureManager.getTexture("fertiliserButtonHover")).getDrawable());
        sprayButtonHover = new ImageButton(new Image(textureManager.getTexture("sprayButtonHover")).getDrawable());

        radialOutline.setSize(350, 350);
        radialOutline.setPosition(radialDisplay.getWidth() / 2f - radialOutline.getWidth() / 2f, radialDisplay.getHeight() / 2f - radialOutline.getHeight() / 2f);
        radialDisplay.setPosition(stageInput.getWidth() / 2, stageInput.getHeight() / 2);
        radialDisplay.addActor(radialOutline);

        xButton.setSize(220,220);
        normalButton.setSize(135, 135);
        grassButton.setSize(135, 135);
        explosiveButton.setSize(135, 135);
        iceButton.setSize(136, 135);
        waterButton.setSize(135, 135);
        fireButton.setSize(135, 135);
        sprayButton.setSize(135, 135);
        fertiliserButton.setSize(140, 136);

        normalButton.setPosition(radialDisplay.getWidth() / 2f - normalButton.getWidth() / 2f - 102, radialDisplay.getHeight() / 2f - normalButton.getHeight() / 2f - 60);
        explosiveButton.setPosition(radialDisplay.getWidth() / 2f - explosiveButton.getWidth() / 2f - 102, radialDisplay.getHeight() / 2f - explosiveButton.getHeight() / 2f + 61);
        fireButton.setPosition(radialDisplay.getWidth() / 2f - fireButton.getWidth() / 2f - 63f,radialDisplay.getHeight() - fireButton.getHeight() / 2f + 103f);
        grassButton.setPosition(radialDisplay.getWidth() / 2f - grassButton.getWidth() / 2f + 62f,radialDisplay.getHeight() - grassButton.getHeight() / 2f + 103f);
        iceButton.setPosition(radialDisplay.getWidth() / 2f - iceButton.getWidth() / 2f + 102, radialDisplay.getHeight() / 2f - iceButton.getHeight() / 2f + 60);
        waterButton.setPosition(radialDisplay.getWidth() / 2f - waterButton.getWidth() / 2f + 102, radialDisplay.getHeight() / 2f - waterButton.getHeight() / 2f - 60);
        xButton.setPosition(radialDisplay.getWidth() / 2f - xButton.getWidth() / 2f, radialDisplay.getHeight() / 2f - xButton.getHeight() / 2f);
        fertiliserButton.setPosition(radialDisplay.getWidth() / 2f - fertiliserButton.getWidth() / 2f + 63f,radialDisplay.getHeight() - fertiliserButton.getHeight() / 2f - 102f);
        sprayButton.setPosition(radialDisplay.getWidth() / 2f - sprayButton.getWidth() / 2f - 60f,radialDisplay.getHeight() - sprayButton.getHeight() / 2f - 103f);

        radialDisplay.addActor(normalButton);
        radialDisplay.addActor(explosiveButton);
        radialDisplay.addActor(fireButton);
        radialDisplay.addActor(grassButton);
        radialDisplay.addActor(iceButton);
        radialDisplay.addActor(waterButton);
        radialDisplay.addActor(xButton);
        radialDisplay.addActor(fertiliserButton);
        radialDisplay.addActor(sprayButton);

        xButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                radialDisplay.remove();
            }
        });

        normalButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                planter.notifyKeyUp(8);
                radialDisplay.remove();
            }
        });

        explosiveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                planter.notifyKeyUp(9);
                radialDisplay.remove();
            }
        });

        fireButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                planter.notifyKeyUp(12);
                radialDisplay.remove();
            }
        });

        grassButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                planter.notifyKeyUp(11);
                radialDisplay.remove();
            }
        });

        iceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                planter.notifyKeyUp(13);
                radialDisplay.remove();
            }
        });

        waterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                planter.notifyKeyUp(10);
                radialDisplay.remove();
            }
        });

        fertiliserButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //growthDelayRes = true;
                //if(growthDelayRes) {
                //	fertiliser.reduceGrowth(AbstractGardenPlant);
                //}
            }
        });

        sprayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //radialDisplay.remove();
            }
        });

    }

    public void addRadialMenu(Stage stage) {
        stage.addActor(radialDisplay);
    }

    public void removeRadialMenu() {
        radialDisplay.remove();
    }
}