package com.deco2800.hcg.multiplayer;

import java.net.*;

public class NetworkSend implements Runnable {
	NetworkState networkState;
	
	public NetworkSend(NetworkState networkState) {
		this.networkState = networkState;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			if (!networkState.peers.isEmpty()) {
				for (Message message : networkState.sendBuffer.values()) {
					try {
						byte[] byteArray = message.toByteArray();
						DatagramPacket packet = new DatagramPacket(
								byteArray,
								byteArray.length,
						        InetAddress.getByName(networkState.peers.get(0).getHostname()),
						        1337);
						networkState.socket.send(packet);
						System.out.println("SENT: " + message.getPayloadString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// temporary fix
				networkState.sendBuffer.clear();
			}
		}
	}
}
