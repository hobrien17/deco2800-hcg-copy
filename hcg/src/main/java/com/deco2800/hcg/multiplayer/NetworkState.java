package com.deco2800.hcg.multiplayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Asynchronous UDP networking
 * 
 * @author Max Crofts
 *
 */
public final class NetworkState {
	DatagramSocket socket;
	// TODO: a HashMap is probably not the best collection for the lobby
	//       shouldn't be a big issue for the moment
	ConcurrentHashMap<Integer, SocketAddress> peers; // the "lobby"
	ConcurrentHashMap<Integer, Message> sendQueue;
	private NetworkSend networkSend;
	private NetworkReceive networkReceive;
	private Thread sendThread;
	private Thread receiveThread;
	
	/**
	 * Initialise NetworkState
	 */
	public NetworkState() {
		peers = new ConcurrentHashMap<>();
		sendQueue = new ConcurrentHashMap<>();
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		networkSend = new NetworkSend(this);
		networkReceive = new NetworkReceive(this);
		
		sendThread = new Thread(networkSend);
		receiveThread = new Thread(networkReceive);
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
		Integer id = new Integer((int) message.getId());
		sendQueue.put(id, message);
	}
	
	/**
	 * Add chat message to queue
	 * @param chatMessage String to be sent
	 */
	public void sendChatMessage(String chatMessage) {
		String printString = socket.getLocalAddress().getHostName() + ": " + chatMessage;
		Message printMessage = new Message(MessageType.CHAT, printString.getBytes());
		// print message locally
				System.out.println(printString);
		// send message to peers
		sendMessage(printMessage);
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
		SocketAddress socketAddress = new InetSocketAddress(hostname, 1337);
		// add host to peers
		peers.put(0, socketAddress);
		// try to connect
		this.sendMessage(new Message(MessageType.JOIN, "".getBytes()));
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
				// on the server peers contains all connected clients
				// for a regular peer it only contains the server
				for (SocketAddress peer : networkState.peers.values()) {
					for (Message message : networkState.sendQueue.values()) {
						try {
							byte[] byteArray = message.toByteArray();
							DatagramPacket packet = new DatagramPacket(
									byteArray,
									byteArray.length,
									peer);
							networkState.socket.send(packet);
							// log
							System.out.println("SENT: " + message.getType().toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Used to run network receive thread
	 */
	private class NetworkReceive implements Runnable {
		private NetworkState networkState;
		private ArrayList<Integer> processedIds; // TODO: should be a ring buffer
		
		private NetworkReceive(NetworkState networkState) {
			this.networkState = networkState;
			this.processedIds = new ArrayList<>();
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
					Integer messageId = new Integer((int) message.getId());
					if (message.getType() != MessageType.CONFIRMATION && !processedIds.contains(messageId)) {
						// TODO: this should end up somewhere else
						switch(message.getType()) {
							case JOIN:
								// add peer to lobby
								networkState.peers.put(networkState.peers.size() - 1, packet.getSocketAddress());
								break;
							case CHAT:
								System.out.println(message.getPayloadString());
								break;
							default:
								break;
						}
						// make sure we don't process this again
						processedIds.add(messageId);
						// log
						System.out.println("RECEIVED: " + message.getType().toString());
					} else if (message.getType() == MessageType.CONFIRMATION) {
						// remove message from send queue
						networkState.sendQueue.remove(message.getPayloadInteger());
					} else {
						Message confirmMessage = new Message(MessageType.CONFIRMATION, message.getIdInBytes());
						byte byteArray[] = confirmMessage.toByteArray();
						DatagramPacket confirmPacket = new DatagramPacket(
								byteArray,
								byteArray.length,
								packet.getSocketAddress());
						networkState.socket.send(confirmPacket);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
