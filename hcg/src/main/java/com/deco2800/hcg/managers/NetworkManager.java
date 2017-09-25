package com.deco2800.hcg.managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.contexts.*;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.multiplayer.*;

/**
 * Lockstep UDP network manager.
 *
 * @author Max Crofts
 */
public final class NetworkManager extends Manager {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkManager.class);
	private static final byte[] MESSAGE_HEADER = "H4RDC0R3".getBytes();
	private static int MAX_PLAYERS = 4; // maybe 5?

	private DatagramChannel channel;
	// TODO: a HashMap is probably not the best collection for the lobby
	//       shouldn't be a big issue for the moment
	private HashMap<Integer, SocketAddress> sockets; // peers we are actually connected to
	private HashMap<Integer, byte[]> sendQueue;
	private HashMap<Integer, Long> peerTickCounts;
	private ArrayList<Integer> processedIds; // TODO: should be a ring buffer
	private boolean host = false;
	private boolean initialised = false;
	
	private GameManager gameManager;
	private ContextManager contextManager;
	private PlayerManager playerManager;
	
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
		processedIds = new ArrayList<>();
		
		gameManager = GameManager.get();
		contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
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
		
		// allocate buffers
		// used to construct messages
		messageBuffer = ByteBuffer.allocateDirect(1400).order(ByteOrder.LITTLE_ENDIAN);
		// used to send messages
		sendBuffer = ByteBuffer.allocateDirect(1400).order(ByteOrder.LITTLE_ENDIAN);
		// used to receive messages
		receiveBuffer = ByteBuffer.allocateDirect(1400).order(ByteOrder.LITTLE_ENDIAN);

		host = hostGame;
		initialised = true;
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
		// FIXME
	}

	/**
	 * Checks if network state is initialised
	 * @return Boolean indicating if network state has been initialised
	 */
	public boolean isInitialised() {
		return initialised;
	}
	
	/**
	 * Checks if we are hosting a game
	 * @return Boolean indicating whether network state is hosting a game
	 */
	public boolean isHost() {
		return host;
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
	 * Join server
	 * @param hostname Hostname of server
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
	 */
	public void startGame() {
		int seed = getNextRandomInt();
		queueMessage(new StartMessage(seed));
		random.setSeed((long) seed);
		
		// FIXME
		Player otherPlayer = new Player(1, 5, 10, 0);
		otherPlayer.initialiseNewPlayer(5, 5, 5, 5, 5, 20, 20, 20, "Player 2");
		playerManager.addPlayer(otherPlayer);
		contextManager.pushContext(new CharacterCreationContext());
	}
	
	/**
	 * Determines whether NetworkManager is properly in sync with other clients
	 * @return <code>true</code> if it is not safe to proceed
	 */
	private boolean shouldBlock() {
		// TODO
		return false;
	}
	
	/**
	 * Performs send and receive, blocking if necessary
	 */
	public void tick() {
		if (initialised) {
			do {
				send(0);
				receive();
			} while (shouldBlock());
		}
	}

	/**
	 * Recurses over messages that are queued and sends them using the smallest number of packets
	 * @param index Index to start at
	 */
	private void send(int index) {
		sendBuffer.clear();
		// convert queue values to array
		byte[][] toSend = sendQueue.values().toArray(new byte[0][0]);
		// iterate through queued messages messages
		for (; index < toSend.length; index++) {
			// make sure we don't exceed 1400
			if (toSend[index].length <= sendBuffer.remaining()) {
				sendBuffer.put(toSend[index]);
			}
		}
		
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
		if (index != toSend.length) {
			send(index - 1);
		}
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
		} catch (IOException e) {}
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
					// remove message from queue
					receiveBuffer.position(14);
					int removeId = receiveBuffer.getInt();
					sendQueue.remove(removeId);
					return;
				}
				
				// get message
				Message message;
				switch (messageType) {
					case JOINING:
						message = new JoiningMessage();
						// add peer to lobby
						sockets.put(sockets.size() - 1, address);
						break;
					case JOINED:
						message = new JoinedMessage();
						break;
					case START:
						message = new StartMessage();
						break;
					case INPUT:
						message = new InputMessage();
						break;
					case CHAT:
						message = new ChatMessage();
						break;
					default:
						throw new MessageFormatException();
				}
				
				if (messageType != MessageType.ACK) {
					// unpack message
					message.unpackData(receiveBuffer);

					if (!processedIds.contains(messageId)) {
						// process message
						message.process();
						// make sure we don't process this again
						processedIds.add(messageId);
						// log
						LOGGER.debug("RECEIVED: " + messageType.toString());

						// acknowledge that we've received this
						messageBuffer.clear();
						// put header
						messageBuffer.put(MESSAGE_HEADER);
						// put id
						messageBuffer.putInt(-1);
						// put type
						messageBuffer.put((byte) MessageType.ACK.ordinal());
						// put number of fields
						messageBuffer.put((byte) 0);
						// put ACK id
						messageBuffer.putInt(messageId);
						// send ACK to peer
						messageBuffer.flip();
						try {
							channel.send(messageBuffer, address);
						} catch (IOException e) {}
					} else {
						// acknowledge that we've already received this
						messageBuffer.clear();
						// put header
						messageBuffer.put(MESSAGE_HEADER);
						// put id
						messageBuffer.putInt(-1);
						// put type
						messageBuffer.put((byte) MessageType.ACK.ordinal());
						// put number of fields
						messageBuffer.put((byte) 0);
						// put ACK id
						messageBuffer.putInt(messageId);
						// send ACK to peer
						messageBuffer.flip();
						try {
							channel.send(messageBuffer, address);
						} catch (IOException e) {}
					}
				}
			}
		} catch (BufferOverflowException|BufferUnderflowException|MessageFormatException e) {
			// we don't care if a datagram is invalid, only that we don't try to read it any further
			// TODO Implement a timeout so we can get away with this
		}
	}
}
