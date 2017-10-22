/**
 * Testing network in multiplayer function 
 * 
 * @author Duc (Ethan) Phan 
 *
 */
package com.deco2800.hcg.managers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.contexts.LobbyContext;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.multiplayer.JoiningMessage;

public class NetworkManagerTest  {
	private GameManager gameManager;
	private NetworkManager hostNetworkManager;
	private NetworkManager clientNetworkManager;
	
	@Before
	public void setupNetworkManagers() {
		gameManager = GameManager.get();
		hostNetworkManager = new NetworkManager();
		clientNetworkManager = new NetworkManager();
	}
	
	@Test
	public void isMultiplayerGameTest() {
		clientNetworkManager.init(false);
		clientNetworkManager.setMultiplayerGame(true);
		assertTrue(clientNetworkManager.isMultiplayerGame());
	}
	
	@Test
	public void isHostTest() {
		hostNetworkManager.init(true);
		assertThat("Player is not hosting", hostNetworkManager.isHost(), is(equalTo(true)));
		clientNetworkManager.init(false);
		assertThat("Player is hosting", clientNetworkManager.isHost(), is(equalTo(false)));
	}
	
	@Test
	public void isDiscoverableTest() {
		hostNetworkManager.init(true);
		assertFalse(hostNetworkManager.isDiscoverable());
	}
	
	@Test
	public void queueMessageTest() {
		hostNetworkManager.init(true);
		int size = hostNetworkManager.getSendQueueSize();
		assertThat("The message queue contains messages", size, is(equalTo(0)));
		hostNetworkManager.queueMessage(new JoiningMessage(0));
		assertThat("The queue should only have 1 message only", hostNetworkManager.getSendQueueSize(), is(equalTo(1)));
		hostNetworkManager.queueMessage(new JoiningMessage(0));
		assertThat("Queue should have 2 messages", hostNetworkManager.getSendQueueSize(), is(equalTo(2)));
	}
}
