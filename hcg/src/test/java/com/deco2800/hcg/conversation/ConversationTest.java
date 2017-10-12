package com.deco2800.hcg.conversation;

import com.deco2800.hcg.managers.ResourceLoadException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tests for the Conversation System
 * @author Richy McGregor
 */
public class ConversationTest {

	// JSON test data & associated Conversation
	private String JsonConversationA = "{\"initialRelationship\":\"neutral\",\"relationshipNodes\":{\"neutral\":\"0\"},\"nodes\":[{\"id\":\"0\",\"nodeText\":\"Foo\",\"options\":[{\"conditions\":[],\"optionText\":\"to Bar\",\"target\":\"1\",\"actions\":[]},{\"conditions\":[],\"optionText\":\"to Baz\",\"target\":\"2\",\"actions\":[]}]},{\"id\":\"1\",\"nodeText\":\"Bar\",\"options\":[{\"conditions\":[],\"optionText\":\"to Baz\",\"target\":\"2\",\"actions\":[]},{\"conditions\":[],\"optionText\":\"quit\",\"target\":null,\"actions\":[]}]},{\"id\":\"2\",\"nodeText\":\"Baz\",\"options\":[{\"conditions\":[],\"optionText\":\"back to Foo\",\"target\":\"0\",\"actions\":[]}]}]}";
	private String JsonConversationB = "{\"initialNode\":\"0\",\"nodes\":[{\"id\":\"0\",\"nodeText\":\"Foo\",\"options\":[{\"optionText\":\"to Bar\",\"target\":\"1\"},{\"optionText\":\"to Baz\",\"target\":\"2\"}]},{\"id\":\"1\",\"nodeText\":\"Bar\",\"options\":[{\"optionText\":\"to Baz\",\"target\":\"2\"},{\"optionText\":\"quit\",\"target\":null}]},{\"id\":\"2\",\"nodeText\":\"Baz\",\"options\":[{\"optionText\":\"back to Foo\",\"target\":\"0\"}]}]}";
	private String JsonConversationC = "{{{{{{{{{{{";
	private Conversation conversationA;
	
	@Before
	public void genConversationA() {
		// Please never ever write code like this
		conversationA = new Conversation();
		List<ConversationNode> nodes = new ArrayList<>();
		ConversationNode foo = new ConversationNode(conversationA, "Foo");
		nodes.add(foo);
		ConversationNode bar = new ConversationNode(conversationA, "Bar");
		nodes.add(bar);
		ConversationNode baz = new ConversationNode(conversationA, "Baz");
		nodes.add(baz);
		List<ConversationOption> fooOptions = new ArrayList<>();
		fooOptions.add(new ConversationOption(foo, new ArrayList<>(),"to Bar", new ArrayList<>(), bar));
		fooOptions.add(new ConversationOption(foo, new ArrayList<>(),"to Baz", new ArrayList<>(), baz));
		foo.setup(fooOptions);
		List<ConversationOption> barOptions = new ArrayList<>();
		barOptions.add(new ConversationOption(bar, new ArrayList<>(),"to Baz", new ArrayList<>(), baz));
		barOptions.add(new ConversationOption(bar, new ArrayList<>(),"quit", new ArrayList<>(), null));
		bar.setup(barOptions);
		List<ConversationOption> bazOptions = new ArrayList<>();
		bazOptions.add(new ConversationOption(foo, new ArrayList<>(),"back to Foo", new ArrayList<>(), foo));
		baz.setup(bazOptions);
		Map<String, ConversationNode> startMap = new HashMap<>();
		startMap.put("neutral", foo);
		conversationA.setup("neutral", startMap, nodes);
	}

	//@Test Not a real test, but often handy
	public void testPrintExport() {
		String text = ConversationWriter.exportConversation(conversationA);
		System.out.println(text);
	}

	@Test
	public void testExport() {
		String text = ConversationWriter.exportConversation(conversationA);
		Assert.assertTrue(JsonConversationA.equals(text));
	}
	
	@Test
	public void testImport() {
		Conversation conversation = ConversationReader.importConversation(JsonConversationA);
		List<ConversationNode> nodes = conversation.getConversationNodes();
		Assert.assertEquals(3, nodes.size());
		//TODO more testing
	}

	@Test(expected = ResourceLoadException.class)
	public void testInvalidImport() {
		Conversation conversation = ConversationReader.importConversation(JsonConversationB);
	}

	@Test(expected = ResourceLoadException.class)
	public void testMalformedJsonImport() {
		Conversation conversation = ConversationReader.importConversation(JsonConversationC);
	}

}
