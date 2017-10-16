package com.deco2800.hcg.observers;

@FunctionalInterface
public interface ServerObserver {

	/**
     * Runs when a server is found
     *
     * @param lobbyName The server's lobby name
     * @param hostName The server's host name
     */
    void notifyServerFound(String lobbyName, String hostName);
    
}
