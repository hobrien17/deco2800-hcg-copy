package com.deco2800.hcg.multiplayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Asynchronous UDP networking
 * 
 * @author Max Crofts
 *
 */
public final class NetworkState {
	DatagramSocket socket;
	List<Peer> peers;
	ConcurrentHashMap<Integer, Message> sendQueue;
	private NetworkSend networkSend;
	private NetworkReceive networkReceive;
	private Thread sendThread;
	private Thread receiveThread;
	
	/**
	 * Initialise NetworkState
	 */
	public NetworkState() {
		peers = new ArrayList<>();
		sendQueue = new ConcurrentHashMap<>();
		
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
	
	/**
	 * Start the networking send/receive threads
	 */
	public void startThreads() {
		sendThread.start();
		receiveThread.start();
	}
	
	/**
	 * Add message to queue
	 * @param message Message to be sent
	 */
	public void sendMessage(Message message) {
		sendQueue.put(message.getId(), message);
	}
	
	/**
	 * Switches network state to server mode
	 */
	public void host() {
		try {
			socket.close();
			socket = new DatagramSocket(1337);
			System.out.println("HOSTING GAME");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Join server
	 * @param hostname Hostname of server
	 */
	public void join(String hostname) {
		Peer peer = new Peer(hostname);
		peers.add(peer);
		this.sendMessage(new Message(MessageType.JOIN, "test".getBytes()));
	}
	
	/**
	 * Used to run network send thread
	 */
	private class NetworkSend implements Runnable {
		private NetworkState networkState;
		
		private NetworkSend(NetworkState networkState) {
			this.networkState = networkState;
		}
		
		/**
		 * Send main process
		 */
		@Override
		public void run() {
			while (!Thread.interrupted()) {
				if (!networkState.peers.isEmpty()) {
					for (Message message : networkState.sendQueue.values()) {
						try {
							byte[] byteArray = message.toByteArray();
							DatagramPacket packet = new DatagramPacket(
									byteArray,
									byteArray.length,
							        InetAddress.getByName(networkState.peers.get(0).getHostname()),
							        1337);
							networkState.socket.send(packet);
							System.out.println("SENT: " + message.getPayloadString());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// temporary fix
					networkState.sendQueue.clear();
				}
			}
		}
	}
	
	/**
	 * Used to run network receive thread
	 */
	private class NetworkReceive implements Runnable {
		private NetworkState networkState;
		
		private NetworkReceive(NetworkState networkState) {
			this.networkState = networkState;
		}
		
		/**
		 * Receive thread process
		 */
		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
					networkState.socket.receive(packet);
					Message message = new Message(packet.getData());
					System.out.println("RECEIVED: " + message.getPayloadString());
					
					// this should definitely end up somewhere else
					switch(message.getType()) {
						case JOIN:
							break;
						case CHAT:
							break;
						default:
							break;
					}
					// handle confirmation separately
					// UDP is unreliable so we have to do this
					if (message.getType() == MessageType.CONFIRMATION) {
						networkState.sendQueue.remove(message.getPayloadInt());
					} else {
						networkState.sendMessage(new Message(MessageType.CONFIRMATION, message.getIdInBytes()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
