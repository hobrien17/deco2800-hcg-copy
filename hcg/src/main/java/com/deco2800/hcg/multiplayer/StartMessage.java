package com.deco2800.hcg.multiplayer;

import com.deco2800.hcg.contexts.CharacterCreationContext;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

/**
 * This class represents a message to be sent when the host starts the game.
 * 
 * @author Max Crofts
 */
public class StartMessage extends Message {
	GameManager gameManager = GameManager.get();
	ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
	PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
	
	public StartMessage() {}
	
	public StartMessage(int id) {
		super(MessageType.START);
	}
	
	@Override
	public void process() {
		// TODO: we need to support more (4?) players
		// FIXME
		/*
		gameManager.setOccupiedNode(gameManager.getWorldMap().getContainedNodes().get(0));
		gameManager.setWorld(new World(gameManager.getWorldMap().getContainedNodes().get(0)
				.getNodeLinkedLevel().getWorld().getLoadedFile()));
		*/
		Player otherPlayer = new Player(1, 5, 10, 0);
		otherPlayer.initialiseNewPlayer(5, 5, 5, 5, 5, 20, 20, 20, "Player 2");
		playerManager.addPlayer(otherPlayer);
		/*
		playerManager.spawnPlayers();
		contextManager.pushContext(new PlayContext());
		*/
		contextManager.pushContext(new CharacterCreationContext());
	}
}
