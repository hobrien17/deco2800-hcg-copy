package com.deco2800.hcg.multiplayer;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Asynchronous UDP networking
 * 
 * @author Max Crofts
 *
 */
public final class NetworkState {
	DatagramSocket socket;
	List<Peer> peers;
	List<byte[]> sendBuffer;
	private NetworkSend networkSend;
	private NetworkReceive networkReceive;
	private Thread sendThread;
	private Thread receiveThread;
	
	public NetworkState() {
		peers = new ArrayList<>();
		sendBuffer = new ArrayList<>();
		
		networkSend = new NetworkSend(this);
		networkReceive = new NetworkReceive(this);
		
		sendThread = new Thread(networkSend);
		receiveThread = new Thread(networkReceive);
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void startThreads() {
		sendThread.start();
		receiveThread.start();
	}
	
	public void send(byte[] message) {
		System.out.println(new String(message));
		sendBuffer.add(message);
	}
	
	public void host() {
		try {
			socket.close();
			socket = new DatagramSocket(1337);
			System.out.println("HOSTING GAME");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void join(String hostname) {
		Peer peer = new Peer(hostname);
		peers.add(peer);
		this.send(("JOINING " + hostname).getBytes());
	}
}
