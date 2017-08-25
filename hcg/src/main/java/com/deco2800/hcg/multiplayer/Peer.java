package com.deco2800.hcg.multiplayer;

/**
 * Represents a networked peer
 * 
 * @author Max Crofts
 *
 */
public class Peer {
	private String hostname;
	private boolean connected;
	
	public Peer(String hostname) {
		this.hostname = hostname;
		this.connected = false;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
 }
