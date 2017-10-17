package com.deco2800.hcg.buffs;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

public class Perk {
    /**
     * Class to contain all the enum perk data as well as values that change throughout the game.
     * Contains additional information such as current level of the perk, a boolean whether its max level or even unlocked
     * to ease display of perks, also stores the attached enum to make searhes easier.
     */

    private perk enumPerk;
    private int currentLevel;
    private Player player;

    public enum perk {
        I_AM_GROOT("I am groot", 1, 2),
        SPLINTER_IS_COMING("Splinter is coming", 2, 3),
        FULL_PETAL_ALCHEMIST("Full-Petal Alchemist", 5, 1),
        GUNS_AND_ROSES("Guns and Roses", 10, 1),
        RUN_FUNGUS_RUN("Run Fungus, Run!", 1, 3),
        HOLLY_MOLEY("Holly Moley", 2, 2),
        KALERATE("Kale-ra-te", 5, 2),
        THORN("Thor-n", 7, 1),
        THE_FUNGAL_COUNTDOWN("The Fungal Countdown", 10, 4),
        BRAMBLE_AM("Whoa Black Betty, Bramble-am", 1, 4),
        BUT_NOT_YEAST("Last But Not Yeast", 2, 1),
        SAVING_GRAVES("Saving Graves", 5, 1),
        FUNGICIDAL_MANIAC("Fungicidal Maniac", 10, 3);

        private final String name;
        private final int levelRequirement;
        private final int maxLevel;

        perk(String name, int levelRequirement, int maxLevel) {
            this.name = name;
            this.levelRequirement = levelRequirement;
            this.maxLevel = maxLevel;
        }

        private String getName() {
            return name;
        }

        private int getLevelRequirement() {
            return levelRequirement;
        }

        private int getMaxLevel() {
            return maxLevel;
        }
    }

    public Perk(perk enumPerk) {

        GameManager gameManager = GameManager.get();
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        player = playerManager.getPlayer();
        this.enumPerk = enumPerk;
        this.currentLevel = 0;
    }

    public perk getEnumPerk() {
        return enumPerk;
    }

    public String getName() {
        return enumPerk.getName();
    }

    public int getLevelRequired() {
        return enumPerk.getLevelRequirement();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return enumPerk.getMaxLevel();
    }

    public boolean isUnlocked() {
        return currentLevel > 0;
    }

    public void setCurrentLevel( int currentLevel) {
        if (currentLevel >= enumPerk.getMaxLevel() || currentLevel < 0) {
        }
        else {
            this.currentLevel = currentLevel;
        }
    }

    public boolean isAvaliable() {
        return (player.getLevel() >= enumPerk.getLevelRequirement());
    }

    public boolean isMaxed() {
        return currentLevel == enumPerk.getMaxLevel();
    }

    @Override
    public String toString() {
        return enumPerk.getName() + ", level: " + currentLevel + " / " + enumPerk.getMaxLevel();
    }
}
