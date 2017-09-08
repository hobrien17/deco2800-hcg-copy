package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * The PerksSelectionScreen represents a context where users can
 * select their player's perks
 *
 * @author avryn
 */
public class CharacterCreationContext extends UIContext{

    private Label strengthLabel, vitalityLabel, agilityLabel, intellectLabel, charismaLabel, meleeSkillLabel,
            gunsSkillLabel, energyWeaponsSkillLabel;

    private Table masterTable, topRowInfoTable;

    private Window attributesWindow, skillsWindow, statsWindow, characterPreviewWindow, selectedDescriptionWindow;
    private String[] sexes = new String[]{"Male", "Female"};

    private int strength = 1, vitality = 1, agility = 1, intellect = 1, charisma = 1, meleeSkill = 10, gunsSkill = 10,
            energyWeaponsSkill = 10;

    private Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

    private GameManager gameManager;
    private ContextManager contextManager;
    private TextureManager textureManager;
    /**
     * Creates a new character creation screen
     */
    public CharacterCreationContext() {
        getManagers();
        initMasterTable();
        initSubTables();
        setupTopRowInfo();
        setupAttributesWindow();
        setupSkillsWindow();
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

    //Declaring sub-tables/sub-windows
    private void initSubTables() {
        topRowInfoTable = new Table(skin);
        attributesWindow = new Window("Attributes", skin);
        skillsWindow = new Window("Skills", skin);
    }

    //Setting up top row info
    private void setupTopRowInfo() {
        TextButton quitButton = new TextButton("Quit", skin);
        TextField characterName = new TextField("Enter Name", skin);
        SelectBox characterSex = new SelectBox(skin);
        characterSex.setItems(sexes);

        topRowInfoTable.add(characterName);
        topRowInfoTable.add(characterSex).expandX().left();
        topRowInfoTable.add(quitButton).right();

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
    }

    private void setupAttributesWindow() {
        strengthLabel = new Label("Strength: " + strength, skin);
        vitalityLabel = new Label("Vitality: " + vitality, skin);
        agilityLabel = new Label("Agility: " + agility, skin);
        intellectLabel = new Label("Intellect: " + intellect, skin);
        charismaLabel = new Label("Charisma: " + charisma, skin);

        TextButton strengthDown = new TextButton("Down", skin);
        TextButton vitalityDown = new TextButton("Down", skin);
        TextButton agilityDown = new TextButton("Down", skin);
        TextButton intellectDown = new TextButton("Down", skin);
        TextButton charismaDown = new TextButton("Down", skin);
        TextButton strengthUp = new TextButton("Up", skin);
        TextButton vitalityUp = new TextButton("Up", skin);
        TextButton agilityUp = new TextButton("Up", skin);
        TextButton intellectUp = new TextButton("Up", skin);
        TextButton charismaUp = new TextButton("Up", skin);

        // Add attribute labels and button to the window
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

        // Add listeners for buttons
        strengthDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (strength > 1) {
                    strength--;
                    strengthLabel.setText("Strength: " + strength);
                }
            }
        });

        vitalityDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (vitality > 1) {
                    vitality--;
                    vitalityLabel.setText("Vitality: " + vitality);
                }
            }
        });

        agilityDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (agility > 1) {
                    agility--;
                    agilityLabel.setText("Agility: " + agility);
                }
            }
        });

        intellectDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (intellect > 1) {
                    intellect--;
                    intellectLabel.setText("Intellect: " + intellect);
                }
            }
        });

        charismaDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (charisma > 1) {
                    charisma--;
                    charismaLabel.setText("Charisma: " + charisma);
                }
            }
        });

        strengthUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (strength < 10) {
                    strength++;
                    strengthLabel.setText("Strength: " + strength);
                }
            }
        });

        vitalityUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (vitality < 10) {
                    vitality++;
                    vitalityLabel.setText("Vitality: " + vitality);
                }
            }
        });

        agilityUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (agility < 10) {
                    agility++;
                    agilityLabel.setText("Agility: " + agility);
                }
            }
        });

        intellectUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (intellect < 10) {
                    intellect++;
                    intellectLabel.setText("Intellect: " + intellect);
                }
            }
        });

        charismaUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (charisma < 10) {
                    charisma++;
                    charismaLabel.setText("Charisma: " + charisma);
                }
            }
        });
    }

    private void setupSkillsWindow() {
        meleeSkillLabel = new Label("Melee Skill: " + meleeSkill, skin);
        gunsSkillLabel = new Label("Guns Skill: " + gunsSkill, skin);
        energyWeaponsSkillLabel = new Label("Energy Weapons: " + energyWeaponsSkill, skin);

        TextButton meleeSkillDown = new TextButton("Down", skin);
        TextButton gunsSkillDown = new TextButton("Down", skin);
        TextButton energyWeaponsSkillDown = new TextButton("Down", skin);
        TextButton meleeSkillUp = new TextButton("Up", skin);
        TextButton gunsSkillUp = new TextButton("Up", skin);
        TextButton energyWeaponsSkillUp = new TextButton("Up", skin);

        // Add attribute labels and button to the window
        skillsWindow.add(meleeSkillDown);
        skillsWindow.add(meleeSkillLabel);
        skillsWindow.add(meleeSkillUp);
        skillsWindow.row();
        skillsWindow.add(gunsSkillDown);
        skillsWindow.add(gunsSkillLabel);
        skillsWindow.add(gunsSkillUp);
        skillsWindow.row();
        skillsWindow.add(energyWeaponsSkillDown);
        skillsWindow.add(energyWeaponsSkillLabel);
        skillsWindow.add(energyWeaponsSkillUp);

        meleeSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (meleeSkill > 1) {
                    meleeSkill--;
                    meleeSkillLabel.setText("Melee Skill: " + meleeSkill);
                }
            }
        });

        gunsSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (gunsSkill > 1) {
                    gunsSkill--;
                    gunsSkillLabel.setText("Guns Skill: " + gunsSkill);
                }
            }
        });

        energyWeaponsSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (energyWeaponsSkill > 1) {
                    energyWeaponsSkill--;
                    energyWeaponsSkillLabel.setText("Energy Weapons Skill: " + energyWeaponsSkill);
                }
            }
        });

        meleeSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (meleeSkill < 100) {
                    meleeSkill++;
                    meleeSkillLabel.setText("Melee Skill: " + meleeSkill);
                }
            }
        });

        gunsSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (gunsSkill < 100) {
                    gunsSkill++;
                    gunsSkillLabel.setText("Guns Skill: " + gunsSkill);
                }
            }
        });

        energyWeaponsSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (energyWeaponsSkill < 100) {
                    energyWeaponsSkill++;
                    energyWeaponsSkillLabel.setText("Energy Weapons Skill: " + energyWeaponsSkill);
                }
            }
        });
    }

    private void addSubtables() {
        masterTable.add(topRowInfoTable).top().left().expandX().fillX();
        masterTable.row();
        masterTable.add(attributesWindow).top().left().expandX().fillX();
        masterTable.add(skillsWindow).top().right().expandX().fillX();
    }
}
