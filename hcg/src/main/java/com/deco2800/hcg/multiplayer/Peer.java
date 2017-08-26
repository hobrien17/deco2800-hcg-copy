package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;

/**
 * Represents a networked peer
 * 
 * @author Max Crofts
 *
 */
public class Peer {
	private SocketAddress socketAddress;
	private boolean host;
	
	public Peer(SocketAddress socketAddress, boolean isHost) {
		this.socketAddress = socketAddress;
		this.host = isHost;
	}
	
	public SocketAddress getSocketAddress() {
		return socketAddress;
	}
	
	public boolean isHost() {
		return host;
	}
	
	public void setHost(boolean host) {
		this.host = host;
	}
 }
