package com.deco2800.hcg.multiplayer;

import java.net.*;

/**
 * Asynchronous UDP networking
 * 
 * @author Max Crofts
 *
 */
public class NetworkReceive implements Runnable {
	NetworkState networkState;
	
	public NetworkReceive(NetworkState networkState) {
		this.networkState = networkState;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
				networkState.socket.receive(packet);
				Message message = new Message(packet.getData());
				System.out.println("RECEIVED: " + message.getPayloadString());
				/*
				if (message.getType() == MessageType.CONFIRMATION) {
					networkState.sendBuffer.remove(message.getPayloadInt());
				} else {
					networkState.send(new Message(MessageType.CONFIRMATION, message.getIdInBytes()));
				}
				*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
