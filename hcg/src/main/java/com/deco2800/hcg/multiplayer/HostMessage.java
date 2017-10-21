package com.deco2800.hcg.multiplayer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;

/**
 * This class represents a message sent when announcing a game.
 * 
 * @author Max Crofts
 */
public class HostMessage extends Message {
	private final NetworkManager networkManager =
			(NetworkManager) GameManager.get().getManager(NetworkManager.class);
	
	private String lobbyName;
	
	public HostMessage(String lobbyName) {
		super(MessageType.HOST);
		this.lobbyName = lobbyName;
	}
	
	public HostMessage(SocketAddress address) {
		super(address);
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.putInt(lobbyName.length());
		buffer.put(lobbyName.getBytes());
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		int length = buffer.getInt();
		byte[] lobbyBytes = new byte[length];
		buffer.get(lobbyBytes);
		lobbyName = new String(lobbyBytes);
	}
	
	@Override
	public void process() {
		// add server to list
		networkManager.serverFound(lobbyName, ((InetSocketAddress) address).getHostString());
	}
	
	@Override
	public boolean shouldAck() {
		return false;
	}
}
