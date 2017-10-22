package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import java.util.Arrays;
import java.util.HashMap;

/**
 * The PerksSelectionScreen represents a context where users can
 * select their player's perks
 */
public class PerksSelectionScreen extends UIContext {

    private GameManager gameManager;
    private TextureManager textureManager;
    private ContextManager contextManager;
    private PlayerManager playerManager;
    private Player player;
    private Skin skin;
    private Label perkPoints;

    private Group masterTable;
    private Group branch1;
    private Group branch2;
    private Group branch3;
    private Button perkExit;
    private Image branch1Label;
    private Image branch2Label;
    private Image branch3Label;
    private HashMap<Enum,DrawablePerk> drawablePerks;

    /**
     * Creates a new PerksSelectionScreen
     */
    public PerksSelectionScreen() {

        // Get necessary managers and skin
        gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        player = playerManager.getPlayer();
        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        //creating tables to add perks too.
        masterTable = new Group();
        createExitButton();
        createbranches();

        //setting background  Image
        Image backGroundImage = new Image(textureManager.getTexture("main_menu_background"));
        backGroundImage.setPosition(0, 0);
        backGroundImage.setSize(stage.getWidth(),stage.getHeight());
        masterTable.addActor(backGroundImage);

        masterTable.addActor(branch1);
        masterTable.addActor(branch2);
        masterTable.addActor(branch3);
        masterTable.addActor(perkPoints);

        stage.addActor(masterTable);
        stage.addActor(perkExit);


        this.update();
    }

    /**
     * updates the display
     */
    public void update() {
        perkPoints.setText("Points:" + player.getPerkPoints() + "/" + (player.getLevel() -1));
        perkPoints.layout();
    }

    /**
     * creates and sets the position and labels for the three perk branches
     */
    void createbranches(){

        branch1 = new Group();
        branch2 = new Group();
        branch3 = new Group();

        //size and position of branches
        branch1.setSize(stage.getWidth()/3f - 250f ,stage.getHeight()-200);
        branch2.setSize(stage.getWidth()/3f - 250f ,stage.getHeight()-200);
        branch3.setSize(stage.getWidth()/3f - 250f ,stage.getHeight()-200);
        branch1.setPosition(200f, 150);
        branch2.setPosition(100f + stage.getWidth()/3f, 150);
        branch3.setPosition(100f + 2 * stage.getWidth()/3f, 150);

        branch1Label = new Image (textureManager.getTexture("druid_title"));
        branch2Label = new Image (textureManager.getTexture("survivalist_title"));
        branch3Label = new Image (textureManager.getTexture("fungal_title"));
        perkPoints = new Label("Points:" + player.getPerkPoints() + "/" + (player.getLevel() - 1), skin);

        //scale and position of labels
        perkPoints.setScale(1.5f);
        perkPoints.setFontScale(1.7f);
        branch1Label.setPosition(branch1.getWidth()/2f - branch1Label.getWidth()/2f, branch1.getHeight()-50);
        branch2Label.setPosition(branch2.getWidth()/2f - branch2Label.getWidth()/2f, branch2.getHeight()-50);
        branch3Label.setPosition(branch3.getWidth()/2f - branch3Label.getWidth()/2f, branch3.getHeight()-50);
        perkPoints.setPosition ( 225f, stage.getHeight() - perkPoints.getHeight() - 20f);

        branch1.addActor(branch1Label);
        branch2.addActor(branch2Label);
        branch3.addActor(branch3Label);

        createPerks();
    }

    /**
     * creates the exit button
     */
    void createExitButton() {
        //exit button
        perkExit = new ImageButton(new Image(textureManager.getTexture("shop_exit")).getDrawable());
        perkExit.setPosition(0, stage.getHeight() - perkExit.getHeight());
        perkExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

    }

    /**
     * Creates the perks and displays them in their respective branch groups
     */
    void createPerks() {
        //creating drawable perks
        drawablePerks = new HashMap<>();

        //Druid
        ArrayList<Enum> druidPerks =  new ArrayList<>(Arrays.asList(Perk.perk.I_AM_GROOT, Perk.perk.SPLINTER_IS_COMING,
                Perk.perk.FULL_PETAL_ALCHEMIST, Perk.perk.GUNS_AND_ROSES));

        for (int i = 0; i < druidPerks.size(); i++) {
            //creating perk
            DrawablePerk drawablePerk = new DrawablePerk(player.getPerk((Perk.perk) druidPerks.get(i)), this);

            //setting its position relative to its position in the list and branch height
            drawablePerk.getPerkDisplay().setPosition(branch1.getWidth()/2f - 75f,
                     branch1.getHeight()*(float)(3-i)/4f - drawablePerk.getPerkDisplay().getHeight());
            //adding perk to display
            branch1.addActor(drawablePerk.getPerkDisplay());
        }

        //Survivalist
        ArrayList<Enum> survivalPerks =  new ArrayList<>(Arrays.asList(Perk.perk.RUN_FUNGUS_RUN, Perk.perk.HOLLY_MOLEY,
                Perk.perk.KALERATE, Perk.perk.THORN, Perk.perk.THE_FUNGAL_COUNTDOWN));

        for (int i = 0; i < survivalPerks.size(); i++) {
            //creating perk
            DrawablePerk drawablePerk = new DrawablePerk(player.getPerk((Perk.perk) survivalPerks.get(i)), this);

            //setting its position relative to its position in the list and branch height
            drawablePerk.getPerkDisplay().setPosition(branch1.getWidth()/2f - 75f,
                    branch1.getHeight()*(float)(3-0.75*i)/4f- drawablePerk.getPerkDisplay().getHeight());
            //adding perk to display
            branch2.addActor(drawablePerk.getPerkDisplay());
        }

        //Fungal Fanatic
        ArrayList<Enum> fungalPerks =  new ArrayList<>(Arrays.asList(Perk.perk.BRAMBLE_AM, Perk.perk.BUT_NOT_YEAST,
                Perk.perk.SAVING_GRAVES, Perk.perk.FUNGICIDAL_MANIAC));

        for (int i = 0; i < fungalPerks.size(); i++) {
            //creating perk
            DrawablePerk drawablePerk = new DrawablePerk(player.getPerk((Perk.perk) fungalPerks.get(i)), this);

            //setting its position relative to its position in the list and branch height
            drawablePerk.getPerkDisplay().setPosition(branch1.getWidth()/2f - 75f,
                    branch1.getHeight()*(float)(3-i)/4f- drawablePerk.getPerkDisplay().getHeight());
            //adding perk to display
            branch3.addActor(drawablePerk.getPerkDisplay());
        }


    }
    public Stage getStage(){
        return stage;
    }
    public Player getPlayer() {
        return player;
    }

}
