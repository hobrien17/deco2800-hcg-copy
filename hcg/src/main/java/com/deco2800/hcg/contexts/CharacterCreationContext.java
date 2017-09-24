package com.deco2800.hcg.contexts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.BasicSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.single.wearable.CottonShirt;
import com.deco2800.hcg.items.stackable.HealthPotion;
import com.deco2800.hcg.managers.PlayerManager;

/**
 * The CharacterCreationContext is used at the start of the game to create a character by assigning various points,
 * as well as choosing from some visual templates.
 *
 * TODO: Finalise skills and actually initialise them when creating the player
 *
 * @author avryn
 */
public class CharacterCreationContext extends CharacterContext{

    private Label strengthLabel;
    private Label vitalityLabel;
    private Label agilityLabel;
    private Label intellectLabel;
    private Label charismaLabel;
    private Label meleeSkillLabel;
    private Label gunsSkillLabel;
    private Label energyWeaponsSkillLabel;
    private Label attributePointsLabel;
    private Label specializedSkillsPointsLabel;
    private Label startingHealthLabel;
    private Label startingStaminaLabel;
    private Label healthGainLabel;
    private Label staminaGainLabel;
    private Label skillPointsGainLabel;
    private Label carryWeightLabel;

    private CheckBox meleeSkillSpecialise;
    private CheckBox gunsSkillSpecialise;
    private CheckBox energyWeaponsSkillSpecialise;

    private SelectBox<String> characterSex;

    // For some reason the checkBox isChecked method isn't working properly so this is a temporary fix
    private Boolean meleeSkillSpecialiseChecked = false;
    private Boolean gunsSkillSpecialiseChecked = false;
    private Boolean energyWeaponsSkillSpecialiseChecked = false;

    private Window attributesWindow;
    private Window skillsWindow;
    private Window statsWindow;
    private Window characterPreviewWindow;
    private Window selectedDescriptionWindow;

    private String[] sexes = new String[]{"Male", "Female"};

    // Placeholder for setting what skills are specialised because I'm a data structures n00b
    private int[] specialisedSkills = new int[3];

    private int strength = 5; 
    private int vitality = 5;
    private int agility = 5;
    private int intellect = 5;
    private int charisma = 5; 
    private int meleeSkill = 10;
    private int gunsSkill = 10;
    private int energyWeaponsSkill = 10;
    private int attributePoints = 5;
    private int specializedSkillsPoints = 2;
    private int skillPointsGain = 14;
    private int carryWeight = 180;
    private int startingHealth = 1800;
    private int startingStamina = 1800;
    private int healthGain = 200;
    private int staminaGain = 200;

    private Image characterPreviewImage;

    // Different placeholder textures for the character preview screen
    // Will put into texture manager later, was getting odd null pointer exceptions
    private Texture male1;
    private Texture male2;
    private Texture male3;
    private Texture female1;
    private Texture female2;
    private Texture female3;
    private Texture blank_window_background;

    //Cycle through this array using texture count to display the different character presets
    private Texture[] charTextureArray;
    private int textureCount;

    private TextArea selectedDescriptionText;

    /**
     * Creates a new character creation screen
     */
    public CharacterCreationContext() {
        getManagers();
        initMasterTable();
        setupTextures();
        initSubTables();
        setupTopRowInfo();
        setupAttributesWindow();
        setupSkillsWindow();
        setupStatsWindow();
        setupCharacterPreviewWindow();
        setupSelectedDescriptionWindow();
        addSubtables();
    }

    private void setupTextures() {
        male1 = textureManager.getTexture("ccMale1");
        male2 = textureManager.getTexture("ccMale2");
        male3 = textureManager.getTexture("ccMale3");
        female1 = textureManager.getTexture("ccFemale1");
        female2 = textureManager.getTexture("ccFemale2");
        female3 = textureManager.getTexture("ccFemale3");
        blank_window_background = textureManager.getTexture("ccWindow_Background_White");
        charTextureArray = new Texture[] {male1, male2, male3, female1, female2, female3};
    }

