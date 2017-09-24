package com.deco2800.hcg.contexts;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * Level up screen for the player. Is almost identical to the CharacterStatsScreen but with a few buttons to distribute
 * skill points.
 * @author avryn
 */
public class LevelUpContext extends CharacterStatsScreen {

    private int skillPoints;

    public LevelUpContext() {
        super();
        skillPoints = playerManager.getPlayer().getAttribute("intellect") * 2 + 4;
        skillsButtons();
        finishButton();

    }

    private void finishButton() {
        Table finishTable = new Table(skin);

        TextButton finishButton = new TextButton("Finished", skin);

        finishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (skillPoints == 0) {
                    playerManager.getPlayer().setAttribute("meleeSkill", meleeSkill);
                    playerManager.getPlayer().setAttribute("gunsSkill", gunsSkill);
                    playerManager.getPlayer().setAttribute("energyWeaponsSkill", energyWeaponsSkill);
                    contextManager.popContext();
                }
            }
        });

        finishTable.add(finishButton);

        masterTable.row();
        masterTable.add(finishTable);
    }

    private void skillsButtons() {
        skillsWindow.clear();

        Label skillPointsLabel;
        skillPointsLabel = new Label("Available Specialities: " + skillPoints, skin);
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
        skillsWindow.add(skillPointsLabel);
        skillsWindow.row();
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
                    skillPoints++;
                    meleeSkillLabel.setText("Melee Skill: " + meleeSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        gunsSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (gunsSkill > 1) {
                    gunsSkill--;
                    skillPoints++;
                    gunsSkillLabel.setText("Guns Skill: " + gunsSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        energyWeaponsSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (energyWeaponsSkill > 1) {
                    energyWeaponsSkill--;
                    skillPoints++;
                    energyWeaponsSkillLabel.setText("Energy Weapons Skill: " + energyWeaponsSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        meleeSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (meleeSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("meleeSkill")) {
                        meleeSkill += 2;
                    } else {
                        meleeSkill++;
                    }
                    skillPoints--;
                    meleeSkillLabel.setText("Melee Skill: " + meleeSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        gunsSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (gunsSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("gunsSkill")) {
                        gunsSkill += 2;
                    } else {
                        gunsSkill++;
                    }
                    skillPoints--;
                    gunsSkillLabel.setText("Guns Skill: " + gunsSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        energyWeaponsSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (energyWeaponsSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("energyWeaponsSkill")) {
                        energyWeaponsSkill += 2;
                    } else {
                        energyWeaponsSkill++;
                    }
                    skillPoints--;
                    energyWeaponsSkillLabel.setText("Energy Weapons Skill: " + energyWeaponsSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });
    }
}
