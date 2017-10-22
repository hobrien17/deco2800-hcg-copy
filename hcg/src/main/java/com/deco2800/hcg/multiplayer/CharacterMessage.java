package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.deco2800.hcg.contexts.WaitHostContext;
import com.deco2800.hcg.contexts.WorldStackContext;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.PlayerManager;

/**
 * This class represents a message to be sent when a player chooses a character.
 * 
 * @author Max Crofts
 */
public class CharacterMessage extends Message {
	private final GameManager gameManager = GameManager.get();
	private final ContextManager contextManager =
			(ContextManager) gameManager.getManager(ContextManager.class);
	private final NetworkManager networkManager =
			(NetworkManager) gameManager.getManager(NetworkManager.class);
	private final PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
	
	private int character = 0;
	
	public CharacterMessage(int character) {
		super(MessageType.CHARACTER);
		this.character = character;
	}
	
	public CharacterMessage(SocketAddress address) {
		super(address);
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.put((byte) character);
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		character = buffer.get();
	}
	
	@Override
	public void process() {
        Player player = playerManager.getMultiplayerCharacter(character);
        playerManager.addPlayer(player);
		
		if (playerManager.getPlayers().size() >= 2) {
            if (!networkManager.isHost()) {
                contextManager.pushContext(new WaitHostContext(0));
            } else {
                contextManager.pushContext(new WorldStackContext());
            }
		}
	}
}
