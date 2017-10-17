package com.deco2800.hcg.buffs;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.entities.Player;
import java.util.ArrayList;

public class PlayerPerks {
    GameManager gameManager;
    Player player;
    ArrayList<Perk> perks;


    /**
     * Enum to store all of the non-changing data for the perks. makes storage, access and creation of perks easier.
     * the enums have a final name, level requirement, and max level.
     * Prevents runtime errors as enum can be used to obtain instance of Perk from PlayerPerks class instead of
     * typing in a string name.
     */
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
        private final int levelRequirement, maxLevel;

        perk(String name, int levelRequirement, int maxLevel) {
            this.name = name;
            this.levelRequirement = levelRequirement;
            this.maxLevel = maxLevel;
        }

        public String getName() {
            return name;
        }

        public int getLevelRequirement() {
            return levelRequirement;
        }

        public int getMaxLevel() {
            return maxLevel;
        }
    }

    /**
     * Class to contain all the enum perk data as well as values that change throughout the game.
     * Contains additional information such as current level of the perk, a boolean whether its max level or even unlocked
     * to ease display of perks, also stores the attached enum to make searhes easier.
     */
    private class Perk {
        private String name;
        private perk enumPerk;
        private int levelRequired;
        private int currentLevel;
        private int maxLevel;
        private boolean unlocked;
        private boolean maxed;

        private Perk(perk enumPerk) {
            this.enumPerk = enumPerk;
            this.name = enumPerk.getName();
            this.levelRequired = enumPerk.getLevelRequirement();
            this.maxLevel = enumPerk.getMaxLevel();
            this.currentLevel = 0;
        }

        public String getName() {
            return name;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        public int getCurrentLevel() {
            return currentLevel;
        }

        public int getMaxLevel() {
            return maxLevel;
        }

        public boolean isUnlocked() {
            return currentLevel > 0;
        }

        public void setCurrentLevel( int currentLevel) {
            if (currentLevel >= maxLevel || currentLevel < 0) {
            }
            else {
                this.currentLevel = currentLevel;
            }
        }

        public boolean isAvaliable() {
            return (player.getLevel() >= levelRequired);
        }

        public boolean isMaxed() {
            return currentLevel == maxLevel;
        }

        @Override
        public String toString() {
            return name + ", level: " + currentLevel + " / " + maxLevel;
        }
    }

    PlayerPerks() {
        this.gameManager = GameManager.get();
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        this.player = playerManager.getPlayer();
        perks = new ArrayList<>();

        for (perk enumPerk : perk.values()) {
            perks.add(new Perk(enumPerk));
        }
    }

    /**
     * returns a Perk given the enum
     * @param enumPerk
     * @return a vald instance of perk
     */
    public Perk getPerk(perk enumPerk) {
        for (Perk playerPerk : perks) {
            if (playerPerk.enumPerk == enumPerk) {
                return playerPerk;
            }
        }
        return null;
    }
}
