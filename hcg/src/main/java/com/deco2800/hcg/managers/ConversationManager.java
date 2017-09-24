package com.deco2800.hcg.managers;

import com.deco2800.hcg.conversation.Conversation;
import com.deco2800.hcg.conversation.ConversationReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A manager designed to load and display conversations.
 * @author Richy McGregor
 */
public class ConversationManager extends Manager {

    // All loaded Conversations are refereced by name
    HashMap<String,Conversation> conversations;

    /**
     * Constructor for the ConversationManager
     */
    public ConversationManager() {
        conversations = new HashMap<>();
        try {
            loadConversation("test_conversation_01", "resources/conversations/test_conversation_01.json");
        } catch (IOException e) {
            throw new ResourceLoadException(e);
        }
    }

    /**
     * Read a conversation from disk into memory
     * @param name A name to use to represent the Conversation
     * @param fileName Name of the file on disk
     * @throws IOException If the file cannot be read
     */
    private void loadConversation(String name, String fileName) throws IOException {
        Conversation newConversation = ConversationReader.readConversation(fileName);
        conversations.put(name, newConversation);
    }

    /**
     * Being displaying a stored Conversation
     * @param name The name of the conversation to launch
     */
    public void startConversation(String name, String texture) {
        Conversation conversation = conversations.get(name);
        if (conversation == null) {
            throw new IllegalArgumentException("No conversation by the name \""+name+"\" exists!");
        } else {
            conversation.initiateConversation(texture);
        }
    }

    /**
     * Get the names of all stored Conversations
     * @return A set of Conversation names
     */
    public Set<String> getConversationNames() {
        return new HashSet<>(conversations.keySet());
    }

    /**
     * Get the number of stored Conversations
     * @return Number of stored Conversations
     */
    public int getConversationCount() {
        return conversations.size();
    }
}
