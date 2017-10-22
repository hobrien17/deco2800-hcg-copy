package com.deco2800.hcg.buffs;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

public class Perk {
    /**
     * Class to contain all the enum perk data as well as values that change throughout the game.
     * Contains additional information such as current level of the perk, a boolean whether its max level
     * or even unlocked. to ease display of perks, also stores the attached enum to make searhes easier.
     */

    private perk enumPerk;
    private int currentLevel;
    private Player player;

    public enum perk {
        //Druid
        I_AM_GROOT("I am groot", 1, 2, "I am groot - Slowly regain (1/2 + 0.2/0.3 per level) health per second"),
        SPLINTER_IS_COMING("Splinter is coming", 2, 3, "Splinter is coming - Enemies hit by you have a 15% chance of being " +
                "splintered, damaging them for (50/75/110 + 10/15/25 per level) per second for 3 seconds"),
        FULL_PETAL_ALCHEMIST("Full-Petal Alchemist", 5, 2, "Full-Petal Alchemist - By turning the food into potions, you gain (15/30 + " +
                "5/10 per level) Increased healing from food seeds"),
        GUNS_AND_ROSES("Guns and Roses", 10, 1, "Guns and Roses - turrets you plant in corpses have a 15% attack speed increase."),

        //Survivalist
        RUN_FUNGUS_RUN("Run Fungus, Run!", 1, 3, "Run Fungus, Run! - If you are hit, you receive a (1/2/3) second 20% speed" +
                " boost"),
        HOLLY_MOLEY("Holly Moley", 2, 2, "Holly Moley - Increases the explosion radius of your explosive seed by (10%/20%)"),
        KALERATE("Kale-ra-te", 5, 2, "Kale-ra-te - Your advanced fighting training has given you a (7.5%/15.0%) chance to dodge " +
                "incoming attacks."),
        THORN("Thor-n", 7, 1, " Thor-n - Your mighty survival skills have increased your pain threshold, making you take " +
                "reduced damage from all attacks by (10%)"),
        THE_FUNGAL_COUNTDOWN("The Fungal Countdown", 10, 4, "The Fungal Countdown - For each enemy you kill in a particular level, gain" +
                " a (1.0/1.2/1.4/1.6/2.0) damage boost for the remainder of that level"),

        //Fungal Fanatic
        BRAMBLE_AM("Whoa Black Betty, Bramble-am", 1, 4, "Whoa Black Betty, Bramble-am - Bramble armour - enemies that strike you in melee" +
                " range take (3/5/7/10 + 0.5/0.7/1/1.2) damage"),
        BUT_NOT_YEAST("Last But Not Yeast", 2, 2, "Last But Not Yeast - Your attacks have a 15% chance to spread fungus to the enemy, " +
                "stopping their movement for 1/2 seconds"),
        SAVING_GRAVES("Saving Graves", 5, 1, "Saving Graves - If the player takes more than 15% of their maximum health in a single" +
                " hit, the damage is staggered over 3 seconds."),
        FUNGICIDAL_MANIAC("Fungicidal Maniac", 10, 3, "Fungicidal Maniac - When the player becomes low on health, gain (5%/10%/15%) " +
                "increase to damage and (15%) increase to attack speed for 8 seconds");

        private final String name;
        private final int levelRequirement;
        private final int maxLevel;
        private final String description;

        perk(String name, int levelRequirement, int maxLevel, String description) {
            this.name = name;
            this.levelRequirement = levelRequirement;
            this.maxLevel = maxLevel;
            this.description = description;

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

        private String getDescription() {return description; }
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

    public  String getDescription() { return enumPerk.getDescription(); }

    public int getLevelRequired() {
        return enumPerk.getLevelRequirement();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return enumPerk.getMaxLevel();
    }

    public boolean isActive() {
        return currentLevel > 0;
    }

    public void setCurrentLevel( int currentLevel) {
        if (!(currentLevel > enumPerk.getMaxLevel() || currentLevel < 0)) {
            this.currentLevel = currentLevel;
        }
    }
    public boolean isAvaliable(Player player) {
        return  player.getLevel() >= enumPerk.getLevelRequirement();
    }

    public boolean isMaxed() {
        return currentLevel == enumPerk.getMaxLevel();
    }

    @Override
    public String toString() {
        return enumPerk.getName() + ", level: " + currentLevel + " / " + enumPerk.getMaxLevel();
    }
}
