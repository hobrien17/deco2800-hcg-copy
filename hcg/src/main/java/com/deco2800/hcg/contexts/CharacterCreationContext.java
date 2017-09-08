package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * The PerksSelectionScreen represents a context where users can
 * select their player's perks
 */
public class CharacterCreationContext extends UIContext{

    private TextButton quitButton;
    private Label strengthLabel, vitalityLabel, agilityLabel, intellectLabel, charismaLabel, testLabel;
    private ImageButton strengthUp, strengthDown, vitalityUp, vitalityDown, agilityUp, agilityDown, intellectUp,
            intellectDown, charismaUp, charismaDown;
    private TextField characterName;
    private Table masterTable, topRowInfoTable, attributesTable, skillsTable, statsTable, characterPreviewTable,
            selectedDescriptionTable;
    private SelectBox characterSex;
    private Window attributesWindow;

    private String[] sexes;

    /**
     * Creates a new PerksSelectionScreen
     */
    public CharacterCreationContext() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        sexes = new String[]{"Male", "Female"};

        masterTable = new Table(skin);
        masterTable.setFillParent(true);
        masterTable.setBackground("white");

        //Declaring subtables/sub windows
        topRowInfoTable = new Table(skin);
        attributesWindow = new Window("Attributes", skin);

        //Setting up top row info
        quitButton = new TextButton("Quit", skin);
        characterName = new TextField("Enter Name", skin);
        characterSex = new SelectBox(skin);
        characterSex.setItems(sexes);

        topRowInfoTable.add(characterName);
        topRowInfoTable.add(characterSex);
        topRowInfoTable.add(quitButton);

        //Setting up attributes table
        strengthLabel = new Label("Strength", skin);
        vitalityLabel = new Label("Vitality", skin);
        agilityLabel = new Label("Agility", skin);
        intellectLabel = new Label("Intellect", skin);
        charismaLabel = new Label("Charisma", skin);

        //strengthUp = new ImageButton()

        attributesWindow.add(strengthLabel);
        attributesWindow.add(vitalityLabel);
        attributesWindow.add(agilityLabel);
        attributesWindow.add(intellectLabel);
        attributesWindow.add(charismaLabel);

        //add subtables to masterTable
       // topRowInfoTable.top();
        //testLabel = new Label("TEST", skin);
       // masterTable.add(testLabel).top();
      //  masterTable.row();
        masterTable.add(topRowInfoTable).expand().top().left();
        masterTable.row();
        masterTable.add(attributesWindow);

        stage.addActor(masterTable);

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
    }

}
