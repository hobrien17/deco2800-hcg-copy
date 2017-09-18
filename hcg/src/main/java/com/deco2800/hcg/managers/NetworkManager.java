package com.deco2800.hcg.managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.contexts.*;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.multiplayer.InputType;
import com.deco2800.hcg.multiplayer.MessageType;
import com.deco2800.hcg.worlds.DemoWorld;

/**
 * Asynchronous UDP networking
 *
 * @author Max Crofts
 *
 */
public final class NetworkManager extends Manager implements TickableManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);
	private static final byte[] MESSAGE_HEADER = "H4RDC0R3".getBytes();
	private static int MAX_PLAYERS = 4; // maybe 5?

	private DatagramChannel channel;
	// TODO: a HashMap is probably not the best collection for the lobby
	//       shouldn't be a big issue for the moment
	private ConcurrentHashMap<Integer, SocketAddress> sockets; // peers we are actually connected to
	private ConcurrentHashMap<Integer, byte[]> sendQueue;
	private ConcurrentLinkedQueue<byte[]> receiveQueue;
	private AtomicLong localTickCount;
	private AtomicLongArray peerTickCounts;
	private ArrayList<Integer> processedIds; // TODO: should be a ring buffer
	private boolean host = false;
	private boolean initialised = false;
	
	private GameManager gameManager;
	private ContextManager contextManager;
	private MessageManager messageManager;
	private PlayerInputManager playerInputManager;
	private PlayerManager playerManager;
	
	private ByteBuffer messageBuffer;
	private ByteBuffer sendBuffer;
	private ByteBuffer receiveBuffer;
	
	private Random messageIdGenerator;

	/**
	 * Initialises NetworkState
	 * @param hostGame
	 */
	public void init(boolean hostGame) {
		sockets = new ConcurrentHashMap<>();
		sendQueue = new ConcurrentHashMap<>();
		receiveQueue = new ConcurrentLinkedQueue<>();
		localTickCount = new AtomicLong();
		peerTickCounts = new AtomicLongArray(MAX_PLAYERS);
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
		(new Thread() {
			public void run() {
				while (!Thread.interrupted()) {
					send();
					receive();
					try {
						Thread.sleep(8);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}

				}
			}
		}).start();
		
		// allocate buffers
		messageBuffer = ByteBuffer.allocateDirect(1024).order(ByteOrder.LITTLE_ENDIAN);
		sendBuffer = ByteBuffer.allocateDirect(1024).order(ByteOrder.LITTLE_ENDIAN);
		receiveBuffer = ByteBuffer.allocateDirect(1024).order(ByteOrder.LITTLE_ENDIAN);
		
		// initialise id generator
		messageIdGenerator = new Random();

		host = hostGame;
		initialised = true;
	}

	/**
	 * Check if network state is initialised
	 * @return Boolean indicating if network state has been initialised
	 */
	public boolean isInitialised() {
		return initialised;
	}
	
	public boolean isHost() {
		return host;
	}
	
	private Integer startNewMessage(MessageType type, int numberOfEntries) {
		// clear buffer
		messageBuffer.clear();
		// put header
		messageBuffer.put(MESSAGE_HEADER);
		// put id
		int id = messageIdGenerator.nextInt();
		messageBuffer.putInt(id);
		// put type
		messageBuffer.put((byte) type.ordinal());
		// put number of entries
		messageBuffer.put((byte) numberOfEntries);
		// return the buffer with header
		return id;
	}
	
	/**
	 * Add input message to queue
	 */
	public void sendInputMessage(int... args) {
		Integer id = startNewMessage(MessageType.INPUT, args.length);
		messageBuffer.putLong(localTickCount.get() + 3);
		messageBuffer.asIntBuffer().put(args);
		messageBuffer.position(messageBuffer.position() + args.length * 4);
		// send message to peers
		messageBuffer.flip();
		byte[] bytes = new byte[messageBuffer.remaining()];
		messageBuffer.get(bytes);
		sendQueue.put(id, bytes);
	}

	/**
	 * Add chat message to queue
	 * @param chatMessage String to be sent
	 * @return String sent to other peers
	 */
	public String sendChatMessage(String message) {
		String string = channel.socket().getLocalAddress().getHostName() + ": " + message;
		Integer id = startNewMessage(MessageType.CHAT, string.getBytes().length);
		// create chat string
		messageBuffer.put(string.getBytes());
		// send message to peers
		messageBuffer.flip();
		byte[] bytes = new byte[messageBuffer.remaining()];
		messageBuffer.get(bytes);
		sendQueue.put(id, bytes);
		return string;
	}

	/**
	 * Join server
	 * @param hostname Hostname of server
	 */
	public void join(String hostname) {
		SocketAddress socketAddress = new InetSocketAddress(hostname, 1337);
		// add host to peers
		sockets.put(0, socketAddress);
		// try to connect
		Integer id = startNewMessage(MessageType.JOINING, 0);
		// send message to peers
		messageBuffer.flip();
		byte[] bytes = new byte[messageBuffer.remaining()];
		messageBuffer.get(bytes);
		sendQueue.put(id, bytes);
	}
	
	public void sendJoinedMessage() {
		Integer id = startNewMessage(MessageType.JOINED, 0);
		// send message to peers
		messageBuffer.flip();
		byte[] bytes = new byte[messageBuffer.remaining()];
		messageBuffer.get(bytes);
		sendQueue.put(id, bytes);
	}
	
	public void startGame() {
		Integer id = startNewMessage(MessageType.START, 0);
		// send message to peers
		messageBuffer.flip();
		byte[] bytes = new byte[messageBuffer.remaining()];
		messageBuffer.get(bytes);
		sendQueue.put(id, bytes);
		
		gameManager.setWorld(new DemoWorld());
		Player otherPlayer = new Player(1, 5, 10, 0);
		otherPlayer.initialiseNewPlayer(5, 5, 5, 5, 5, 20);
		playerManager.addPlayer(otherPlayer);
		playerManager.spawnPlayers();
		contextManager.pushContext(new PlayContext());
	}
	
	public void send() {
		// on the server peers contains all connected clients
		// for a regular peer it only contains the server
		for (SocketAddress peer : sockets.values()) {
			for (byte[] bytes : sendQueue.values()) {
				try {
					sendBuffer.clear();
					sendBuffer.put(bytes);
					sendBuffer.flip();
					channel.send(sendBuffer, peer);
				} catch (IOException e) {
					LOGGER.error("Failed to send message", e);
				}
			}
		}
	}
	
	public void receive() {
		try {
			// acquire buffer from channel
			receiveBuffer.clear();
			SocketAddress address = channel.receive(receiveBuffer);
			if (address == null) {
				// no message received
				return;
			}
			receiveBuffer.flip();
			byte[] messageBytes = new byte[receiveBuffer.remaining()];
			receiveBuffer.get(messageBytes);
			receiveBuffer.flip();
			
			// get header
			byte[] header = new byte[8];
			receiveBuffer.get(header);
			// get id
			Integer messageId = receiveBuffer.getInt();
			// get type
			byte typeByte = receiveBuffer.get();
			MessageType messageType = MessageType.values()[typeByte];
			// get number of entries
			byte numberOfEntries = receiveBuffer.get();
			
			// handle message
			if (messageType != MessageType.ACK && !processedIds.contains(messageId)) {
				if (messageType == MessageType.JOINING) {
					// add peer to lobby
					sockets.put(sockets.size() - 1, address);
				}
				receiveQueue.add(messageBytes);
			} else if (messageType == MessageType.ACK) {
				// remove message from queue
				int removeId = receiveBuffer.getInt();
				sendQueue.remove(removeId);
			} else {
				// acknowledge that we've already received this
				receiveBuffer.clear();
				// put header
				receiveBuffer.put(MESSAGE_HEADER);
				// put null id
				receiveBuffer.putInt(0);
				// put type
				receiveBuffer.put((byte) MessageType.ACK.ordinal());
				// put number of fields
				receiveBuffer.put((byte) 1);
				// put ACK id
				receiveBuffer.putInt(messageId);
				// send ACK to peer
				receiveBuffer.flip();
				channel.send(receiveBuffer, address);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to receive message", e);
		}
	}
	
	public void processReceivedMessages() {
		byte[] bytes;
		while ((bytes = receiveQueue.poll()) != null) {
			messageBuffer.clear();
			messageBuffer.put(bytes);
			messageBuffer.flip();
			
			// get header
			byte[] header = new byte[8];
			messageBuffer.get(header);
			// get id
			Integer messageId = messageBuffer.getInt();
			// get type
			byte typeByte = messageBuffer.get();
			MessageType messageType = MessageType.values()[typeByte];
			// get number of entries
			byte numberOfEntries = messageBuffer.get();
			
			if (!processedIds.contains(messageId)) {
				// TODO: this should end up somewhere else
				switch (messageType) {
					case JOINING:
						// send joined message
						sendJoinedMessage();
					case JOINED:
						// TODO: we need to communicate how many other players are already in the
						//       game as well as their state
						contextManager.pushContext(new LobbyContext());
						break;
					case START:
						// TODO: we need to support more (4?) players
						gameManager.setWorld(new DemoWorld());
						Player otherPlayer = new Player(1, 5, 10, 0);
						otherPlayer.initialiseNewPlayer(5, 5, 5, 5, 5, 20);
						playerManager.addPlayer(otherPlayer);
						playerManager.spawnPlayers();
						contextManager.pushContext(new PlayContext());
						break;
					case INPUT:
						long inputTick = messageBuffer.getLong();
						InputType inputType = InputType.values()[messageBuffer.getInt()];
						// TODO: handle input for more than one player
						switch (inputType) {
							case KEY_DOWN:
								playerInputManager.queueAction(
										inputTick,
										1,
										inputType.ordinal(),
										messageBuffer.getInt());
								break;
							case KEY_UP:
								playerInputManager.queueAction(
										inputTick,
										1,
										inputType.ordinal(),
										messageBuffer.getInt());
								break;
							case MOUSE_MOVED:
								playerInputManager.queueAction(
										inputTick, 
										1,
										inputType.ordinal(),
										messageBuffer.getInt(),
										messageBuffer.getInt());
								break;
							case TOUCH_DOWN:
								playerInputManager.queueAction(
										inputTick,
										1,
										inputType.ordinal(),
										messageBuffer.getInt(),
										messageBuffer.getInt(),
										messageBuffer.getInt(),
										messageBuffer.getInt());
								break;
							case TOUCH_DRAGGED:
								playerInputManager.queueAction(
										inputTick,
										1,
										inputType.ordinal(),
										messageBuffer.getInt(),
										messageBuffer.getInt(),
										messageBuffer.getInt());
								break;
							case TOUCH_UP:
								playerInputManager.queueAction(
										inputTick,
										1,
										inputType.ordinal(),
										messageBuffer.getInt(),
										messageBuffer.getInt(),
										messageBuffer.getInt(),
										messageBuffer.getInt());
								break;
							default:
								break;
						}
						break;
					case CHAT:
						byte[] byteArray = new byte[messageBuffer.remaining()];
						messageBuffer.get(byteArray);
						messageManager.chatMessageReceieved(new String(byteArray));
						break;
					default:
						break;
				}
				// make sure we don't process this again
				processedIds.add(messageId);
				// log
				LOGGER.debug("RECEIVED: " + messageType.toString());
			}
		}
	}

	@Override
	public void onTick(long gameTickCount) {
		if (initialised) {
			localTickCount.getAndIncrement();
		}
	}
}