    // Declaring sub-tables/sub-windows
    private void initSubTables() {
        topRowInfoTable = new Table(skin);
        attributesWindow = new Window("Attributes", skin);
        //attributesWindow.setBackground();
        skillsWindow = new Window("Skills", skin);
        statsWindow = new Window("Stats", skin);
        characterPreviewWindow = new Window("Character Preview", skin);
        selectedDescriptionWindow = new Window("Click on an attribute or skill to find out what it does!", skin);

        // Set windows as non-movable
        attributesWindow.setMovable(false);
        skillsWindow.setMovable(false);
        statsWindow.setMovable(false);
        characterPreviewWindow.setMovable(false);
        selectedDescriptionWindow.setMovable(false);

        attributesWindow.setBackground(new Image(blank_window_background).getDrawable());
        skillsWindow.setBackground(new Image(blank_window_background).getDrawable());
        statsWindow.setBackground(new Image(blank_window_background).getDrawable());
        characterPreviewWindow.setBackground(new Image(blank_window_background).getDrawable());

        /* Need to find a way to do this without overwriting the button and label listeners.
        attributesWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Attributes:\n Attributes are set at the start of the game and" +
                        "may only be modified through special perks, items, or consumables. Since your attributes can" +
                        "not be changed easily, be sure to choose wisely. Click on an attribute to" +
                        " find out what it does.");
            }
        });*/
    }

