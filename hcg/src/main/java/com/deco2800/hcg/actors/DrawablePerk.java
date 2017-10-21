package com.deco2800.hcg.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.buffs.Perk;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

public class DrawablePerk {
    private Perk perk;
    private Label levelLabel;
    private Image border;
    private ImageButton perkImage;
    private Group perkDisplay;
    private TextureManager textureManager;
    private Label descriptionLabel;
    private Stage stage;
    private Player player;

    public DrawablePerk(Perk perk, Stage stage, Player player) {

        //Getting nessecary managers and skin
        GameManager gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        this.player = player;
        //getting images from texture manager and setting label text.
        String textureName = perk.getName().replaceAll(" ", "_");
        this.perkImage = new ImageButton(new Image(textureManager.getTexture(textureName)).getDrawable());
        this.border = new Image(textureManager.getTexture("perk_border_inactive"));
        this.levelLabel = new Label(("" + perk.getCurrentLevel() + " / " + perk.getMaxLevel()), skin);

        //other fields for handling when the perk is clicked
        this.stage = stage;
        this.perk = perk;

        //positioning and scale
        border.setPosition(0, 20);
        perkImage.setPosition(20, 40);
        levelLabel.setPosition(45, 0);
        descriptionLabel = new Label(perk.getDescription(), skin);
        descriptionLabel.setFontScale(1.3f);

        //grouping ui elements to display them
        this.perkDisplay = new Group();
        perkDisplay.addActor(border);
        perkDisplay.addActor(levelLabel);
        perkDisplay.addActor(perkImage);
        perkDisplay.setColor(1f, 1f, 1f, 0.5f);

        //Handler to increase level of perk if its availiable and clicked on with left mouse button
        perkImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (perk.isAvaliable(player)) {
                    perk.setCurrentLevel(perk.getCurrentLevel() + 1);
                    DrawablePerk.this.update();
                }
            }
        });

        //Listener to decrease level of perk if its already level 1 or above, and click on with right mouse button
        perkImage.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (perk.isAvaliable(player)) {
                    perk.setCurrentLevel(perk.getCurrentLevel() - 1);
                    DrawablePerk.this.update();
                }
            }
        });

        //Listeners to display information about perks whilst they are hovered over by player
        perkImage.addListener(new ClickListener(-1) {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
                // description information about what leveling up the perk does
                descriptionLabel.setPosition(50f, 50f);
                stage.addActor(descriptionLabel);

                //label to tell the player their level is too low to unlock the perk
                if (!(perk.isAvaliable(player))) {
                    levelLabel.setText("Level Too Low");
                    levelLabel.setColor(Color.RED);
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //player is no longer hovering over the perk, so stop displaying description
                descriptionLabel.remove();
                DrawablePerk.this.update();

            }
        });

        this.update();
    }

    /**
     * Method to get the group of elements used to display the perk
     * @return
     * group containing all required ui elements to properly display the perk
     */
    public Group getPerkDisplay() {
        return perkDisplay;
    }

    /**
     * Updates the displayed information in the DrawablePerk
     */
    public void update() {
        levelLabel.setText("" + perk.getCurrentLevel() + " / " + perk.getMaxLevel());
        levelLabel.setColor(Color.WHITE);

        if (perk.isAvaliable(player)) {
            perkDisplay.setColor(1f, 1f, 1f, 1f);
            border.setColor(0.8f, 0.8f, 0.8f, 1f);
            border.setDrawable(new Image(textureManager.getTexture("perk_border_inactive")).getDrawable());
        }

        if (perk.isActive()) {
            border.setDrawable(new Image(textureManager.getTexture("perk_border_inactive")).getDrawable());
            border.setColor(1f, 0.9f, 0f, 1f);
        }
        if (perk.isMaxed()) {
            levelLabel.setColor(1f, 0.9f, 0f, 1f);
            border.setDrawable(new Image(textureManager.getTexture("perk_border_maxed")).getDrawable());
        }
    }
}
