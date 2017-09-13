/**
 * Testing network in multiplayer function 
 * 
 * @author Duc (Ethan) Phan 
 *
 */
package com.deco2800.hcg.multiplayer;

import static org.junit.Assert.*;

import org.junit.*;



public class NetworkStateTest {
	private static NetworkState network;
	private byte[] payload = "123456789ABC".getBytes();
	private byte[] payload1 = "123456789ABCD".getBytes();
	//Message message1 = new Message(MessageType.CHAT, payload);
	//Message message2 = new Message(MessageType.CHAT, payload1);
	
	
	
	@Test
	public void InitializeTest() {
		/*
		network.init(false);
		System.out.println(network.isInitialised());
		assert(network.isInitialised() == true);
		*/
	}
	@Test
	public void sendMessageTest() {
		/*
		network.init(false);
		network.sendMessage(messsage2);
		
		System.out.println(network.sendQueue.size());
		assertTrue("Incorrect number of messages sent", network.sendQueue.size() == 1);
	
		network.sendChatMessage("String 1");
		System.out.println(network.sendQueue.size());
		assertTrue("Incorrect number of messages sent", network.sendQueue.size() == 2);
		
		network.join("HOST_1");
		System.out.println(network.sockets.values());
		assertTrue("Correct name of socket address", network.sockets.values().toString().equals("[HOST_1:1337]"));
		*/
	}

	
}
