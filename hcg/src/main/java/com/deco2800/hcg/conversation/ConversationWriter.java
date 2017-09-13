package com.deco2800.hcg.conversation;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ConversationWriter {

	// Save a Conversation to a file
	public static void writeConversation(Conversation conversation,
			String filename) throws IOException {
		String text = exportConversation(conversation);
		FileWriter fw = new FileWriter(filename);
		fw.write(text);
		fw.close();
	}

	// Export a Conversation as a String
	public static String exportConversation(Conversation conversation) {
		JsonObject jConversation = serialiseConversation(conversation);
		return jConversation.toString();
	}
		
	// Serialise a Conversation as a JsonObject
	public static JsonObject serialiseConversation(Conversation conversation) {
		
		List<ConversationNode> nodes = conversation.getConversationNodes();
		
		JsonObject jConversation = new JsonObject();
		jConversation.add("initialNode", getID(conversation.getInitialNode(), nodes));
		
		JsonArray jNodes = new JsonArray();
		for (ConversationNode node : nodes) {
			jNodes.add(serialiseNode(node, nodes));
		}
		jConversation.add("nodes", jNodes);
		
		return jConversation;
	}

	private static JsonObject serialiseNode(ConversationNode node,
			List<ConversationNode> nodes) {
		
		JsonObject jNode = new JsonObject();
		jNode.add("id", getID(node, nodes));
		jNode.addProperty("nodeText", node.getNodeText());
		
		JsonArray jOptions = new JsonArray();
		for (ConversationOption option : node.getOptions()) {
			jOptions.add(serialiseOption(option, nodes));
		}
		jNode.add("options", jOptions);
		
		return jNode;
	}
	
	private static JsonObject serialiseOption(ConversationOption option,
			List<ConversationNode> nodes) {
		
		JsonObject jOption = new JsonObject();
		jOption.addProperty("optionText", option.getOptionText());
		jOption.add("target", getID(option.getTarget(), nodes));
		//TODO add actions
		
		return jOption;
	}
	
	private static JsonElement getID(ConversationNode node, List<ConversationNode> nodes) {
		int index = nodes.indexOf(node);
		if (index == -1) {
			return new JsonNull();
		} else {
			return new JsonPrimitive(""+index);
		}
	}
}
