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
 * The CharacterCreationContext is used at the start of the game to create a character by assigning various points,
 * as well as choosing from some visual templates.
 *
 * It can currently be accessed by pressing 'C' whilst in the game.
 *
 * @author avryn
 */
public class CharacterCreationContext extends UIContext{

    private Label strengthLabel, vitalityLabel, agilityLabel, intellectLabel, charismaLabel, meleeSkillLabel,
            gunsSkillLabel, energyWeaponsSkillLabel, attributePointsLabel, specializedSkillsPointsLabel,
            startingHealthLabel, startingStaminaLabel, healthGainLabel, staminaGainLabel, skillPointsGainLabel,
            carryWeightLabel;

    private CheckBox meleeSkillSpecialise, gunsSkillSpecialise, energyWeaponsSkillSpecialise;

    //For some reason the checkBox isChecked method isn't working properly so this is a temporary fix
    private Boolean meleeSkillSpecialiseChecked = false, gunsSkillSpecialiseChecked = false,
            energyWeaponsSkillSpecialiseChecked = false;

    private Table masterTable, topRowInfoTable;

    private Window attributesWindow, skillsWindow, statsWindow, characterPreviewWindow, selectedDescriptionWindow;

    private String[] sexes = new String[]{"Male", "Female"};

    //Placeholder for setting what skills are specialised because I'm a data structures n00b
    private int[] specialisedSkills = new int[3];

