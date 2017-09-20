/**
 * Testing network in multiplayer function 
 * 
 * @author Duc (Ethan) Phan 
 *
 */
package com.deco2800.hcg.multiplayer;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;



public class NetworkStateTest  {
	private GameManager gameManager;
	private NetworkManager networkManager;
	private NetworkManager networkManager2;
	
	@Before
	public void setupNetworkManager(){
		gameManager = GameManager.get();
		networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
		networkManager2 = (NetworkManager) gameManager.getManager(NetworkManager.class);
	}
	@Test
	public void InitializeTest() {
		System.out.println("Initialise Test");
		//assertThat("Not yet initialise but appear to initialise already", networkManager.isInitialised(), is(equalTo(null)));
		networkManager.init(true);
		assertThat("Network is not initialised after initiallising", networkManager.isInitialised(), is(equalTo(true)));
		networkManager2.init(false);
		assertThat("Network is not initialised after initiallising", networkManager2.isInitialised(), is(equalTo(true)));
	}
	
	@Test
	public void HostTest() {		
		System.out.println("Host Test");
		networkManager.init(true);
		assertThat("Player is not hosting", networkManager.isHost(), is(equalTo(true)));
		networkManager.init(false);
		assertThat("Player is hosting", networkManager.isHost(), is(equalTo(false)));
	}
	
	@Test
	public void InputMessageTest(){
		networkManager.init(true);
		//networkManager.sendInputMessage(1,5,8);
		//networkManager.sendInputMessage(1,5,6);
		int size = networkManager.getSendQueueSize();
		assertThat("The message queue contains messages", size, is(equalTo(0)));
		networkManager.sendInputMessage(1,5,6);
		assertThat("The queue should only have 1 message only", networkManager.getSendQueueSize(), is(equalTo(1)) );
		networkManager.sendChatMessage("This is a test");
		assertThat("Queue should have 2 messages", networkManager.getSendQueueSize(), is(equalTo(2)));
	}
}
