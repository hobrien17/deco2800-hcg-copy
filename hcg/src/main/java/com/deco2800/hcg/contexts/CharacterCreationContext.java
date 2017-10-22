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
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.single.wearable.CottonShirt;
import com.deco2800.hcg.items.stackable.HealthPotion;
import com.deco2800.hcg.items.tools.Shovel;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;

import java.util.*;
import java.util.List;

/**
 * The CharacterCreationContext is used at the start of the game to create a character by assigning various points,
 * as well as choosing from some visual templates.
 *
 * TODO: Finalise skills and actually initialise them when creating the player
 *
 * @author avryn
 */
public class CharacterCreationContext extends CharacterContext{

    private static final String FEMALE = "Female";
    private static final String MALE = "Male";
    
    private NetworkManager networkManager =
    		(NetworkManager) GameManager.get().getManager(NetworkManager.class);

    private Label strengthLabel;
    private Label vitalityLabel;
    private Label agilityLabel;
    private Label intellectLabel;
    private Label charismaLabel;
    private Label machineGunSkillLabel;
    private Label shotGunSkillLabel;
    private Label starGunSkillLabel;
    private Label multiGunSkillLabel;
    private Label attributePointsLabel;
    private Label specializedSkillsPointsLabel;
    private Label startingHealthLabel;
    private Label startingStaminaLabel;
    private Label healthGainLabel;
    private Label staminaGainLabel;
    private Label skillPointsGainLabel;
    private Label carryWeightLabel;

    private CheckBox machineGunSpecialise;
    private CheckBox shotGunSpecialise;
    private CheckBox starGunSpecialise;
    private CheckBox multiGunSpecialise;
    
    private TextField characterName;

    // For some reason the checkBox isChecked method isn't working properly so this is a temporary fix
    private Boolean machineGunSkillSpecialiseChecked = false;
    private Boolean shotGunSkillSpecialiseChecked = false;
    private Boolean starGunSkillSpecialiseChecked = false;
    private Boolean multiGunSkillSpecialiseChecked = false;
    
    // Sub tables
    private Table attributesTable;
    private Table skillsTable;
    private Table statsTable;
    private Table characterPreviewTable;

    private String[] sexes = new String[]{MALE, FEMALE};

    // Placeholder for setting what skills are specialised because I'm a data structures n00b
    private List<String> SPECIALISED_SKILLS = Arrays.asList( "machineGunSkill", "shotGunSkill", "starGunSkill", "multiGunSkill");
    private Map<String, Boolean> specialisedSkills;

    // Attribute and skill points
    private int strength = 5; 
    private int vitality = 5;
    private int agility = 5;
    private int intellect = 5;
    private int charisma = 5; 
    private int machineGunSkill = 10;
    private int shotGunSkill = 10;
    private int starGunSkill = 10;
    private int multiGunSkill = 10;
    private int attributePoints = 5;
    private int specializedSkillsPoints = 2;
    private int skillPointsGain = 7;
    private int carryWeight = 180;
    private int startingHealth = 250;
    private int startingStamina = 250;
    private int healthGain = 80;
    private int staminaGain = 40;

    private Image characterPreviewImage;

    private Texture male1;
    private Texture male2;
    private Texture male3;
    private Texture female1;
    private Texture female2;
    private Texture female3;
    private Texture blankWindowBackground;
    private Texture titleAttributes;
    private Texture titleSkills;
    private Texture titleStats;
    private Texture titleCharacter;

    //Cycle through this array using texture count to display the different character presets
    private Texture[] charTextureArray;
    private int textureCount;

    /**
     * Creates a new character creation screen
     */
    public CharacterCreationContext() {
        getManagers();
        this.specialisedSkills = new HashMap<String, Boolean>();
        for (String attribute: SPECIALISED_SKILLS) {
            specialisedSkills.put(attribute, false);
        }
        initMasterTable();
        setupTextures();
        initSubTables();
        setupTopRowInfo();
        setupAttributesWindow();
        setupSkillsWindow();
        setupStatsWindow();
        setupCharacterPreviewWindow();
        addSubtables();
    }

    private void setupTextures() {
        male1 = textureManager.getTexture("ccMale1");
        male2 = textureManager.getTexture("ccMale2");
        male3 = textureManager.getTexture("ccMale3");
        female1 = textureManager.getTexture("ccFemale1");
        female2 = textureManager.getTexture("ccFemale2");
        female3 = textureManager.getTexture("ccFemale3");
        titleAttributes = textureManager.getTexture("ccAttributes");
        titleSkills = textureManager.getTexture("ccSkills");
        titleStats = textureManager.getTexture("ccStats");
        titleCharacter = textureManager.getTexture("ccCharacter");
        blankWindowBackground = textureManager.getTexture("ccWindow_BorderSmaller_White");
        charTextureArray = new Texture[] {male1, male2, male3, female1, female2, female3};
    }