    private int strength = 5, vitality = 5, agility = 5, intellect = 5, charisma = 5, meleeSkill = 10, gunsSkill = 10,
            energyWeaponsSkill = 10, attributePoints = 5, specializedSkillsPoints = 2, skillPointsGain = 14,
            carryWeight = 180, startingHealth = 1800, startingStamina = 1800, healthGain = 200, staminaGain = 200;



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
        setupStatsWindow();
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
        this.show();
    }

    // Declaring sub-tables/sub-windows
    private void initSubTables() {
        topRowInfoTable = new Table(skin);
        attributesWindow = new Window("Attributes", skin);
        skillsWindow = new Window("Skills", skin);
        statsWindow = new Window("Stats", skin);
        characterPreviewWindow = new Window("Character Preview", skin);

        // Set windows as non-movable
        attributesWindow.setMovable(false);
        skillsWindow.setMovable(false);
        statsWindow.setMovable(false);
        characterPreviewWindow.setMovable(false);

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
        attributePointsLabel = new Label("Available Points: " + attributePoints, skin);
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
        attributesWindow.add(attributePointsLabel);
        attributesWindow.row();
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
                    attributePoints++;
                    carryWeight -= 20;
                    strengthLabel.setText("Strength: " + strength);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    carryWeightLabel.setText("Carry Weight: " + carryWeight);

                }
            }
        });

        vitalityDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (vitality > 1) {
                    vitality--;
                    attributePoints++;
                    startingHealth -= 200;
                    healthGain -= 40;
                    vitalityLabel.setText("Vitality: " + vitality);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    startingHealthLabel.setText("Starting Health: " + startingHealth);
                    healthGainLabel.setText("Health gained per level up: " + healthGain);
                }
            }
        });

        agilityDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (agility > 1) {
                    agility--;
                    attributePoints++;
                    startingStamina -= 200;
                    staminaGain -= 40;
                    agilityLabel.setText("Agility: " + agility);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    startingStaminaLabel.setText("Starting Stamina: " + startingStamina);
                    staminaGainLabel.setText("Stamina Gained per Level: " + staminaGain);
                }
            }
        });

        intellectDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (intellect > 1) {
                    intellect--;
                    attributePoints++;
                    skillPointsGain -= 2;
                    intellectLabel.setText("Intellect: " + intellect);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    skillPointsGainLabel.setText("Skill points to spend per level up: " + skillPointsGain);
                }
            }
        });

        charismaDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (charisma > 1) {
                    charisma--;
                    attributePoints++;
                    charismaLabel.setText("Charisma: " + charisma);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                }
            }
        });

        strengthUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (strength < 10 && attributePoints > 0) {
                    strength++;
                    attributePoints--;
                    carryWeight += 20;
                    strengthLabel.setText("Strength: " + strength);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    carryWeightLabel.setText("Carry Weight: " + carryWeight);
                }
            }
        });

        vitalityUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (vitality < 10 && attributePoints > 0) {
                    vitality++;
                    attributePoints--;
                    startingHealth += 200;
                    healthGain += 40;
                    vitalityLabel.setText("Vitality: " + vitality);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    startingHealthLabel.setText("Starting Health: " + startingHealth);
                    healthGainLabel.setText("Health gained per level up: " + healthGain);
                }
            }
        });

        agilityUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (agility < 10 && attributePoints > 0) {
                    agility++;
                    attributePoints--;
                    startingStamina += 200;
                    staminaGain += 40;
                    agilityLabel.setText("Agility: " + agility);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    startingStaminaLabel.setText("Starting Stamina: " + startingStamina);
                    staminaGainLabel.setText("Stamina Gained per Level: " + staminaGain);
                }
            }
        });

        intellectUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (intellect < 10 && attributePoints > 0) {
                    intellect++;
                    attributePoints--;
                    skillPointsGain += 2;
                    intellectLabel.setText("Intellect: " + intellect);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                    skillPointsGainLabel.setText("Skill points to spend per level up: " + skillPointsGain);
                }
            }
        });

        charismaUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (charisma < 10 && attributePoints > 0) {
                    charisma++;
                    attributePoints--;
                    charismaLabel.setText("Charisma: " + charisma);
                    attributePointsLabel.setText("Available Points: " + attributePoints);
                }
            }
        });
    }

    private void setupSkillsWindow() {
        specializedSkillsPointsLabel = new Label("Available Specialities: " + specializedSkillsPoints, skin);
        meleeSkillLabel = new Label("Melee Skill: " + meleeSkill, skin);
        gunsSkillLabel = new Label("Guns Skill: " + gunsSkill, skin);
        energyWeaponsSkillLabel = new Label("Energy Weapons: " + energyWeaponsSkill, skin);

        meleeSkillSpecialise = new CheckBox("Specialise", skin);
        meleeSkillSpecialise.setChecked(false);
        gunsSkillSpecialise = new CheckBox("Specialise", skin);
        gunsSkillSpecialise.setChecked(false);
        energyWeaponsSkillSpecialise = new CheckBox("Specialise", skin);
        energyWeaponsSkillSpecialise.setChecked(false);

        // Add attribute labels and button to the window
        skillsWindow.add(specializedSkillsPointsLabel);
        skillsWindow.row();
        skillsWindow.add(meleeSkillSpecialise);
        skillsWindow.add(meleeSkillLabel);
        skillsWindow.row();
        skillsWindow.add(gunsSkillSpecialise);
        skillsWindow.add(gunsSkillLabel);
        skillsWindow.row();
        skillsWindow.add(energyWeaponsSkillSpecialise);
        skillsWindow.add(energyWeaponsSkillLabel);

        /*  Add listeners to the check-boxes have had to do some VERY odd work arounds to get these checkboxes working
            The checkbox.isChecked() methods don't seem to be working properly with the clickListener
         */

       meleeSkillSpecialise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (meleeSkillSpecialiseChecked) {
                    specializedSkillsPoints++;
                    meleeSkill -= 10;
                    meleeSkillSpecialise.setChecked(false);
                    meleeSkillSpecialiseChecked = false;
                    specialisedSkills[0] = 0;
                } else {
                    if (specializedSkillsPoints > 0) {
                        specializedSkillsPoints--;
                        meleeSkill += 10;
                        meleeSkillSpecialise.setChecked(true);
                        meleeSkillSpecialiseChecked = true;
                        specialisedSkills[0] = 1;
                    } else {
                        meleeSkillSpecialise.setChecked(false);
                        meleeSkillSpecialiseChecked = false;
                    }
                }
                meleeSkillLabel.setText("Melee Skill: " + meleeSkill);
                specializedSkillsPointsLabel.setText("Available Specialities: " + specializedSkillsPoints);
            }
        });

        gunsSkillSpecialise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (gunsSkillSpecialiseChecked) {
                    specializedSkillsPoints++;
                    gunsSkill -= 10;
                    gunsSkillSpecialise.setChecked(false);
                    gunsSkillSpecialiseChecked = false;
                    specialisedSkills[1] = 0;
                } else {
                    if (specializedSkillsPoints > 0) {
                        specializedSkillsPoints--;
                        gunsSkill += 10;
                        gunsSkillSpecialise.setChecked(true);
                        gunsSkillSpecialiseChecked = true;
                        specialisedSkills[1] = 1;
                    } else {
                        gunsSkillSpecialise.setChecked(false);
                        gunsSkillSpecialiseChecked = false;
                    }
                }
                gunsSkillLabel.setText("Guns Skill: " + gunsSkill);
                specializedSkillsPointsLabel.setText("Available Specialities: " + specializedSkillsPoints);
            }
        });

        energyWeaponsSkillSpecialise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (energyWeaponsSkillSpecialiseChecked) {
                    specializedSkillsPoints++;
                    energyWeaponsSkill -= 10;
                    energyWeaponsSkillSpecialise.setChecked(false);
                    energyWeaponsSkillSpecialiseChecked = false;
                    specialisedSkills[2] = 0;
                } else {
                    if (specializedSkillsPoints > 0) {
                        specializedSkillsPoints--;
                        energyWeaponsSkill += 10;
                        energyWeaponsSkillSpecialise.setChecked(true);
                        energyWeaponsSkillSpecialiseChecked = true;
                        specialisedSkills[2] = 1;
                    } else {
                        energyWeaponsSkillSpecialise.setChecked(false);
                        energyWeaponsSkillSpecialiseChecked = false;
                    }
                }
                energyWeaponsSkillLabel.setText("Energy Weapons Skill: " + energyWeaponsSkill);
                specializedSkillsPointsLabel.setText("Available Specialities: " + specializedSkillsPoints);
            }
        });
    }

    private void setupStatsWindow() {
        startingHealthLabel = new Label("Starting Health: " + startingHealth, skin);
        startingStaminaLabel = new Label("Starting stamina: " + startingStamina, skin);
        healthGainLabel = new Label("Health gained per level up: " + healthGain, skin);
        staminaGainLabel = new Label("Stamina gained per level up: " + staminaGain, skin);
        skillPointsGainLabel = new Label("Skill points to spend per level up: " + skillPointsGain, skin);
        carryWeightLabel = new Label("Carry Weight: " + carryWeight, skin);

        statsWindow.add(startingHealthLabel);
        statsWindow.row();
        statsWindow.add(startingStaminaLabel);
        statsWindow.row();
        statsWindow.add(healthGainLabel);
        statsWindow.row();
        statsWindow.add(staminaGainLabel);
        statsWindow.row();
        statsWindow.add(skillPointsGainLabel);
        statsWindow.row();
        statsWindow.add(carryWeightLabel);
    }

    private void addSubtables() {
        masterTable.add(topRowInfoTable).top().left().expandX().fillX();
        masterTable.row();
        masterTable.add(attributesWindow).top().left().expandX().fillX();
        masterTable.add(skillsWindow).top().right().expandX().fillX().fillY();
        masterTable.row();
        masterTable.add(statsWindow).top().left().expandX().fillX().fillY();
        masterTable.add(characterPreviewWindow).top().right().expandX().fillX();
    }
}