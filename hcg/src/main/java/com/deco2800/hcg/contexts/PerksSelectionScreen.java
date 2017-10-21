package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.actors.DrawablePerk;
import com.deco2800.hcg.buffs.Perk;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;

import java.util.ArrayList;

/**
 * The PerksSelectionScreen represents a context where users can
 * select their player's perks
 */
public class PerksSelectionScreen extends UIContext {

    private Table masterTable;
    private Button perkExit;
    private Label branch1Label;
    private Label branch2Label;
    private Label branch3Label;
    private HorizontalGroup perksGroups;
    private VerticalGroup branch1;
    private VerticalGroup branch2;
    private VerticalGroup branch3;
    private ArrayList<DrawablePerk> drawablePerks;
    private Player player;

    /**
     * Creates a new PerksSelectionScreen
     */
    public PerksSelectionScreen() {

        // Get necessary managers and skin
        GameManager gameManager = GameManager.get();
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        player = playerManager.getPlayer();
        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        //exit button
        perkExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());
        perkExit.setPosition(0, stage.getHeight() - perkExit.getHeight());

        //creating tables to add perks too.
        masterTable = new Table();
        masterTable.setFillParent(true);
        masterTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());
        

        branch1 = new VerticalGroup();
        branch1.setPosition(100f, stage.getHeight() - 100f);
        branch2 = new VerticalGroup();
        branch1.setPosition(100f + stage.getWidth()/3, stage.getHeight() - 100f);
        branch3 = new VerticalGroup();
        branch3.setPosition(100f + 2 * stage.getHeight() / 3, stage.getHeight() - 100f);
        
        branch1Label = new Label("Druid", skin);
        branch2Label = new Label("Survivalist",skin);
        branch3Label = new Label("Fungal Fanatic", skin);
        branch1Label.setFontScale(1.4f);
        branch2Label.setFontScale(1.4f);
        branch3Label.setFontScale(1.4f);

        //creating drawable perks
        drawablePerks = new ArrayList<>();
        DrawablePerk perk1 = new DrawablePerk(player.getPerk(Perk.perk.BRAMBLE_AM), stage, player);
        drawablePerks.add(perk1);
        DrawablePerk perk2 = new DrawablePerk(player.getPerk(Perk.perk.BRAMBLE_AM), stage, player);
        drawablePerks.add(perk2);
        DrawablePerk perk3 = new DrawablePerk(new Perk((Perk.perk.BRAMBLE_AM)), stage, player);
        drawablePerks.add(perk3);

        //path separators
        Image separator1 = new Image(textureManager.getTexture("path_separator"));
        separator1.setPosition(branch1.getWidth() + branch1.getX(), stage.getHeight()/3f);
        Image separator2 = new Image(textureManager.getTexture("path_separator"));
        separator2.setPosition (branch2.getWidth() + branch2.getX(), stage.getHeight()/3f);

        //adding perks to tables
        //branch1
        branch1.columnCenter();
        branch1.top();
        branch1.addActor(branch1Label);
        branch1.center();
        branch1.addActor(perk2.getPerkDisplay());

        //branch2.addActor( branch2Label);
        //branch2.row();
        //branch2.add(perk1.getPerkDisplay());

       // branch3.addActor( branch3Label);
        //branch3.row();
        //branch3.add(perk3.getPerkDisplay());

        //adding pathway tables to master
        masterTable.left();
        masterTable.add(branch1);
        masterTable.debugAll();
        //masterTable.addActor(separator1);
        masterTable.addActor(branch2);
        masterTable.addActor(separator2);
        //masterTable.addActor(branch3);
        branch1.debug();

        masterTable.debug();

        //adding master table and exit button to stage
        stage.addActor(masterTable);
        stage.addActor(perkExit);

        perkExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

        //this.update();
    }

    void update(){
        for(DrawablePerk perk: drawablePerks) {
            perk.update();
        }
    }

}
