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
    private TextButton strengthUp, strengthDown, vitalityUp, vitalityDown, agilityUp, agilityDown, intellectUp,
            intellectDown, charismaUp, charismaDown;
    private TextField characterName;
    private Table masterTable, topRowInfoTable, attributesTable, skillsTable, statsTable, characterPreviewTable,
            selectedDescriptionTable;
    private SelectBox characterSex;
    private Window attributesWindow;
    private String[] sexes = new String[]{"Male", "Female"};
    private Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

    GameManager gameManager;
    ContextManager contextManager;
    TextureManager textureManager;
    /**
     * Creates a new character creation screen
     */
    public CharacterCreationContext() {
        getManagers();
        initMasterTable();
        initSubTables();
        setupTopRowInfo();
        setupAttributesWindow();
        addSubtables();
    }

    private void getManagers() {
        gameManager = GameManager.get();
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
    }

    private void initMasterTable() {
        masterTable = new Table(skin);
        masterTable.setFillParent(true);
        masterTable.setBackground("white");
        masterTable.top().left();
        stage.addActor(masterTable);
    }

    //Declaring subtables/sub windows
    private void initSubTables() {
        topRowInfoTable = new Table(skin);
        attributesWindow = new Window("Attributes", skin);
    }

    //Setting up top row info
    private void setupTopRowInfo() {
        quitButton = new TextButton("Quit", skin);
        characterName = new TextField("Enter Name", skin);
        characterSex = new SelectBox(skin);
        characterSex.setItems(sexes);

        topRowInfoTable.add(characterName);
        topRowInfoTable.add(characterSex).expandX();
        topRowInfoTable.add(quitButton).top().right();

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
    }

    private void setupAttributesWindow() {
        strengthLabel = new Label("Strength: 1", skin);
        vitalityLabel = new Label("Vitality: 1", skin);
        agilityLabel = new Label("Agility: 1", skin);
        intellectLabel = new Label("Intellect: 1", skin);
        charismaLabel = new Label("Charisma: 1", skin);

        strengthDown = new TextButton("Down", skin);
        vitalityDown = new TextButton("Down", skin);
        agilityDown = new TextButton("Down", skin);
        intellectDown = new TextButton("Down", skin);
        charismaDown = new TextButton("Down", skin);
        strengthUp = new TextButton("Up", skin);
        vitalityUp = new TextButton("Up", skin);
        agilityUp = new TextButton("Up", skin);
        intellectUp = new TextButton("Up", skin);
        charismaUp = new TextButton("Up", skin);

        attributesWindow.add(strengthDown);
        attributesWindow.add(strengthLabel);
        attributesWindow.add(strengthUp);
        attributesWindow.row();
        attributesWindow.add(vitalityDown);
        attributesWindow.add(vitalityLabel);
        attributesWindow.add(vitalityUp);
        attributesWindow.row();
        attributesWindow.add(agilityDown);
        attributesWindow.add(agilityLabel);
        attributesWindow.add(agilityUp);
        attributesWindow.row();
        attributesWindow.add(intellectDown);
        attributesWindow.add(intellectLabel);
        attributesWindow.add(intellectUp);
        attributesWindow.row();
        attributesWindow.add(charismaDown);
        attributesWindow.add(charismaLabel);
        attributesWindow.add(charismaUp);
    }

    private void addSubtables() {
        topRowInfoTable.top().left();
        masterTable.add(topRowInfoTable);//.top().left().expandX();
        masterTable.row();
        masterTable.add(attributesWindow).top().left();
    }

}
