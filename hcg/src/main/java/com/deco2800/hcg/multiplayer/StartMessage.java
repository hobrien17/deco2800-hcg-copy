package com.deco2800.hcg.multiplayer;

import java.nio.ByteBuffer;

import com.deco2800.hcg.contexts.CharacterCreationContext;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.WorldManager;

/**
 * This class represents a message to be sent when the host starts the game.
 * 
 * @author Max Crofts
 */
public class StartMessage extends Message {
	private final GameManager gameManager = GameManager.get();
	private final ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
	private final NetworkManager networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
	private final PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
	private final WorldManager worldManager = (WorldManager) gameManager.getManager(WorldManager.class);
	
	private int seed;
	
	public StartMessage() {
		//deliberately empty
	}
	
	public StartMessage(int seed) {
		super(MessageType.START);
		this.seed = seed;
		
		networkManager.setSeed((long) seed);
		
		worldManager.setGeneratorSeed(networkManager.getNextRandomInt(Integer.MAX_VALUE));
		worldManager.generateAndSetWorldStack();
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.putInt(seed);
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		seed = buffer.getInt();
	}
	
	@Override
	public void process() {
		networkManager.setSeed((long) seed);
		
		worldManager.setGeneratorSeed(networkManager.getNextRandomInt(Integer.MAX_VALUE));
		worldManager.generateAndSetWorldStack();
		
		// TODO: we need to support more (4?) players
		// FIXME
		Player otherPlayer = new Player(1, 5, 10, 0);
		otherPlayer.initialiseNewPlayer(5, 5, 5, 5, 5, 20, 20, 20, "Player 2");
		playerManager.addPlayer(otherPlayer);
		contextManager.pushContext(new CharacterCreationContext());
	}
}
