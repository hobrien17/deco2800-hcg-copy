package com.deco2800.hcg.conversation;

import com.deco2800.hcg.managers.ResourceLoadException;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ConversationReader {

	private ConversationReader() {} // This should never be instantiated

	// Container used to store information about ConversationNodes during deserialisation
	private static class IntermediateStageNode {
		String nodeID;
		JsonArray jOptions;
		ConversationNode node;
		IntermediateStageNode(String nodeID, JsonArray jOptions, ConversationNode node) {
			this.nodeID = nodeID;
			this.jOptions = jOptions;
			this.node = node;
		}
	}

	// Read a Conversation from a file
	public static Conversation readConversation(String filename) {
		try {
			JsonParser parser = new JsonParser();
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			JsonObject jConversation = (JsonObject) parser.parse(reader);
			reader.close();
			return deserialiseConversation(jConversation);
		} catch (JsonSyntaxException | IOException | ResourceLoadException e) {
			throw new ResourceLoadException("Unable to load conversation: " + filename, e);
		}
	}

	// Import a Conversation from a String
	public static Conversation importConversation(String textConversation) {
		try {
			JsonParser parser = new JsonParser();
			JsonObject jConversation = (JsonObject) parser.parse(textConversation);
			return deserialiseConversation(jConversation);
		} catch (JsonSyntaxException | ResourceLoadException e) {
			throw new ResourceLoadException("Unable to parse conversation: " + textConversation, e);
		}
	}

	//TODO safety checks
	// Deserialise a Conversation from a JsonObject
	// Currently need two distinct passes through the JSON
	public static Conversation deserialiseConversation(JsonObject jConversation) {

		Conversation conversation = new Conversation();
		List<IntermediateStageNode> intermediateStageNodes = new ArrayList<>();
		Map<String, ConversationNode> nodes = new HashMap<>();

		try {

			// First pass
			for (JsonElement jNode : jConversation.getAsJsonArray("nodes")) {
				IntermediateStageNode iNode = deserialiseNode((JsonObject) jNode, conversation);
				intermediateStageNodes.add(iNode);
				nodes.put(iNode.nodeID, iNode.node);
			}

			// Second pass
			for (IntermediateStageNode iNode : intermediateStageNodes) {
				List<ConversationOption> options = deserialiseNodeOptions(iNode, nodes);
				iNode.node.setup(options);
			}

			// Relationship starting nodes
			Map<String, ConversationNode> relationshipMap = new HashMap<>();
			for (Map.Entry<String, JsonElement> entry : jConversation.getAsJsonObject("relationshipMap").entrySet()) {
				String nodeName = entry.getValue().getAsString();
				relationshipMap.put(entry.getKey(), nodes.get(nodeName));
			}

			String initialRelationship = jConversation.get("initialRelationship").getAsString();
			conversation.setup(initialRelationship, relationshipMap, new ArrayList<>(nodes.values()));

		} catch (NullPointerException e) {
			throw new ResourceLoadException(e);
		}

		return conversation;
	}

	private static IntermediateStageNode deserialiseNode(JsonObject jNode, Conversation parent) {
		String nodeID = jNode.get("id").getAsString();
		String nodeText = jNode.get("nodeText").getAsString();
		JsonArray jOptions = jNode.getAsJsonArray("options");
		ConversationNode node = new ConversationNode(parent, nodeText);
		return new IntermediateStageNode(nodeID, jOptions, node);
	}

	private static List<ConversationOption> deserialiseNodeOptions(IntermediateStageNode iNode, Map<String, ConversationNode> nodes) {
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

		List<AbstractConversationCondition> conditions = new ArrayList<>();
		if (jOption.has("conditions")) {
			for (JsonElement jCondition : jOption.getAsJsonArray("conditions")) {
				conditions.add(deserialiseOptionCondition(jCondition));
			}
		}

		List<AbstractConversationAction> actions = new ArrayList<>();
		if (jOption.has("actions")) {
			for (JsonElement jAction : jOption.getAsJsonArray("actions")) {
				actions.add(deserialiseOptionAction(jAction));
			}
		}

		return new ConversationOption(parent, conditions, optionText, actions, target);
	}

	// Parse a JSON condition into a Condition object
	private static AbstractConversationCondition deserialiseOptionCondition(JsonElement jCondition) {

		// Collect options and arguments
		String condition = jCondition.getAsString();
		Scanner scanner = new Scanner(condition).useDelimiter("\\|");
		String command = scanner.next();
		boolean negate = false;
		if (command.charAt(0) == '!') {
			command = command.substring(1);
			negate = true;
		}
		List<String> args = new ArrayList<>();
		while (scanner.hasNext()) {
			args.add(scanner.next());
		}
		scanner.close();

		// Generate the appropriate condition object
		switch (command) {
			case "checkRelationship":    return buildCheckRelationshipCondition(condition, negate, args);
			case "healthPercentBelow":   return buildHealthPercentBelowCondition(condition, negate, args);
			case "questNotStarted":      return buildQuestNotStartedCondition(condition, negate, args);
			case "questActive":          return buildQuestActiveCondition(condition, negate, args);
			case "questCompleted":       return buildQuestCompletedCondition(condition, negate, args);
			default:                     throw new ResourceLoadException("No such condition: " + condition);
		}

	}

	// Parse a JSON action into a Action object
	private static AbstractConversationAction deserialiseOptionAction(JsonElement jCondition) {

		// Collect options and arguments
		String action = jCondition.getAsString();
		Scanner scanner = new Scanner(action).useDelimiter("\\|");
		String command = scanner.next();
		List<String> args = new ArrayList<>();
		while (scanner.hasNext()) {
			args.add(scanner.next());
		}
		scanner.close();

		// Generate the appropriate action object
		switch (command) {
			case "setRelationship":      return buildSetRelationshipAction(action, args);
			case "giveItems":            return buildGiveItemsAction(action, args);
			case "startQuest":           return buildStartQuestAction(action, args);
			case "finishCurrentQuest":   return buildFinishQuestAction(action, args);
			default:                     throw new ResourceLoadException("No such action: " + action);
		}

	}

	// Everything below this line is for building Conditions and Action from a list of arguments

	private static void validateArgumentCount(String source, List<String> args, int expectedArgCount) {
		if (args.size() != expectedArgCount) {
			throw new ResourceLoadException("Wrong number of args in: " + source);
		}
	}

	private static CheckRelationshipCondition buildCheckRelationshipCondition(String source, boolean negate, List<String> args) {
		validateArgumentCount(source, args, 1);
		return new CheckRelationshipCondition(negate, args.get(0));
	}

	private static HealthPercentBelowCondition buildHealthPercentBelowCondition(String source, boolean negate, List<String> args) {
		validateArgumentCount(source, args, 1);
		try {
			return new HealthPercentBelowCondition(negate, Integer.parseInt(args.get(0)));
		} catch (NumberFormatException e) {
			throw new ResourceLoadException("Unparsable int in condition: " + source, e);
		}
	}

	private static QuestNotStartedCondition buildQuestNotStartedCondition(String source, boolean negate, List<String> args) {
		validateArgumentCount(source, args, 1);
		return new QuestNotStartedCondition(negate, args.get(0));
	}

	private static QuestActiveCondition buildQuestActiveCondition(String source, boolean negate, List<String> args) {
		validateArgumentCount(source, args, 1);
		return new QuestActiveCondition(negate, args.get(0));
	}

	private static QuestCompletedCondition buildQuestCompletedCondition(String source, boolean negate, List<String> args) {
		validateArgumentCount(source, args, 1);
		return new QuestCompletedCondition(negate, args.get(0));
	}

	private static SetRelationshipAction buildSetRelationshipAction(String source, List<String> args) {
		validateArgumentCount(source, args, 1);
		return new SetRelationshipAction(args.get(0));
	}

	private static GiveItemsAction buildGiveItemsAction(String source, List<String> args) {
		validateArgumentCount(source, args, 2);
		try {
			return new GiveItemsAction(args.get(0), Integer.parseInt(args.get(1)));
		} catch (NumberFormatException e) {
			throw new ResourceLoadException("Unparsable int in action: " + source, e);
		}
	}

	private static StartQuestAction buildStartQuestAction(String source, List<String> args) {
		validateArgumentCount(source, args, 1);
		return new StartQuestAction(args.get(0));
	}

	private static FinishQuestAction buildFinishQuestAction(String source, List<String> args) {
		validateArgumentCount(source, args, 0);
		return new FinishQuestAction();
	}


}


