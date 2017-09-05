package com.deco2800.hcg.managers;

import java.util.LinkedHashSet;
import java.util.Set;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.worlds.AbstractWorld;

/**
 * PlayerManager for managing the Player instance.
 *
 * @author leggy
 */
public class PlayerManager extends Manager {

	private Set<Player> players = new LinkedHashSet<>();
    private Player player;
    
    /**
     * Adds a player to the game.
     * @param player The player to add
     * @return <code>true</code> if player has not been previously added
     */
    public boolean addPlayer(Player player) {
    		return players.add(player);
    }
    
    /**
     * Spawns players in current world.
     */
    public void spawnPlayers() {
    		AbstractWorld world = GameManager.get().getWorld();
    		for (Player player : players) {
    			world.addEntity(player);
    			world.addEntity(player.getEquippedWeapon());
    		}
    }

    /**
     * Sets the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
        addPlayer(player);
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
