package com.deco2800.hcg.conversation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConversationWriter {
	
	public static void writeConversation(Conversation conversation,
			ConversationNode initalConversationNode,
			String filename) throws IOException {
		String text = exportConversation(conversation, initalConversationNode);
		FileWriter fw = new FileWriter(filename);
		fw.write(text);
		fw.close();
	}
	
	public static String exportConversation(Conversation conversation,
			ConversationNode initalConversationNode) {
		JsonObject jConversation = serialiseConversation(conversation, initalConversationNode);
		return jConversation.toString();
	}
		
	
	public static JsonObject serialiseConversation(Conversation conversation,
			ConversationNode initalConversationNode) {
		
		List<ConversationNode> nodes = conversation.getConversationNodes();
		
		JsonObject jConversation = new JsonObject();
		//TODO check if -1
		jConversation.addProperty("initalNode", nodes.indexOf(initalConversationNode));
		
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
		jNode.addProperty("id", nodes.indexOf(node));
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
		jOption.addProperty("target", nodes.indexOf(option.getTarget()));
		//TODO add actions
		
		return jOption;
	}
}