    //Setting up top row info
    private void setupTopRowInfo() {
        TextButton quitButton = new TextButton("Back", skin);
        TextButton skipButton = new TextButton("CLICK HERE TO SKIP CHARACTER CREATION", skin);
        TextButton doneButton = new TextButton("Finished", skin);
        TextField characterName = new TextField("Enter Name", skin);

        characterSex = new SelectBox<>(skin);
        characterSex.setItems(sexes);

        topRowInfoTable.add(characterName);
        topRowInfoTable.add(characterSex).left().expandX();
        topRowInfoTable.add(quitButton).center();
        topRowInfoTable.add(skipButton);
        topRowInfoTable.add(doneButton).right();

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

        // If all points have been distributed, all specialities have been chosen,
        // create the character from the given specification, and continue on with game.
        doneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (attributePoints == 0 && specializedSkillsPoints == 0) {
                    contextManager.pushContext(new WorldStackContext());
                    /* Create new player */
                    createPlayer(strength, vitality, agility, charisma, intellect, meleeSkill, gunsSkill, energyWeaponsSkill,
                            characterName.getText(), charTextureArray[textureCount].toString());
                } else {
                    selectedDescriptionText.setText("Please distribute all skill points and choose your specialised" +
                            " skills");
                }
            }
        });

        // Temporary button to create character with default values
        skipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.pushContext(new WorldStackContext());
                /* Create new player with default values. */
                createPlayer(5, 5, 5, 5, 5, meleeSkill, gunsSkill, energyWeaponsSkill,
                        characterName.getText(), charTextureArray[textureCount].toString());
            }
        });


        characterSex.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (characterSex.getSelected() == "Male") {
                    textureCount = 0;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                } else {
                    textureCount = 3;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                }
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
                if (strength <= 1) {
                	return;
                }
                strength--;
                attributePoints++;
                carryWeight -= 20;
                strengthLabel.setText("Strength: " + strength);
                attributePointsLabel.setText("Available Points: " + attributePoints);
                carryWeightLabel.setText("Carry Weight: " + carryWeight);
                selectedDescriptionText.setText("Your Strength.\n Strength determines how much damage you " +
                        "deal with melee weapons, as well as how much you are able to carry");
            }
        });

        vitalityDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
            	if (vitality <= 1) {
                	return;
                }
            	vitality--;
                attributePoints++;
                startingHealth -= 200;
                healthGain -= 40;
                vitalityLabel.setText("Vitality: " + vitality);
                attributePointsLabel.setText("Available Points: " + attributePoints);
                startingHealthLabel.setText("Starting Health: " + startingHealth);
                healthGainLabel.setText("Health gained per level up: " + healthGain);
                selectedDescriptionText.setText("Your Vitality.\n Vitality determines your starting health," +
                        "as well as how much health you gain per level");
            }
        });

        agilityDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
            	if (agility <= 1) {
                	return;
                }
            	agility--;
            	attributePoints++;
            	startingStamina -= 200;
            	staminaGain -= 40;
            	agilityLabel.setText("Agility: " + agility);
            	attributePointsLabel.setText("Available Points: " + attributePoints);
            	startingStaminaLabel.setText("Starting Stamina: " + startingStamina);
            	staminaGainLabel.setText("Stamina Gained per Level: " + staminaGain);
                selectedDescriptionText.setText("Your Agility.\n Agility determines your starting stamina," +
                        "as well as how much stamina you gain per level");
            }
        });

        intellectDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
            	if (intellect <= 1) {
                	return;
                }
            	intellect--;
            	attributePoints++;
            	skillPointsGain -= 2;
            	intellectLabel.setText("Intellect: " + intellect);
            	attributePointsLabel.setText("Available Points: " + attributePoints);
            	skillPointsGainLabel.setText("Skill points to spend per level up: " + skillPointsGain);
                selectedDescriptionText.setText("Your Intellect.\n Intellect determines how many skill points you have" +
                        "to distribute to your skills each level");
            }
        });

        charismaDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
            	if (charisma <= 1) {
                	return;
                }
            	charisma--;
            	attributePoints++;
            	charismaLabel.setText("Charisma: " + charisma);
            	attributePointsLabel.setText("Available Points: " + attributePoints);
                selectedDescriptionText.setText("Your Charisma.\n Charisma determines how well your interactions with" +
                        "friendly NPCs go. A high Charisma will allow you to barter for better prices at shops and" +
                        "influence others to see your point of view, and perhaps even follow you");
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
                    selectedDescriptionText.setText("Your Strength.\n Strength determines how much damage you " +
                            "deal with melee weapons, as well as how much you are able to carry");
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
                    selectedDescriptionText.setText("Your Vitality.\n Vitality determines your starting health," +
                            "as well as how much health you gain per level");
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
                    selectedDescriptionText.setText("Your Agility.\n Agility determines your starting stamina," +
                            "as well as how much stamina you gain per level");
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
                    selectedDescriptionText.setText("Your Intellect.\n Intellect determines how many skill points you have" +
                            "to distribute to your skills each level");
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
                    selectedDescriptionText.setText("Your Charisma.\n Charisma determines how well your interactions with" +
                            "friendly NPCs go. A high Charisma will allow you to barter for better prices at shops and" +
                            "influence others to see your point of view, and perhaps even follow you");
                }
            }
        });

        attributePointsLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("The number of points you have to distribute to your attributes.");
            }
        });

        strengthLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Strength.\n Strength determines how much damage you " +
                        "deal with melee weapons, as well as how much you are able to carry");
            }
        });

        vitalityLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Vitality.\n Vitality determines your starting health," +
                        "as well as how much health you gain per level");
            }
        });

        agilityLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Agility.\n Agility determines your starting stamina," +
                        "as well as how much stamina you gain per level");
            }
        });

        intellectLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Intellect.\n Intellect determines how many skill points you have" +
                        "to distribute to your skills each level");
            }
        });

        charismaLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Charisma.\n Charisma determines how well your interactions with" +
                        "friendly NPCs go. A high Charisma will allow you to barter for better prices at shops and" +
                        "influence others to see your point of view, and perhaps even follow you");
            }
        });
    }

    private void setupSkillsWindow() {
        specializedSkillsPointsLabel = new Label("Available Specialities: " + specializedSkillsPoints, skin);
        meleeSkillLabel = new Label("Melee Skill: " + meleeSkill, skin);
        gunsSkillLabel = new Label("Guns Skill: " + gunsSkill, skin);
        energyWeaponsSkillLabel = new Label("Energy Weapons Skill: " + energyWeaponsSkill, skin);

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
                selectedDescriptionText.setText("Your Melee Weapons skill.\n Determines how much damage you do with" +
                        " Melee Weapons");
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
                selectedDescriptionText.setText("Your Guns skill.\n Determines how much damage you do with" +
                        " Guns");
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
                selectedDescriptionText.setText("Your Energy Weapons skill.\n Determines how much damage you do with" +
                        " Energy Weapons");
            }
        });

        meleeSkillLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Melee Weapons skill.\n Determines how much damage you do with" +
                        " Melee Weapons");
            }
        });

        gunsSkillLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Guns skill.\n Determines how much damage you do with" +
                        " Guns");
            }
        });

        energyWeaponsSkillLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectedDescriptionText.setText("Your Energy Weapons skill.\n Determines how much damage you do with" +
                        " Energy Weapons");
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

    private void setupCharacterPreviewWindow() {
        characterPreviewImage = new Image(male1);

        TextButton next = new TextButton("Next", skin);

        characterPreviewWindow.add(next);
        characterPreviewWindow.add(characterPreviewImage);

        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (characterSex.getSelected().equals("Male") && textureCount < 2) {
                    textureCount++;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                } else if (characterSex.getSelected().equals("Male") && textureCount == 2) {
                    textureCount = 0;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                } else if (characterSex.getSelected().equals("Female") && textureCount < 5) {
                    textureCount++;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                } else if (characterSex.getSelected().equals("Female") && textureCount == 5) {
                    textureCount = 3;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                }
            }
        });
    }

    private void setupSelectedDescriptionWindow() {
        selectedDescriptionText = new TextArea("JUST CLICK ON SOMETHING ALREADY", skin);
        selectedDescriptionText.setColor(Color.WHITE);
        selectedDescriptionWindow.add(selectedDescriptionText).bottom().left().expandY().expandX().fillX().fillY().padTop(10);
    }

    private void addSubtables() {
        masterTable.add(topRowInfoTable).top().left().expandX().fillX().colspan(2).padBottom(10);
        masterTable.row();
        masterTable.add(attributesWindow).top().left().expandX().fillX();
        masterTable.add(skillsWindow).top().right().expandX().fillX().fillY();
        masterTable.row();
        masterTable.add(statsWindow).top().left().expandX().fillX().fillY();
        masterTable.add(characterPreviewWindow).top().right().expandX().fillX().fillY();
        masterTable.row();
        masterTable.add(selectedDescriptionWindow).top().left().fillX().fillY().expandY().expandX().colspan(2);
    }


    // Will be changed later to include skill specialisations
    private void createPlayer(int strength, int vitality, int agility, int charisma, int intellect, int meleeSkill,
                              int gunsSkill, int energyWeaponsSkill, String name, String texture) {
        Player player = new Player(5, 10, 0);
        player.initialiseNewPlayer(strength, vitality, agility, charisma, intellect, meleeSkill, gunsSkill,
                energyWeaponsSkill, name);
        player.setTexture(texture);
        playerManager.setPlayer(player);
        //TODO: Change this, currently these are just testing items
        Item test = new CottonShirt(CottonShirt.ShirtColour.BLACK);
        Item test2 = new CottonShirt(CottonShirt.ShirtColour.GREEN);
        Item testPotion = new HealthPotion(100);
        Item startingSeeds = new BasicSeed();
        startingSeeds.addToStack(100);
        testPotion.setStackSize(4);
        Item testPotion2 = new HealthPotion(100);
        player.addItemToInventory(test);
        player.addItemToInventory(test2);
        player.addItemToInventory(testPotion);
        player.addItemToInventory(testPotion2);
        player.addItemToInventory(startingSeeds);

    }
}
