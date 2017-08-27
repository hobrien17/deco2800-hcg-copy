package com.deco2800.hcg.managers;

import com.deco2800.hcg.entities.Player;

/**
 * PlayerManager for managing the Player instance.
 *
 * @author leggy
 */
public class PlayerManager extends Manager {

    private Player player;

    /**
     * Sets the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the player.
     *
     * @return Returns the player.
     */
    public Player getPlayer() {
        return this.player;
    }

}
