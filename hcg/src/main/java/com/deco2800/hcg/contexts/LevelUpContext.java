package com.deco2800.hcg.contexts;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    private int oldMachineGunSkill;
    private int oldShotGunSkill;
    private int oldStarGunSkill;

    public LevelUpContext() {
        super();
        topRowInfoTable.removeActor(backButton);
        skillPoints = playerManager.getPlayer().getSkillPoints();
        oldMachineGunSkill = playerManager.getPlayer().getAttribute("machineGunSkill");
        oldShotGunSkill = playerManager.getPlayer().getAttribute("shotGunSkill");
        oldStarGunSkill = playerManager.getPlayer().getAttribute("starGunSkill");
        skillsButtons();
        finishButton();
    }

    private void finishButton() {
        TextButton finishButton = new TextButton("Finished", skin);

        finishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
				if (skillPoints != 0) {
					return;
				}
				playerManager.getPlayer().setAttribute("machineGunSkill",
                        machineGunSkill);
				playerManager.getPlayer().setAttribute("shotGunSkill", shotGunSkill);
				playerManager.getPlayer().setAttribute("starGunSkill",
                        starGunSkill);
				contextManager.popContext();
			}
        });

        topRowInfoTable.add(finishButton).left().expandX();
    }

    private void skillsButtons() {
        skillsWindow.clear();

        Label skillPointsLabel;
        skillPointsLabel = new Label("Available Specialities: " + skillPoints, skin);
        machineGunSkillLabel = new Label("Melee Skill: " + machineGunSkill, skin);
        shotGunSkillLabel = new Label("Guns Skill: " + shotGunSkill, skin);
        starGunSkillLabel = new Label("Energy Weapons: " + starGunSkill, skin);

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
        skillsWindow.add(machineGunSkillLabel);
        skillsWindow.add(meleeSkillUp);
        skillsWindow.row();
        skillsWindow.add(gunsSkillDown);
        skillsWindow.add(shotGunSkillLabel);
        skillsWindow.add(gunsSkillUp);
        skillsWindow.row();
        skillsWindow.add(energyWeaponsSkillDown);
        skillsWindow.add(starGunSkillLabel);
        skillsWindow.add(energyWeaponsSkillUp);

        meleeSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (playerManager.getPlayer().getSpecialisedSkills().get("machineGunSkill")) {
                    if ((machineGunSkill - 2) >= oldMachineGunSkill) {
                        machineGunSkill -= 2;
                        skillPoints++;
					}
				} else if ((machineGunSkill - 1) >= oldMachineGunSkill) {
					machineGunSkill--;
					skillPoints++;
				}
				machineGunSkillLabel.setText("Melee Skill: " + machineGunSkill);
                skillPointsLabel.setText("Available Points: " + skillPoints);
            }
        });

        gunsSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (playerManager.getPlayer().getSpecialisedSkills().get("shotGunSkill")) {
                    if ((shotGunSkill - 2) >= oldShotGunSkill) {
                        shotGunSkill -= 2;
                        skillPoints++;
					}
				} else if ((shotGunSkill - 1) >= oldShotGunSkill) {
					shotGunSkill--;
					skillPoints++;
				}
                shotGunSkillLabel.setText("Guns Skill: " + shotGunSkill);
                skillPointsLabel.setText("Available Points: " + skillPoints);

            }
        });

        energyWeaponsSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (playerManager.getPlayer().getSpecialisedSkills().get("starGunSkill")) {
                    if ((starGunSkill - 2) >= oldStarGunSkill) {
                        starGunSkill -= 2;
                        skillPoints++;
                    }
				} else if ((starGunSkill - 1) >= oldStarGunSkill) {
					starGunSkill--;
					skillPoints++;
				}
				starGunSkillLabel.setText("Energy Weapons Skill: " + starGunSkill);
                skillPointsLabel.setText("Available Points: " + skillPoints);
            }
        });

        meleeSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (machineGunSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("machineGunSkill")) {
                        machineGunSkill += 2;
                    } else {
                        machineGunSkill++;
                    }
                    skillPoints--;
                    machineGunSkillLabel.setText("Melee Skill: " + machineGunSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        gunsSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (shotGunSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("shotGunSkill")) {
                        shotGunSkill += 2;
                    } else {
                        shotGunSkill++;
                    }
                    skillPoints--;
                    shotGunSkillLabel.setText("Guns Skill: " + shotGunSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        energyWeaponsSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (starGunSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("starGunSkill")) {
                        starGunSkill += 2;
                    } else {
                        starGunSkill++;
                    }
                    skillPoints--;
                    starGunSkillLabel.setText("Energy Weapons Skill: " + starGunSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });
    }
}
