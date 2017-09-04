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

import com.deco2800.hcg.contexts.PlayContext;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.MessageManager;
import com.deco2800.hcg.managers.PeerInputManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.DemoWorld;

/**
 * Asynchronous UDP networking
 *
 * @author Max Crofts
 *
 */
public final class NetworkState {
	static final Logger LOGGER = LoggerFactory.getLogger(NetworkState.class);

	static DatagramSocket socket;
	// TODO: a HashMap is probably not the best collection for the lobby
	//       shouldn't be a big issue for the moment
	static ConcurrentHashMap<Integer, SocketAddress> sockets; // peers we are actually connected to
	static ConcurrentHashMap<Integer, Message> sendQueue;
	private static boolean initialised = false;
	private static NetworkSend networkSend;
	private static NetworkReceive networkReceive;
	private static Thread sendThread;
	private static Thread receiveThread;
	
	private static GameManager gameManager;
	private static ContextManager contextManager;
	private static MessageManager messageManager;
	private static PeerInputManager peerInputManager;
	private static PlayerManager playerManager;

	private NetworkState() {}

	/**
	 * Initialises NetworkState
	 * @param hostGame
	 */
	public static void init(boolean hostGame) {
		sockets = new ConcurrentHashMap<>();
		sendQueue = new ConcurrentHashMap<>();
		
		gameManager = GameManager.get();
		contextManager = (ContextManager) GameManager.get().getManager(ContextManager.class);
		messageManager = (MessageManager) GameManager.get().getManager(MessageManager.class);
		peerInputManager = (PeerInputManager) GameManager.get().getManager(PeerInputManager.class);
		playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);

		// initialise socket
		try {
			if (hostGame) {
				socket = new DatagramSocket(1337);
				LOGGER.debug("HOSTING GAME");
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
	 * Add input message to queue
	 */
	public static void sendInputMessage(int... args) {
		Message inputMessage = new Message(MessageType.INPUT, args);
		// send message to peers
		sendMessage(inputMessage);
	}

	/**
	 * Add chat message to queue
	 * @param chatMessage String to be sent
	 * @return String sent to other peers
	 */
	public static String sendChatMessage(String chatMessage) {
		String printString = socket.getLocalAddress().getHostName() + ": " + chatMessage;
		Message printMessage = new Message(MessageType.CHAT, printString.getBytes());
		// send message to peers
		sendMessage(printMessage);
		return printString;
	}

	/**
	 * Join server
	 * @param hostname Hostname of server
	 */
	public static void join(String hostname) {
		SocketAddress socketAddress = new InetSocketAddress(hostname, 1337);
		// add host to peers
		sockets.put(0, socketAddress);
		// try to connect
		sendMessage(new Message(MessageType.JOINING, new byte[0]));
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
				for (SocketAddress peer : NetworkState.sockets.values()) {
					for (Message message : NetworkState.sendQueue.values()) {
						try {
							byte[] byteArray = message.toByteArray();
							DatagramPacket packet =
									new DatagramPacket(byteArray, byteArray.length, peer);
							NetworkState.socket.send(packet);
							// log
							LOGGER.debug("SENT: " + message.getType().toString());
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
					if (message.getType() != MessageType.CONFIRMATION && !processedIds.contains(messageId)) {
						// TODO: this should end up somewhere else
						switch (message.getType()) {
							case JOINING:
								// add peer to lobby
								NetworkState.sockets.put(
										NetworkState.sockets.size() - 1, packet.getSocketAddress());
								// send joined message
								Message joinedMessage = new Message(MessageType.JOINED, message.getIdInBytes());
								byte byteArray[] = joinedMessage.toByteArray();
								DatagramPacket joinedPacket = new DatagramPacket(
										byteArray, byteArray.length, packet.getSocketAddress());
								NetworkState.socket.send(joinedPacket);
								// TODO: extra players should probably be handled by player manager
								Player player = new Player(0, 5, 10, 0);
			                		player.initialiseNewPlayer(5, 5, 5, 5, 5, 20);
			                		gameManager.getWorld().addEntity(player);
								break;
							case JOINED:
								// TODO:
						        break;
							case INPUT:
								InputType inputType = InputType.values()[message.getPayloadInt(0)];
								System.out.println(inputType);
								switch (inputType) {
									case KEY_DOWN:
										peerInputManager.keyDown(0, message.getPayloadInt(1));
										break;
									case KEY_UP:
										peerInputManager.keyUp(0, message.getPayloadInt(1));
										break;
									case TOUCH_DOWN:
										peerInputManager.touchDown(
												0,
												message.getPayloadInt(1),
												message.getPayloadInt(2),
												message.getPayloadInt(3),
												message.getPayloadInt(4));
										break;
									case TOUCH_DRAGGED:
										peerInputManager.touchDragged(
												0,
												message.getPayloadInt(1),
												message.getPayloadInt(2),
												message.getPayloadInt(3));
										break;
									case TOUCH_UP:
										peerInputManager.touchUp(
												0,
												message.getPayloadInt(1),
												message.getPayloadInt(2),
												message.getPayloadInt(3),
												message.getPayloadInt(4));
										break;
									default:
										break;
								}
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
						LOGGER.debug("RECEIVED: " + message.getType().toString());
						LOGGER.debug(new String(packet.getData()));
					} else if (message.getType() == MessageType.CONFIRMATION) {
						// get id for logging
						Message removed = NetworkState.sendQueue.get(message.getPayloadInteger());
						// remove message from send queue
						if (NetworkState.sendQueue.remove(message.getPayloadInteger()) != null) {
							LOGGER.debug("REMOVED: " + removed.getType().toString());
							// log ping
							Integer ping = (int) ((System.currentTimeMillis() % Integer.MAX_VALUE)
									- message.getPayloadInteger());
							LOGGER.debug("PING: " + ping.toString());
						}
					} else {
						Message confirmMessage = new Message(MessageType.CONFIRMATION, message.getIdInBytes());
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
