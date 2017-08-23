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
				for (byte[] message : networkState.sendBuffer) {
					try {
						DatagramPacket packet = new DatagramPacket(
						        message,
						        message.length,
						        InetAddress.getByName(networkState.peers.get(0).getHostname()),
						        1337);
						networkState.socket.send(packet);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
