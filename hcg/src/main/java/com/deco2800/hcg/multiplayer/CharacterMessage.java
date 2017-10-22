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
	
	private int strength = 5;
    private int vitality = 5;
    private int agility = 5;
    private int intellect = 5;
    private int charisma = 5;
    private int machineGunSkill = 10;
    private int shotGunSkill = 10;
    private int starGunSkill = 10;
    private int multiGunSkill = 10;
    private String characterName = "";
	
	public CharacterMessage(int strength, int vitality, int agility, int charisma, int intellect,
							int machineGunSkill, int shotGunSkill, int starGunSkill, int multiGunSkill, String name) {
		super(MessageType.CHARACTER);
		this.strength = strength;
		this.vitality = vitality;
		this.agility = agility;
		this.intellect = intellect;
		this.charisma = charisma;
		this.machineGunSkill = machineGunSkill;
		this.shotGunSkill = shotGunSkill;
		this.starGunSkill = starGunSkill;
		this.multiGunSkill = multiGunSkill;
		this.characterName = name;
	}
	
	public CharacterMessage(SocketAddress address) {
		super(address);
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.putInt(strength);
		buffer.putInt(vitality);
		buffer.putInt(agility);
		buffer.putInt(intellect);
		buffer.putInt(charisma);
		buffer.putInt(machineGunSkill);
		buffer.putInt(shotGunSkill);
		buffer.putInt(starGunSkill);
		buffer.put((byte) characterName.length());
		byte[] name = characterName.getBytes();
		buffer.put(name, 0, Math.min(name.length, 127));
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		strength = buffer.getInt();
		vitality = buffer.getInt();
		agility = buffer.getInt();
		intellect = buffer.getInt();
		charisma = buffer.getInt();
		machineGunSkill = buffer.getInt();
		shotGunSkill = buffer.getInt();
		starGunSkill = buffer.getInt();
		byte[] name = new byte[buffer.get()];
		buffer.get(name);
		characterName = new String(name);
	}
	
	@Override
	public void process() {
		// FIXME Player ID
		Player player = new Player(1, 5, 10, 0);
		player.initialiseNewPlayer(strength, vitality, agility, charisma, intellect, machineGunSkill, shotGunSkill,
				starGunSkill, multiGunSkill, characterName);
		playerManager.addPlayer(player);
		
		// FIXME Player count
		if (playerManager.getPlayers().size() >= 2) {
            if (!networkManager.isHost()) {
                contextManager.pushContext(new WaitHostContext(0));
            } else {
                contextManager.pushContext(new WorldStackContext());
            }
		}
	}
}
