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
    private int oldMultiGunSkill;

    public LevelUpContext() {
        super();
        topRowInfoTable.removeActor(close);
        skillPoints = playerManager.getPlayer().getSkillPoints();
        oldMachineGunSkill = playerManager.getPlayer().getAttribute("machineGunSkill");
        oldShotGunSkill = playerManager.getPlayer().getAttribute("shotGunSkill");
        oldStarGunSkill = playerManager.getPlayer().getAttribute("starGunSkill");
        oldMultiGunSkill = playerManager.getPlayer().getAttribute("multiGunSkill");
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
				playerManager.getPlayer().setAttribute("machineGunSkill", machineGunSkill);
				playerManager.getPlayer().setAttribute("shotGunSkill", shotGunSkill);
				playerManager.getPlayer().setAttribute("starGunSkill", starGunSkill);
                playerManager.getPlayer().setAttribute("multiGunSkill", multiGunSkill);
				contextManager.popContext();
			}
        });

        topRowInfoTable.add(finishButton).left().expandX();
    }

    private void skillsButtons() {
        skillsWindow.clear();

        Label skillPointsLabel;
        skillPointsLabel = new Label("Available Specialities: " + skillPoints, skin);
        machineGunSkillLabel = new Label("Machine Gun Skill: " + machineGunSkill, skin);
        shotGunSkillLabel = new Label("Shotgun Skill: " + shotGunSkill, skin);
        starGunSkillLabel = new Label("Star Gun: " + starGunSkill, skin);
        multiGunSkillLabel = new Label("Multi Gun: " + starGunSkill, skin);

        TextButton machineGunSkillDown = new TextButton("Down", skin);
        TextButton shotGunSkillDown = new TextButton("Down", skin);
        TextButton starGunSkillDown = new TextButton("Down", skin);
        TextButton multiGunSkillDown = new TextButton("Down", skin);
        TextButton machineGunSkillUp = new TextButton("Up", skin);
        TextButton shotGunSkillUp = new TextButton("Up", skin);
        TextButton starGunSkillUp = new TextButton("Up", skin);
        TextButton multiGunSkillUp = new TextButton("Up", skin);

        // Add attribute labels and button to the window
        skillsWindow.add(skillPointsLabel);
        skillsWindow.row();
        skillsWindow.add(machineGunSkillDown);
        skillsWindow.add(machineGunSkillLabel);
        skillsWindow.add(machineGunSkillUp);
        skillsWindow.row();
        skillsWindow.add(shotGunSkillDown);
        skillsWindow.add(shotGunSkillLabel);
        skillsWindow.add(shotGunSkillUp);
        skillsWindow.row();
        skillsWindow.add(starGunSkillDown);
        skillsWindow.add(starGunSkillLabel);
        skillsWindow.add(starGunSkillUp);
        skillsWindow.row();
        skillsWindow.add(multiGunSkillDown);
        skillsWindow.add(multiGunSkillLabel);
        skillsWindow.add(multiGunSkillUp);

        machineGunSkillDown.addListener(new ClickListener() {
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
				machineGunSkillLabel.setText("Machine Gun Skill: " + machineGunSkill);
                skillPointsLabel.setText("Available Points: " + skillPoints);
            }
        });

        shotGunSkillDown.addListener(new ClickListener() {
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
                shotGunSkillLabel.setText("Shotgun Skill: " + shotGunSkill);
                skillPointsLabel.setText("Available Points: " + skillPoints);

            }
        });

        starGunSkillDown.addListener(new ClickListener() {
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
				starGunSkillLabel.setText("Star Gun Skill: " + starGunSkill);
                skillPointsLabel.setText("Available Points: " + skillPoints);
            }
        });

        multiGunSkillDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (playerManager.getPlayer().getSpecialisedSkills().get("multiGunSkill")) {
                    if ((multiGunSkill - 2) >= oldMultiGunSkill) {
                        multiGunSkill -= 2;
                        skillPoints++;
                    }
                } else if ((multiGunSkill - 1) >= oldMultiGunSkill) {
                    multiGunSkill--;
                    skillPoints++;
                }
                multiGunSkillLabel.setText("Multi Gun Skill: " + multiGunSkill);
                skillPointsLabel.setText("Available Points: " + skillPoints);
            }
        });

        machineGunSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (machineGunSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("machineGunSkill")) {
                        machineGunSkill += 2;
                    } else {
                        machineGunSkill++;
                    }
                    skillPoints--;
                    machineGunSkillLabel.setText("Machine Gun Skill: " + machineGunSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        shotGunSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (shotGunSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("shotGunSkill")) {
                        shotGunSkill += 2;
                    } else {
                        shotGunSkill++;
                    }
                    skillPoints--;
                    shotGunSkillLabel.setText("Shotgun Skill: " + shotGunSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        starGunSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (starGunSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("starGunSkill")) {
                        starGunSkill += 2;
                    } else {
                        starGunSkill++;
                    }
                    skillPoints--;
                    starGunSkillLabel.setText("Star Gun Skill: " + starGunSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });

        multiGunSkillUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (multiGunSkill < 100 && skillPoints > 0) {
                    if (playerManager.getPlayer().getSpecialisedSkills().get("multiGunSkill")) {
                        multiGunSkill += 2;
                    } else {
                        multiGunSkill++;
                    }
                    skillPoints--;
                    multiGunSkillLabel.setText("Multi Gun Skill: " + multiGunSkill);
                    skillPointsLabel.setText("Available Points: " + skillPoints);
                }
            }
        });
    }
}