    // Declaring sub-tables/sub-windows
    private void initSubTables() {
        topRowInfoTable = new Table(skin);
        attributesTable = new Table(skin);
        skillsTable = new Table(skin);
        statsTable = new Table(skin);
        characterPreviewTable = new Table(skin);
        
        attributesTable.setBackground(new Image(blankWindowBackground).getDrawable());
        skillsTable.setBackground(new Image(blankWindowBackground).getDrawable());
        statsTable.setBackground(new Image(blankWindowBackground).getDrawable());
        characterPreviewTable.setBackground(new Image(blankWindowBackground).getDrawable());
    }

    //Setting up top row info
    private void setupTopRowInfo() {
        TextButton quitButton = new TextButton("Back", skin);
        TextButton skipButton = new TextButton("Skip", skin);
        TextButton doneButton = new TextButton("Done", skin);
        topRowInfoTable.add(quitButton).left().expandX();
        topRowInfoTable.add(skipButton).right();
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
                		if (networkManager.isMultiplayerGame() && !networkManager.isHost()) {
                			contextManager.pushContext(new WaitHostContext(0));
                		} else {
                			contextManager.pushContext(new WorldStackContext());
                		}
                    /* Create new player */
                    /* Also check to see if player already exists */
                    if (playerManager.getPlayer() == null) {
                        createPlayer(strength, vitality, agility, charisma, intellect, machineGunSkill, shotGunSkill, starGunSkill,
                                multiGunSkill, characterName.getText(), charTextureArray[textureCount].toString());
                    }
                } else {
                    //TODO: Show tooltip explaining why you can't advance
                }
            }
        });

        // Temporary button to create character with default values
        skipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	if (networkManager.isMultiplayerGame() && !networkManager.isHost()) {
            		contextManager.pushContext(new WaitHostContext(0));
            	} else {
            		contextManager.pushContext(new WorldStackContext());
            	}
                /* Create new player with default values. */
                if (playerManager.getPlayer() == null) {
                    createPlayer(5, 5, 5, 5, 5, machineGunSkill, shotGunSkill, starGunSkill,
                           multiGunSkill, characterName.getText(), charTextureArray[textureCount].toString());
                }
            }
        });
    }

    private void setupAttributesWindow() {
        // Attribute labels
        attributePointsLabel = new Label("Available Points: " + attributePoints, skin);
        strengthLabel = new Label("Strength: " + strength, skin);
        vitalityLabel = new Label("Vitality: " + vitality, skin);
        agilityLabel = new Label("Agility: " + agility, skin);
        intellectLabel = new Label("Intellect: " + intellect, skin);
        charismaLabel = new Label("Charisma: " + charisma, skin);

        // Buttons to increase and decrease attribute points
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
        
        // Tooltips for displaying information on attributes
        TextTooltip strengthInfo = new TextTooltip("Strength determines how much damage you " +
                "deal with melee weapons, as well as how much you are able to carry.", skin);
        TextTooltip vitalityInfo = new TextTooltip("Vitality determines your starting health " +
                "as well as how much health you gain per level.", skin);
        TextTooltip agilityInfo = new TextTooltip("Agility determines your starting stamina " +
                "as well as how much stamina you gain per level.", skin);
        TextTooltip intellectInfo = new TextTooltip("Intellect determines how many skill " +
                "points you have to distribute to your skills each level.", skin);
        TextTooltip charismaInfo = new TextTooltip("Charisma determines how well your " +
                "interactions with friendly NPCs go.", skin);
        
        // Add everything to the subtable
        attributesTable.add(new Image(titleAttributes)).colspan(3).padTop(50).padBottom(30);
        attributesTable.row();
        attributesTable.add(attributePointsLabel).colspan(3).padBottom(10);
        attributesTable.row();
        attributesTable.add(strengthDown);
        attributesTable.add(strengthLabel);
        attributesTable.add(strengthUp);
        attributesTable.row();
        attributesTable.add(vitalityDown);
        attributesTable.add(vitalityLabel);
        attributesTable.add(vitalityUp);
        attributesTable.row();
        attributesTable.add(agilityDown);
        attributesTable.add(agilityLabel);
        attributesTable.add(agilityUp);
        attributesTable.row();
        attributesTable.add(intellectDown);
        attributesTable.add(intellectLabel);
        attributesTable.add(intellectUp);
        attributesTable.row();
        attributesTable.add(charismaDown);
        attributesTable.add(charismaLabel);
        attributesTable.add(charismaUp);
        attributesTable.row();
        attributesTable.add().expandY();
        attributesTable.pack();

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
                startingHealth -= 50;
                healthGain -= 20;
                vitalityLabel.setText("Vitality: " + vitality);
                attributePointsLabel.setText("Available Points: " + attributePoints);
                startingHealthLabel.setText("Starting Health: " + startingHealth);
                healthGainLabel.setText("Health gained per level up: " + healthGain);
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
            	startingStamina -= 50;
            	staminaGain -= 10;
            	agilityLabel.setText("Agility: " + agility);
            	attributePointsLabel.setText("Available Points: " + attributePoints);
            	startingStaminaLabel.setText("Starting Stamina: " + startingStamina);
            	staminaGainLabel.setText("Stamina Gained per Level: " + staminaGain);
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
            	skillPointsGain -= 1;
            	intellectLabel.setText("Intellect: " + intellect);
            	attributePointsLabel.setText("Available Points: " + attributePoints);
            	skillPointsGainLabel.setText("Skill points to spend per level up: " + skillPointsGain);
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
                    startingHealth += 50;
                    healthGain += 20;
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
                    startingStamina += 50;
                    staminaGain += 10;
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
                    skillPointsGain += 1;
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

        // Add tooltips to labels
        attributePointsLabel.addListener(new TextTooltip("The number of points you have to " +
                "distribute to your attributes.", skin));
        strengthLabel.addListener(strengthInfo);
        vitalityLabel.addListener(vitalityInfo);
        agilityLabel.addListener(agilityInfo);
        intellectLabel.addListener(intellectInfo);
        charismaLabel.addListener(charismaInfo);
    }

    private void setupSkillsWindow() {
        // Skill labels
        specializedSkillsPointsLabel = new Label("Available Specialities: " + specializedSkillsPoints, skin);
        machineGunSkillLabel = new Label("Machine Gun Skill: " + machineGunSkill, skin);
        shotGunSkillLabel = new Label("Shotgun Skill: " + shotGunSkill, skin);
        starGunSkillLabel = new Label("Star Gun Skill: " + starGunSkill, skin);
        multiGunSkillLabel = new Label("Multi Gun Skill: " + multiGunSkill, skin);

        // Checkboxes to choose specialties
        machineGunSpecialise = new CheckBox("", skin);
        machineGunSpecialise.setChecked(false);
        shotGunSpecialise = new CheckBox("", skin);
        shotGunSpecialise.setChecked(false);
        starGunSpecialise = new CheckBox("", skin);
        starGunSpecialise.setChecked(false);
        multiGunSpecialise = new CheckBox("", skin);
        multiGunSpecialise.setChecked(false);
        
        // Tooltips for displaying information on skills
        TextTooltip machineGunInfo = new TextTooltip("Determines how much damage you do with " +
                "the Machine Gun. Weapon damage will be multiplied by a factor of SkillPoints/20", skin);
        TextTooltip shotGunInfo = new TextTooltip("Determines how much damage you do with the " +
                "Shotgun. Weapon damage will be multiplied by a factor of SkillPoints/20", skin);
        TextTooltip starGunInfo = new TextTooltip("Determines how much damage you do with the " +
                "Star Gun. Weapon damage will be multiplied by a factor of SkillPoints/20", skin);
        TextTooltip multiGunInfo = new TextTooltip("Determines how much damage you do with the " +
                "Multi Gun. Weapon damage will be multiplied by a factor of SkillPoints/20", skin);
        
        // Add everything to subtable
        skillsTable.add(new Image(titleSkills)).colspan(2).padTop(50).padBottom(30);
        skillsTable.row();
        skillsTable.add(specializedSkillsPointsLabel).colspan(2).padBottom(10);
        skillsTable.row();
        skillsTable.add(machineGunSpecialise).left().padRight(10);
        skillsTable.add(machineGunSkillLabel);
        skillsTable.row();
        skillsTable.add(shotGunSpecialise).left().padRight(10);
        skillsTable.add(shotGunSkillLabel);
        skillsTable.row();
        skillsTable.add(starGunSpecialise).left().padRight(10);
        skillsTable.add(starGunSkillLabel);
        skillsTable.row();
        skillsTable.add(multiGunSpecialise).left().padRight(10);
        skillsTable.add(multiGunSkillLabel);
        skillsTable.row();
        skillsTable.add().expandY();
        skillsTable.pack();

        /*  Add listeners to the check-boxes have had to do some VERY odd work arounds to get these checkboxes working
            The checkbox.isChecked() methods don't seem to be working properly with the clickListener
         */

       machineGunSpecialise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (machineGunSkillSpecialiseChecked) {
                    specializedSkillsPoints++;
                    machineGunSkill -= 10;
                    machineGunSpecialise.setChecked(false);
                    machineGunSkillSpecialiseChecked = false;
                    specialisedSkills.replace("machineGunSkill", false);
                } else {
                    if (specializedSkillsPoints > 0) {
                        specializedSkillsPoints--;
                        machineGunSkill += 10;
                        machineGunSpecialise.setChecked(true);
                        machineGunSkillSpecialiseChecked = true;
                        specialisedSkills.replace("machineGunSkill", true);
                    } else {
                        machineGunSpecialise.setChecked(false);
                        machineGunSkillSpecialiseChecked = false;
                    }
                }
                machineGunSkillLabel.setText("Machine Gun Skill: " + machineGunSkill);
                specializedSkillsPointsLabel.setText("Available Specialities: " + specializedSkillsPoints);
            }
        });

        shotGunSpecialise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (shotGunSkillSpecialiseChecked) {
                    specializedSkillsPoints++;
					shotGunSkill -= 10;
					shotGunSpecialise.setChecked(false);
					shotGunSkillSpecialiseChecked = false;
					specialisedSkills.replace("shotGunSkill", false);
				} else if (specializedSkillsPoints > 0) {
					specializedSkillsPoints--;
					shotGunSkill += 10;
					shotGunSpecialise.setChecked(true);
					shotGunSkillSpecialiseChecked = true;
					specialisedSkills.replace("shotGunSkill", true);
				} else {
					shotGunSpecialise.setChecked(false);
					shotGunSkillSpecialiseChecked = false;
				}
                shotGunSkillLabel.setText("Shotgun Skill: " + shotGunSkill);
                specializedSkillsPointsLabel.setText("Available Specialities: " + specializedSkillsPoints);
            }
        });

        starGunSpecialise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (starGunSkillSpecialiseChecked) {
                    specializedSkillsPoints++;
                    starGunSkill -= 10;
                    starGunSpecialise.setChecked(false);
                    starGunSkillSpecialiseChecked = false;
					specialisedSkills.replace("starGunSkill", false);
				} else if (specializedSkillsPoints > 0) {
					specializedSkillsPoints--;
					starGunSkill += 10;
					starGunSpecialise.setChecked(true);
					starGunSkillSpecialiseChecked = true;
					specialisedSkills.replace("starGunSkill", true);
				} else {
					starGunSpecialise.setChecked(false);
					starGunSkillSpecialiseChecked = false;
				}
                starGunSkillLabel.setText("Star Gun Skill: " + starGunSkill);
                specializedSkillsPointsLabel.setText("Available Specialities: " + specializedSkillsPoints);
            }
        });

        multiGunSpecialise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (multiGunSkillSpecialiseChecked) {
                    specializedSkillsPoints++;
                    multiGunSkill -= 10;
                    multiGunSpecialise.setChecked(false);
                    multiGunSkillSpecialiseChecked = false;
                    specialisedSkills.replace("multiGunSkill", false);
                } else if (specializedSkillsPoints > 0) {
                    specializedSkillsPoints--;
                    multiGunSkill += 10;
                    multiGunSpecialise.setChecked(true);
                    multiGunSkillSpecialiseChecked = true;
                    specialisedSkills.replace("multiGunSkill", true);
                } else {
                    multiGunSpecialise.setChecked(false);
                    multiGunSkillSpecialiseChecked = false;
                }
                multiGunSkillLabel.setText("Multi Gun Skill: " + multiGunSkill);
                specializedSkillsPointsLabel.setText("Available Specialities: " + specializedSkillsPoints);
            }
        });

        //Add tooltips to labels
        specializedSkillsPointsLabel.addListener(new TextTooltip("The number of points you have to" + 
                "distribute to your attributes.", skin));
        machineGunSkillLabel.addListener(machineGunInfo);
        shotGunSkillLabel.addListener(shotGunInfo);
        starGunSkillLabel.addListener(starGunInfo);
        multiGunSkillLabel.addListener(multiGunInfo);
    }

    private void setupStatsWindow() {
        // Stat labels
        startingHealthLabel = new Label("Starting Health: " + startingHealth, skin);
        startingStaminaLabel = new Label("Starting stamina: " + startingStamina, skin);
        healthGainLabel = new Label("Health gained per level up: " + healthGain, skin);
        staminaGainLabel = new Label("Stamina gained per level up: " + staminaGain, skin);
        skillPointsGainLabel = new Label("Skill points to spend per level up: " + skillPointsGain, skin);
        carryWeightLabel = new Label("Carry Weight: " + carryWeight, skin);
        
        // Add everything to subtable
        statsTable.add(new Image(titleStats)).padTop(50).padBottom(30);
        statsTable.row();
        statsTable.add(startingHealthLabel);
        statsTable.row();
        statsTable.add(startingStaminaLabel);
        statsTable.row();
        statsTable.add(healthGainLabel);
        statsTable.row();
        statsTable.add(staminaGainLabel);
        statsTable.row();
        statsTable.add(skillPointsGainLabel);
        statsTable.row();
        statsTable.add(carryWeightLabel);
        statsTable.row();
        statsTable.add().expandY();
        statsTable.pack();
    }

    private void setupCharacterPreviewWindow() {
        characterPreviewImage = new Image(male1);

        TextButton next = new TextButton("Change colour", skin);
        
        this.characterName = new TextField("Enter Name", skin);
        SelectBox<String> characterSex = new SelectBox<String>(skin);
        characterSex.setItems(sexes);

        // Add everything to subtable
        characterPreviewTable.add(new Image(titleCharacter)).colspan(2).padBottom(30);
        characterPreviewTable.row();
        characterPreviewTable.add(this.characterName).right();
        characterPreviewTable.add(characterSex).left();
        characterPreviewTable.row();
        characterPreviewTable.add(characterPreviewImage).colspan(2);
        characterPreviewTable.row();
        characterPreviewTable.add(next).colspan(2);
        characterPreviewTable.pack();
        
        // Listeners for buttons
        characterSex.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (characterSex.getSelected() == MALE) {
                    textureCount = 0;
                } else {
                    textureCount = 3;
                }
                characterPreviewImage.setDrawable(new SpriteDrawable(
                        new Sprite(charTextureArray[textureCount])));
            }
        });

        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (characterSex.getSelected().equals(MALE) && textureCount < 2) {
                    textureCount++;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                } else if (characterSex.getSelected().equals(MALE) && textureCount == 2) {
                    textureCount = 0;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                } else if (characterSex.getSelected().equals(FEMALE) && textureCount < 5) {
                    textureCount++;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                } else if (characterSex.getSelected().equals(FEMALE) && textureCount == 5) {
                    textureCount = 3;
                    characterPreviewImage.setDrawable(new SpriteDrawable(new Sprite(charTextureArray[textureCount])));
                }
            }
        });
    }

    private void addSubtables() {
        masterTable.add(topRowInfoTable).top().left().expandX().fillX().colspan(2).padBottom(15);
        masterTable.row();
        masterTable.add(characterPreviewTable).grow().pad(30);
        masterTable.add(attributesTable).grow().pad(30);
        masterTable.row();
        masterTable.add(statsTable).grow().pad(30);
        masterTable.add(skillsTable).grow().pad(30);
        masterTable.row();
    }


    // Will be changed later to include skill specialisations
    private void createPlayer(int strength, int vitality, int agility, int charisma, int intellect, int machineGunSkill,
                              int shotGunSkill, int starGunSkill, int multiGunSkill, String name, String texture) {
        Player player = new Player(5, 10, 0);
        player.initialiseNewPlayer(strength, vitality, agility, charisma, intellect, machineGunSkill, shotGunSkill,
                starGunSkill, multiGunSkill, name);
        player.setSpecialisedSkills(specialisedSkills);
        player.setTexture(texture);
        playerManager.setPlayer(player);
        //TODO: Change this, currently these are just testing items
        Item test = new CottonShirt(CottonShirt.ShirtColour.BLACK);
        Item test2 = new CottonShirt(CottonShirt.ShirtColour.GREEN);
        Item testPotion = new HealthPotion(100);
        Item startingSeeds = new Seed(Seed.Type.SUNFLOWER);
        Item shovel = new Shovel();
        startingSeeds.setStackSize(200);
        testPotion.setStackSize(4);
        Item testPotion2 = new HealthPotion(100);
        player.addItemToInventory(test);
        player.addItemToInventory(test2);
        player.addItemToInventory(testPotion);
        player.addItemToInventory(testPotion2);
        player.addItemToInventory(startingSeeds);
        player.addItemToInventory(shovel);
    }
}
