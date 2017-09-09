package com.deco2800.hcg.multiplayer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.MessageManager;
import com.deco2800.hcg.managers.PlayerInputManager;
import com.deco2800.hcg.managers.PlayerManager;

/**
 * Asynchronous UDP networking
 *
 * @author Max Crofts
 *
 */
public final class NetworkState {
	static final Logger LOGGER = LoggerFactory.getLogger(NetworkState.class);

	static DatagramChannel channel;
	// TODO: a HashMap is probably not the best collection for the lobby
	//       shouldn't be a big issue for the moment
	static ConcurrentHashMap<Integer, SocketAddress> sockets; // peers we are actually connected to
	static ConcurrentHashMap<Integer, Message> sendQueue;
	private static ArrayList<Integer> processedIds; // TODO: should be a ring buffer
	private static boolean initialised = false;
	
	private static GameManager gameManager;
	private static ContextManager contextManager;
	private static MessageManager messageManager;
	private static PlayerInputManager playerInputManager;
	private static PlayerManager playerManager;

	private NetworkState() {}

	/**
	 * Initialises NetworkState
	 * @param hostGame
	 */
	public static void init(boolean hostGame) {
		sockets = new ConcurrentHashMap<>();
		sendQueue = new ConcurrentHashMap<>();
		processedIds = new ArrayList<>();
		
		gameManager = GameManager.get();
		contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
		messageManager = (MessageManager) gameManager.getManager(MessageManager.class);
		playerInputManager = (PlayerInputManager) gameManager.getManager(PlayerInputManager.class);
		playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
		
		// initialise channel
		try {
			channel = DatagramChannel.open();
			if (hostGame) {
				channel.socket().bind(new InetSocketAddress(1337));
			}
			channel.configureBlocking(false);
		} catch (IOException e) {
			LOGGER.error("Failed to initialise DatagramChannel", e);
		}
		
		// start networking thread
		// TODO: try to not use a thread
		(new Thread() {
			public void run() {
				while (!Thread.interrupted()) {
					send();
					receive();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {}
				}
			}
		}).start();

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
		String printString = channel.socket().getLocalAddress().getHostName() + ": " + chatMessage;
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
	
	public static void send() {
		// on the server peers contains all connected clients
		// for a regular peer it only contains the server
		for (SocketAddress peer : sockets.values()) {
			for (Message message : sendQueue.values()) {
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				buffer.clear();
				buffer.put(message.toByteArray());
				buffer.flip();
				try {
					channel.send(buffer, peer);
				} catch (IOException e) {
					LOGGER.error("Failed to send message", e);
				}
			}
		}
	}
	
	public static void receive() {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.clear();
			SocketAddress address = channel.receive(buffer);
			if (address == null) {
				return;
			}
			buffer.flip();
			byte[] bytes = new byte[1024];
			buffer.get(bytes, 0, buffer.remaining());
			Message message = new Message(bytes);
			Integer messageId = new Integer((int) message.getId());
			if (message.getType() != MessageType.CONFIRMATION && !processedIds.contains(messageId)) {
				// TODO: this should end up somewhere else
				switch (message.getType()) {
					case JOINING:
						// add peer to lobby
						NetworkState.sockets.put(
								NetworkState.sockets.size() - 1, address);
						// send joined message
						Message joinedMessage = new Message(MessageType.JOINED, message.getIdInBytes());
						ByteBuffer joinedBuffer = ByteBuffer.allocate(1024);
						joinedBuffer.clear();
						joinedBuffer.put(joinedMessage.toByteArray());
						joinedBuffer.flip();
						channel.send(joinedBuffer, address);
						// fall through to spawn other player
						// TODO: we need to support more (4?) players
					case JOINED:
						// TODO: we need to communicate how many other players are already in the
						//       game as well as their state, unless we only finalise that after
						//       the host starts the game from the lobby
						Player otherPlayer = new Player(1, 5, 10, 0);
						otherPlayer.initialiseNewPlayer(5, 5, 5, 5, 5, 20);
						playerManager.addPlayer(otherPlayer);
						playerManager.spawnPlayers();
						break;
					case INPUT:
						InputType inputType = InputType.values()[message.getPayloadInt(0)];
						// TODO: handle input for more than one player
						switch (inputType) {
							case KEY_DOWN:
								playerInputManager.keyDown(1, message.getPayloadInt(1));
								break;
							case KEY_UP:
								playerInputManager.keyUp(1, message.getPayloadInt(1));
								break;
							case MOUSE_MOVED:
								playerInputManager.mouseMoved(
										1,
										message.getPayloadInt(1),
										message.getPayloadInt(2));
								break;
							case TOUCH_DOWN:
								playerInputManager.touchDown(
										1,
										message.getPayloadInt(1),
										message.getPayloadInt(2),
										message.getPayloadInt(3),
										message.getPayloadInt(4));
								break;
							case TOUCH_DRAGGED:
								playerInputManager.touchDragged(
										1,
										message.getPayloadInt(1),
										message.getPayloadInt(2),
										message.getPayloadInt(3));
								break;
							case TOUCH_UP:
								playerInputManager.touchUp(
										1,
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
				ByteBuffer confirmBuffer = ByteBuffer.allocate(1024);
				confirmBuffer.clear();
				confirmBuffer.put(confirmMessage.toByteArray());
				confirmBuffer.flip();
				channel.send(confirmBuffer, address);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to receive message", e);
		}
	}
}
