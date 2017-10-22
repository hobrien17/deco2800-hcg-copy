package com.deco2800.hcg.managers;

import java.util.ArrayList;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.worlds.World;

/**
 * PlayerManager for managing the Player instance.
 *
 * @author leggy
 */
public class PlayerManager extends Manager {

    //Was changed from LinkedHashset to ArrayList to resolve issue with removing players
	private ArrayList<Player> players = new ArrayList<>();
    private Player player;
    
    private static final int[][] stats = new int[][] {
        new int[] {5, 7, 5, 8, 6, 13, 14, 10},
        new int[] {5, 6, 5, 6, 10, 15, 11, 12},
        new int[] {6, 5, 6, 5, 6, 11, 14, 13},
        new int[] {5, 7, 5, 8, 6, 13, 14, 10},
        new int[] {5, 5, 6, 12, 8, 10, 10, 15}
    };
    
    /**
     * Adds a player to the game.
     * @param tempPlayer The player to add
     * @return <code>true</code> if player has not been previously added
     */
    public boolean addPlayer(Player tempPlayer) {
    		return players.add(tempPlayer);
    }
    
    /**
     * Spawns players in current world.
     */
    public void spawnPlayers() {
    		World world = GameManager.get().getWorld();
    		for (Player tempPlayer : players) {
    			// FIXME Players shouldn't spawn in the same place
                tempPlayer.setPosX(world.getStartingPlayerX());
                tempPlayer.setPosY(world.getStartingPlayerY());

    			    			
    			world.addEntity(tempPlayer);
    			world.addEntity(tempPlayer.getEquippedWeapon());
    		}
    }

    /**
     * Despawns players from the current world. Used for exiting levels.
     */
    public void despawnPlayers() {
        World world = GameManager.get().getWorld();
        for (Player tempPlayer : players) {
            tempPlayer.ceaseMovement();
            world.removeEntity(tempPlayer);
            world.removeEntity(tempPlayer.getEquippedWeapon());
        }
    }
    
    /**
     * Gets all players.
     * @return Set of players
     */
    public ArrayList<Player> getPlayers() {
    		return players;
    }

    /**
     * Sets the player.
     */
    public void setPlayer(Player tempPlayer) {
        this.player = tempPlayer;
        addPlayer(tempPlayer);
    }

    /**
     * Gets the player.
     *
     * @return Returns the player.
     */
    public Player getPlayer() {
        return this.player;
    }

    public boolean removeCurrentPlayer() {
        return players.remove(this.player);
    }
    
    public Player getMultiplayerCharacter(int character) {
		Player player = new Player(players.size(), 5, 10, 0);
		player.initialiseNewPlayer(stats[character][0], stats[character][1], stats[character][2],
			stats[character][3], stats[character][4], stats[character][5], stats[character][6],
			stats[character][7], 10, character);
		return player;
	}

}
