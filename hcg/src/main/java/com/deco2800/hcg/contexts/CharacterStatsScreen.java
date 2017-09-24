package com.deco2800.hcg.contexts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Abstract class to be extended by the CharacterStatsContext and levelUpContext
 * The two contexts are almost identical with levelUpContext having some extra buttons to distribute skill points
 * @author avryn
 */
abstract class CharacterStatsScreen extends CharacterContext {
    private Window attributesWindow;
    Window skillsWindow;
    private Window statsWindow;
    private Window perksWindow;

    int meleeSkill;
    int gunsSkill;
    int energyWeaponsSkill;

    Label meleeSkillLabel;
    Label gunsSkillLabel;
    Label energyWeaponsSkillLabel;

    TextButton backButton;

    CharacterStatsScreen() {
        getManagers();
        initMasterTable();
        initSubTables();
        setupTopRowInfo();
        setupAttributesWindow();
        setupSkillsWindow();
        setupStatsWindow();
        addSubtables();
    }

    private void initSubTables() {
        topRowInfoTable = new Table(skin);
        attributesWindow = new Window("Attributes", skin);
        skillsWindow = new Window("Skills", skin);
        statsWindow = new Window("Stats", skin);
        perksWindow = new Window("Perks", skin);

        Texture backgroundTexture = textureManager.getTexture("ccWindow_Background_White");
        attributesWindow.setBackground(new Image(backgroundTexture).getDrawable());
        skillsWindow.setBackground(new Image(backgroundTexture).getDrawable());
        statsWindow.setBackground(new Image(backgroundTexture).getDrawable());
        perksWindow.setBackground(new Image(backgroundTexture).getDrawable());
    }

    private void setupTopRowInfo() {
        int level = playerManager.getPlayer().getLevel();
        int currentXp = playerManager.getPlayer().getXp();
        int xpThreshold = playerManager.getPlayer().getXpThreshold();

        backButton = new TextButton("Back", skin);
        Label levelLabel = new Label("Level: " + level, skin);
        Label xpLabel = new Label("Xp: " + currentXp + "/" + xpThreshold, skin);

        topRowInfoTable.add(backButton).left().expandX();
        topRowInfoTable.add(levelLabel).right().padRight(10);
        topRowInfoTable.add(xpLabel).right();

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
    }

    private void setupAttributesWindow() {
        int strength = playerManager.getPlayer().getAttribute("strength");
        int vitality = playerManager.getPlayer().getAttribute("vitality");
        int agility = playerManager.getPlayer().getAttribute("agility");
        int intellect = playerManager.getPlayer().getAttribute("intellect");
        int charisma = playerManager.getPlayer().getAttribute("charisma");

        Label strengthLabel = new Label("Strength: " + strength, skin);
        Label vitalityLabel = new Label("Vitality: " + vitality, skin);
        Label agilityLabel = new Label("Agility: " + agility, skin);
        Label intellectLabel = new Label("Intellect: " + intellect, skin);
        Label charismaLabel = new Label("Charisma: " + charisma, skin);

        attributesWindow.add(strengthLabel);
        attributesWindow.row();
        attributesWindow.add(vitalityLabel);
        attributesWindow.row();
        attributesWindow.add(agilityLabel);
        attributesWindow.row();
        attributesWindow.add(intellectLabel);
        attributesWindow.row();
        attributesWindow.add(charismaLabel);
        attributesWindow.pack();
    }

    private  void setupSkillsWindow() {
        meleeSkill = playerManager.getPlayer().getAttribute("meleeSkill");
        gunsSkill = playerManager.getPlayer().getAttribute("gunsSkill");
        energyWeaponsSkill = playerManager.getPlayer().getAttribute("energyWeaponsSkill");

        meleeSkillLabel = new Label("Melee Skill: " + meleeSkill, skin);
        gunsSkillLabel = new Label("Guns Skill: " + gunsSkill, skin);
        energyWeaponsSkillLabel = new Label("Energy Weapons Skill: " + energyWeaponsSkill, skin);

        skillsWindow.add(meleeSkillLabel);
        skillsWindow.row();
        skillsWindow.add(gunsSkillLabel);
        skillsWindow.row();
        skillsWindow.add(energyWeaponsSkillLabel);
    }

    private  void setupStatsWindow() {
        int currentHealth = playerManager.getPlayer().getHealthCur();
        int maxHealth = playerManager.getPlayer().getHealthMax();
        int currentStamina = playerManager.getPlayer().getStaminaCur();
        int maxStamina = playerManager.getPlayer().getStaminaMax();

        Label healthLabel = new Label("Health: " + currentHealth + "/" + maxHealth, skin);
        Label staminaLabel = new Label("Stamina: " + currentStamina + "/" + maxStamina, skin);

        statsWindow.add(healthLabel);
        statsWindow.row();
        statsWindow.add(staminaLabel);
        statsWindow.row();
    }

    private  void addSubtables() {
        masterTable.add(topRowInfoTable).top().left().expandX().fillX().colspan(2).padBottom(10);
        masterTable.row();
        masterTable.add(attributesWindow).top().left().expandX().expandY().fillX().fillY().padBottom(10);
        masterTable.add(skillsWindow).top().right().expandX().expandY().fillX().fillY().padBottom(10);
        masterTable.row();
        masterTable.add(statsWindow).top().left().expandX().expandY().fillX().fillY();
        masterTable.add(perksWindow).top().right().expandX().expandY().fillX().fillY();
    }

}
