package com.deco2800.hcg.multiplayer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.MessageManager;

/**
 * Asynchronous UDP networking
 *
 * @author Max Crofts
 *
 */
public final class NetworkState {
	static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

	static DatagramSocket socket;
	// TODO: a HashMap is probably not the best collection for the lobby
	//       shouldn't be a big issue for the moment
	static ConcurrentHashMap<Integer, SocketAddress> peers; // the "lobby"
	static ConcurrentHashMap<Integer, Message> sendQueue;
	private static boolean initialised = false;
	private static NetworkSend networkSend;
	private static NetworkReceive networkReceive;
	private static Thread sendThread;
	private static Thread receiveThread;
	
	private static MessageManager messageManager;

	private NetworkState() {}

	/**
	 * Initialises NetworkState
	 * @param hostGame
	 */
	public static void init(boolean hostGame) {
		peers = new ConcurrentHashMap<>();
		sendQueue = new ConcurrentHashMap<>();
		
		messageManager = (MessageManager) GameManager.get().getManager(MessageManager.class);

		// initialise socket
		try {
			if (hostGame) {
				socket = new DatagramSocket(1337);
				System.out.println("HOSTING GAME");
			} else {
				socket = new DatagramSocket();
			}
		} catch (SocketException e) {
			LOGGER.error("Failed to initialise socket", e);
		}

		// initialise threads
		networkSend = new NetworkSend();
		networkReceive = new NetworkReceive();
		sendThread = new Thread(networkSend);
		receiveThread = new Thread(networkReceive);
		// start the networking send/receive threads
		sendThread.start();
		receiveThread.start();

		initialised = true;
	}

	/**
	 * Check if network state is initialised
	 * @return Boolean indicating if network state has been initialised
	 */
	public static boolean isInitialised() {
		return initialised;
	}

	/**
	 * Add message to queue
	 * @param message Message to be sent
	 */
	public static void sendMessage(Message message) {
		Integer id = new Integer((int) message.getId());
		sendQueue.put(id, message);
	}

	/**
	 * Add chat message to queue
	 * @param chatMessage String to be sent
	 */
	public static void sendChatMessage(String chatMessage) {
		String printString = socket.getLocalAddress().getHostName() + ": " + chatMessage;
		Message printMessage = new Message(MessageType.CHAT, printString.getBytes());
		// print message locally
		System.out.println(printString);
		// send message to peers
		sendMessage(printMessage);
	}

	/**
	 * Join server
	 * @param hostname Hostname of server
	 */
	public static void join(String hostname) {
		SocketAddress socketAddress = new InetSocketAddress(hostname, 1337);
		// add host to peers
		peers.put(0, socketAddress);
		// try to connect
		sendMessage(new Message(MessageType.JOIN, "".getBytes()));
	}

	/**
	 * Used to run network send thread
	 */
	private static class NetworkSend implements Runnable {
		/**
		 * Send main process
		 */
		@Override
		public void run() {
			while (!Thread.interrupted()) {
				// on the server peers contains all connected clients
				// for a regular peer it only contains the server
				for (SocketAddress peer : NetworkState.peers.values()) {
					for (Message message : NetworkState.sendQueue.values()) {
						try {
							byte[] byteArray = message.toByteArray();
							DatagramPacket packet =
									new DatagramPacket(byteArray, byteArray.length, peer);
							NetworkState.socket.send(packet);
							// log
							System.out.println("SENT: " + message.getType().toString());
						} catch (Exception e) {
							LOGGER.error("Failed to send message", e);
						}
					}
				}
			}
		}
	}

	/**
	 * Used to run network receive thread
	 */
	private static class NetworkReceive implements Runnable {
		private ArrayList<Integer> processedIds; // TODO: should be a ring buffer

		private NetworkReceive() {
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
					NetworkState.socket.receive(packet);
					Message message = new Message(packet.getData());
					Integer messageId = new Integer((int) message.getId());
					if (message.getType() != MessageType.CONFIRMATION
							&& !processedIds.contains(messageId)) {
						// TODO: this should end up somewhere else
						switch (message.getType()) {
							case JOIN:
								// add peer to lobby
								NetworkState.peers.put(
										NetworkState.peers.size() - 1, packet.getSocketAddress());
								break;
							case CHAT:
								messageManager.chatMessageReceieved(message);
								break;
							default:
								break;
						}
						// make sure we don't process this again
						processedIds.add(messageId);
						// log
						System.out.println("RECEIVED: " + message.getType().toString());
					} else if (message.getType() == MessageType.CONFIRMATION) {
						// get id for logging
						Message removed = NetworkState.sendQueue.get(message.getPayloadInteger());
						// remove message from send queue
						if (NetworkState.sendQueue.remove(message.getPayloadInteger()) != null) {
							System.out.println("REMOVED: " + removed.getType().toString());
						}
					} else {
						Message confirmMessage =
								new Message(MessageType.CONFIRMATION, message.getIdInBytes());
						byte byteArray[] = confirmMessage.toByteArray();
						DatagramPacket confirmPacket = new DatagramPacket(
								byteArray, byteArray.length, packet.getSocketAddress());
						NetworkState.socket.send(confirmPacket);
					}
				} catch (Exception e) {
					LOGGER.error("Failed to receive message", e);
				}
			}
		}
	}
}
