package com.deco2800.hcg.conversation;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationReader {

	// Container used to store information about ConversationNodes during deserialisation
	private static class intermediateStageNode {
		String nodeID;
		JsonArray jOptions;
		public ConversationNode node;
		intermediateStageNode(String nodeID, JsonArray jOptions, ConversationNode node) {
			this.nodeID = nodeID;
			this.jOptions = jOptions;
			this.node = node;
		}
	}

	// Read a Conversation from a file
	public static Conversation readConversation(String filename) throws IOException {
		JsonParser parser = new JsonParser();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		JsonObject jConversation = (JsonObject) parser.parse(reader);
		reader.close();
		return deserialiseConversation(jConversation);
	}

	// Import a Conversation from a String
	public static Conversation importConversation(String TextConversation) {
		JsonParser parser = new JsonParser();
		JsonObject jConversation = (JsonObject) parser.parse(TextConversation);
		return deserialiseConversation(jConversation);
	}

	//TODO safety checks
	// Deserialise a Conversation from a JsonObject
	// Currently need two distinct passes through the JSON
	public static Conversation deserialiseConversation(JsonObject jConversation) {

		Conversation conversation = new Conversation();
		List<intermediateStageNode> intermediateStageNodes = new ArrayList<>();
		Map<String, ConversationNode> nodes = new HashMap<>();

		// First pass
		for (JsonElement jNode : jConversation.getAsJsonArray("nodes")) {
			intermediateStageNode iNode = deserialiseNode((JsonObject) jNode, conversation);
			intermediateStageNodes.add(iNode);
			nodes.put(iNode.nodeID, iNode.node);
		}

		// Second pass
		for (intermediateStageNode iNode : intermediateStageNodes) {
			List<ConversationOption> options = deserialiseNodeOptions(iNode, nodes);
			iNode.node.setup(options);
		}

		String initialNodeID = jConversation.get("initialNode").getAsString();
		ConversationNode initialNode = nodes.get(initialNodeID);
		conversation.setup(new ArrayList<>(nodes.values()), initialNode);
		return conversation;
	}

	private static intermediateStageNode deserialiseNode(JsonObject jNode, Conversation parent) {
		String nodeID = jNode.get("id").getAsString();
		String nodeText = jNode.get("nodeText").getAsString();
		JsonArray jOptions = jNode.getAsJsonArray("options");
		ConversationNode node = new ConversationNode(parent, nodeText);
		return new intermediateStageNode(nodeID, jOptions, node);
	}

	private static List<ConversationOption> deserialiseNodeOptions(intermediateStageNode iNode, Map<String, ConversationNode> nodes) {
		List<ConversationOption> options = new ArrayList<>();
		for (JsonElement jOption : iNode.jOptions) {
			options.add(deserialiseOption((JsonObject) jOption, iNode.node, nodes));
		}
		return options;
	}

	private static ConversationOption deserialiseOption(JsonObject jOption, ConversationNode parent, Map<String, ConversationNode> nodes) {
		String optionText = jOption.get("optionText").getAsString();
		ConversationNode target;
		JsonElement jTarget = jOption.get("target");
		if (jTarget instanceof JsonNull) {
			target = null;
		} else {
			String targetID = jTarget.getAsString();
			target = nodes.get(targetID);
		}
		AbstractConversationCondition condition = null; 				//TODO read condition from JSON
		List<AbstractConversationAction> actions = new ArrayList<>();	//TODO read actions from JSON
		return new ConversationOption(parent, optionText, target, condition, actions);
	}

}


