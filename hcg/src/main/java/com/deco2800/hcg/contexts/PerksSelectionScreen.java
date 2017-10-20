package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.buffs.Perk;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * The PerksSelectionScreen represents a context where users can
 * select their player's perks
 */
public class PerksSelectionScreen extends UIContext{

    private Table masterTable;
    private Table branch1;
    private Table branch2;
    private Table branch3;

    private class DrawablePerk {
        private Perk perk;
        private Label levelLabel;
        private Image border;
        private ImageButton perkImage;
        private Group perkDisplay;
        private TextureManager textureManager;

        private DrawablePerk(Perk perk) {

            GameManager gameManager = GameManager.get();
            ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
            textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
            Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
            String textureName = perk.getName().replaceAll(" ", "_");

            this.perk = perk;
            this.levelLabel = new Label("" + perk.getCurrentLevel() + " / " + perk.getMaxLevel(), skin);
            this.perkImage = new ImageButton(new Image(textureManager.getTexture(textureName)).getDrawable());
            this.border = new Image(textureManager.getTexture("perk_border_inactive"));
            this.perkDisplay = new Group();

            border.setPosition(0, 20);
            perkImage.setPosition(20, 40);
            levelLabel.setPosition(45, 0);

            perkDisplay.addActor(border);
            perkDisplay.addActor(levelLabel);
            perkDisplay.addActor(perkImage);
            perkDisplay.setColor(1f, 1f, 1f, 0.5f);

            perkImage.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    DrawablePerk.this.update();
                    contextManager.popContext();
                }
            });
        }
        public Group getPerkDisplay() {
            return perkDisplay;
        }

        public void update(){
            if (perk.isAvaliable()) {
                perkDisplay.setColor(1f, 1f, 1f, 1f);
            }

            if(perk.isActive()) {
                border.setColor(1f,0.9f,0f,1f);
            }
            if (perk.isMaxed()) {
                levelLabel.setColor(1f,0.9f,0f,1f);
                border.setDrawable(new Image(textureManager.getTexture("perk_border_maxed")).getDrawable());
            }

        }
    }

    /**
     * Creates a new PerksSelectionScreen
     */
    public PerksSelectionScreen() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        masterTable = new Table(skin);
        masterTable.setFillParent(true);
        masterTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());

        branch1 = new Table(skin);
        branch2 = new Table(skin);
        branch3 = new Table(skin);

        DrawablePerk perk1 = new DrawablePerk(new Perk(Perk.perk.BRAMBLE_AM));
        branch2.add(perk1.getPerkDisplay());

        masterTable.add(branch1);
        masterTable.add(branch2);
        masterTable.add(branch3);
        stage.addActor(masterTable);
    }

}
