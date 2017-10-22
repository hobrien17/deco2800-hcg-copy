package com.deco2800.hcg.managers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.contexts.*;
import com.deco2800.hcg.multiplayer.*;
import com.deco2800.hcg.observers.ServerObserver;

/**
 * Lockstep UDP network manager.
 *
 * @author Max Crofts
 */
public final class NetworkManager extends Manager {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);
	private static final byte[] MESSAGE_HEADER = "H4RDC0R3".getBytes();
	
	private ArrayList<ServerObserver> serverListeners = new ArrayList<>();

	private DatagramChannel channel;
	// TODO: a HashMap is probably not the best collection for the lobby
	//       shouldn't be a big issue for the moment
	private HashMap<Integer, SocketAddress> sockets; // peers we are actually connected to
	private HashMap<Integer, byte[]> sendQueue;
	private HashMap<Integer, Long> peerTickCounts;
	private int acked;
	private HashMap<Integer, Integer> peerSequenceNumbers;
	private boolean host = false;
	private boolean multiplayerGame = false;
	
	private GameManager gameManager;
	private ContextManager contextManager;
	private PlayerInputManager playerInputManager;
	
	private ByteBuffer messageBuffer;
	private ByteBuffer sendBuffer;
	private ByteBuffer receiveBuffer;
	
	private Random random = new Random();

	private String lobbyName;

	/**
	 * Initialises the network manager
	 * @param hostGame Boolean indicating if we are hosting a game
	 */
	public void init(boolean hostGame) {
		sockets = new HashMap<>();
		sendQueue = new HashMap<>();
		peerTickCounts = new HashMap<>();
		peerSequenceNumbers = new HashMap<>();
		
		gameManager = GameManager.get();
		contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
		playerInputManager = (PlayerInputManager) gameManager.getManager(PlayerInputManager.class);
		
		// initialise channel
		try {
			if (channel != null) {
				channel.close();
			}
			channel = DatagramChannel.open();
			if (hostGame) {
				channel.socket().bind(new InetSocketAddress(1337));
			}
			channel.configureBlocking(false);
		} catch (IOException e) {
			LOGGER.error("Failed to initialise DatagramChannel", e);
		}
		
		// allocate buffers
		// used to construct messages
		messageBuffer = ByteBuffer.allocateDirect(1400).order(ByteOrder.LITTLE_ENDIAN);
		// used to send messages
		sendBuffer = ByteBuffer.allocateDirect(1400).order(ByteOrder.LITTLE_ENDIAN);
		// used to receive messages
		receiveBuffer = ByteBuffer.allocateDirect(1400).order(ByteOrder.LITTLE_ENDIAN);

		host = hostGame;
	}
	
	/**
	 * Returns the name of the game lobby
	 * @return Lobby name
	 */
	public String getLobbyName() {
		return lobbyName;
	}

	/**
	 * Updates the name of the lobby for game hosting
	 * @param name Lobby name
	 */
	public void setLobbyName(String name) {
		lobbyName = name;
	}
	
	/**
	 * Returns size of send queue
	 * @return Queue size
	 */
	public Integer getSendQueueSize(){
		return sendQueue.size();
	}
	
	/**
	 * Returns a random int
	 * @return Random int
	 */
	public int getNextRandomInt() {
		return random.nextInt();
	}
	
	/**
	 * Returns a random int
	 * @param bound The exclusive upper bound
	 * @return Random int
	 */
	public int getNextRandomInt(int bound) {
		return random.nextInt(bound);
	}
	
	/**
	 * Returns a random float
	 * @return Random float
	 */
	public float getNextRandomFloat() {
		return random.nextFloat();
	}
	
	/**
	 * Returns a random, normally distributed double
	 * @return Gaussian double
	 */
	public double getNextGaussian() {
		return random.nextGaussian();
	}
	
	/**
	 * Sets the networked random generator's seed
	 * @param seed The seed
	 */
	public void setSeed(long seed) {
		random.setSeed(seed);
	}
	
	/**
	 * Updates a peer's tick count
	 * @param peer Peer ID
	 * @param tick Tick count
	 */
	public void updatePeerTickCount(int peer, long tick) {
		if (peerTickCounts.get(peer) == null || tick > peerTickCounts.get(peer)) {
			peerTickCounts.put(peer, tick);
		}
	}
	
	/**
	 * Resets all tick counts
	 */
	public void resetPeerTickCounts() {
		peerTickCounts = new HashMap<>();
	}
	
	/**
	 * Sets the multiplayer game flag
	 * @param multiplayerGame Boolean indicating if the user is in a multiplayer game
	 */
	public void setMultiplayerGame(boolean multiplayerGame) {
		this.multiplayerGame = multiplayerGame;
	}

	/**
	 * Checks if user is in a multiplayer game
	 * @return <code>true</code> if user is in a multiplayer game
	 */
	public boolean isMultiplayerGame() {
		return multiplayerGame;
	}
	
	/**
	 * Checks if we are hosting a game
	 * @return Boolean indicating whether network state is hosting a game
	 */
	public boolean isHost() {
		return host;
	}
	
	/**
	 * Checks if we are hosting a discoverable LAN game
	 * @return <code>true</code> if local game should discoverable over LAN
	 */
	public boolean isDiscoverable() {
		return host && contextManager.currentContext() instanceof LobbyContext;
	}
	
	/**
	 * Adds message to send queue
	 * @param message Message to add
	 * @return <code>true</code> if message was successfully added to queue
	 */
	public boolean queueMessage(Message message) {
		// clear buffer
		messageBuffer.clear();
		// pack data
		message.packData(messageBuffer);
		// get byte array
		messageBuffer.flip();
		byte[] bytes = new byte[messageBuffer.remaining()];
		messageBuffer.get(bytes);
		// add byte array to queue
		return sendQueue.put(message.getId(), bytes) == null;
	}
	
	/**
	 * Adds a server listener
	 * @param observer The ServerObserver to be added
	 */
	public void addServerListener(ServerObserver observer) {
		serverListeners.add(observer);
	}

	/**
	 * Removes the specified listener from the list of server listeners
	 * @param observer The ServerObserver to be removed
	 */
	public void removeServerListener(ServerObserver observer) {
		serverListeners.remove(observer);
	}
	
	/**
	 * Called when a server is found
	 * @param lobbyName The server's lobby name
	 * @param hostName The server's host name
	 */
	public void serverFound(String lobbyName, String hostName) {
		for (ServerObserver observer : serverListeners) {
			observer.notifyServerFound(lobbyName, hostName);
		}
	}
	
	/**
	 * Adds peer to lobby
	 * @param address The peer's address
	 */
	public void addPeer(SocketAddress address) {
		sockets.put(sockets.size(), address);
	}
	
	/**
	 * Sends discovery messages to all broadcast interfaces
	 */
	public void refreshLocalServers() {
		// try to set broadcast flag
		try {
			channel.socket().setBroadcast(true);
		} catch (SocketException e) {
			LOGGER.error("Could not set socket broadcast flag", e);
			return;
		}
		
		try {
			// iterate over all network interfaces
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					// get interface's broadcast address
					InetAddress broadcastAddress = interfaceAddress.getBroadcast();
					if (broadcastAddress == null) {
						continue;
					}

					try {
						// send discovery message
						SocketAddress socketAddress = new InetSocketAddress(broadcastAddress, 1337);
						sendOnce(new DiscoveryMessage(getNextRandomInt()), socketAddress);
					} catch (IOException e) {
						LOGGER.error("Failed to send discovery message", e);
					}
				}
			}
		} catch (SocketException e) {
			LOGGER.error("Could not get network interfaces", e);
		}
		
		// try to clear broadcast flag
		try {
			channel.socket().setBroadcast(false);
		} catch (SocketException e) {
			LOGGER.error("Could not clear socket broadcast flag", e);
		}
	}
	
	/**
	 * Joins server
	 * @param hostname Host name of server to join
	 */
	public void join(String hostname) {
		SocketAddress socketAddress = new InetSocketAddress(hostname, 1337);
		// add host to peers
		sockets.put(0, socketAddress);
		// try to connect
		queueMessage(new JoiningMessage(getNextRandomInt()));
	}

	/**
	 * Starts a co-op game
	 * 
	 * <p>
	 * Note that this method should only be called by the host
	 * </p>
	 */
	public void startGame() {
		int seed = getNextRandomInt();
		queueMessage(new StartMessage(seed));
		random.setSeed((long) seed);
		contextManager.pushContext(new MultiplayerCharacterContext());
	}
	
	/**
	 * Determines whether NetworkManager is properly in sync with other clients
	 * @return <code>true</code> if it is not safe to proceed
	 */
	public boolean shouldBlock() {
		if (!(contextManager.currentContext() instanceof PlayContext)) {
			return false;
		}
		
		if (multiplayerGame) {
			if (playerInputManager.getInputTick() > 0 && peerTickCounts.isEmpty()) {
				return true;
			}

			for (long tick : peerTickCounts.values()) {
				if (tick <= playerInputManager.getInputTick()) {
					return true;
				}
			}
		}
		
		// queue mouse input
		playerInputManager.queueLocalInput(
				InputType.MOUSE_MOVED,
				null,
				new float[] {playerInputManager.getLocalMouseX(), playerInputManager.getLocalMouseY()});
		// increment input tick
		playerInputManager.incrementInputTick();
		
		return false;
	}
	
	/**
	 * Performs send and receive
	 */
	public void tick() {
		if (multiplayerGame) {
			send(sendQueue.entrySet().iterator());
			receive();
		}
	}

	/**
	 * Recurses over messages that are queued and sends them using the smallest number of packets
	 * @param messageIterator Message entry set iterator
	 */
	private void send(Iterator<Entry<Integer, byte[]>> messageIterator) {
		sendBuffer.clear();
		
		if (!messageIterator.hasNext()) {
			return;
		}
		
		// iterate through queued messages
		do {
			Entry<Integer, byte[]> next = messageIterator.next();
			int id = next.getKey();
			byte[] bytes = next.getValue();
			
			// remove message if it has been acked
			if (id <= acked) {
				messageIterator.remove();
				continue;
			}
			
			// make sure we don't exceed 1400 bytes
			if (bytes.length <= sendBuffer.remaining()) {
				sendBuffer.put(bytes);
			} else {
				break;
			}
		} while (messageIterator.hasNext());
		
		sendBuffer.flip();
		// if not empty...
		if (sendBuffer.hasRemaining()) {
			// on the server peers contains all connected clients
			// for a regular peer it only contains the server
			for (SocketAddress peer : sockets.values()) {
				try {
					channel.send(sendBuffer, peer);
				} catch (IOException e) {
					LOGGER.error("Failed to send message", e);
				}
			}
		}
		
		// if we have more to send...
		send(messageIterator);
	}
	
	/**
	 * Sends a single message exactly once
	 * 
	 * <p>
	 * Note that the message is unreliable and may never be received
	 * </p>
	 * 
	 * @param message Message to be sent
	 * @param address Address to send to
	 * @throws IOException If an IO error occurs
	 */
	public void sendOnce(Message message, SocketAddress address) throws IOException {
		sendBuffer.clear();
		message.packData(sendBuffer);
		sendBuffer.flip();
		channel.send(sendBuffer, address);
	}

	/**
	 * Receives a single message
	 */
	public void receive() {
		// acquire buffer from channel
		receiveBuffer.clear();
		SocketAddress address = null;
		try {
			address = channel.receive(receiveBuffer);
		} catch (IOException e) {
			LOGGER.error("Failed to receive message", e);
			return;
		}
		if (address == null) {
			// no message received
			return;
		}
		receiveBuffer.flip();
		if (!receiveBuffer.hasRemaining()) {
			// empty message
			return;
		}
		
		try {
			while (receiveBuffer.hasRemaining()) {
				// get message id
				Integer messageId = Message.getId(receiveBuffer);
				// get message type
				MessageType messageType = Message.getType(receiveBuffer);
				
				if (messageType == MessageType.ACK) {
					if (messageId > acked) {
						acked = messageId;
					}
					return;
				}
				
				// get message
				Message message;
				switch (messageType) {
					case DISCOVERY:
						message = new DiscoveryMessage(address);
						break;
					case HOST:
						message = new HostMessage(address);
						break;
					case JOINING:
						message = new JoiningMessage(address);
						break;
					case JOINED:
						message = new JoinedMessage(address);
						break;
					case START:
						message = new StartMessage(address);
						break;
					case CHARACTER:
						message = new CharacterMessage(address);
						break;
					case WORLD_MAP:
						message = new WorldMapMessage(address);
						break;
					case LEVEL_START:
						message = new LevelStartMessage(address);
						break;
					case LEVEL_END:
						message = new LevelEndMessage(address);
						break;
					case INPUT:
						message = new InputMessage(address);
						break;
					case CHAT:
						message = new ChatMessage(address);
						break;
					default:
						throw new MessageFormatException();
				}
				
				// unpack message
				message.unpackData(receiveBuffer);

				if (peerSequenceNumbers.get(0) == null
						|| messageId > peerSequenceNumbers.get(0).intValue()) {
					// process message
					message.process();
					// make sure we don't process this again
					peerSequenceNumbers.put(0, messageId);
					// log
					LOGGER.debug("PROCESS: " + messageType.toString());
				}
			}
			
			// acknowledge that we've successfully received this
			messageBuffer.clear();
			// put header
			messageBuffer.put(MESSAGE_HEADER);
			// put ACK id
			messageBuffer.putInt(peerSequenceNumbers.get(0));
			// put type
			messageBuffer.put((byte) MessageType.ACK.ordinal());
			// send ACK to peer
			messageBuffer.flip();
			try {
				channel.send(messageBuffer, address);
			} catch (IOException e) {
				LOGGER.error("Failed to send ACK message", e);
			}
		} catch (BufferOverflowException | BufferUnderflowException
				| MessageFormatException e) {
			// we don't care if a datagram is invalid, only that we don't try to
			// read it any further
			// TODO Implement a timeout so we can get away with this
			LOGGER.error("Invalid datagram received", e);
		}
		
		// receive the next message (if there is one)
		receive();
	}
}
