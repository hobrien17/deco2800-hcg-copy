package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Conversation System
 * @author Richy McGregor
 */
public class ConversationTest {

	// JSON test data & associated Conversation
	private String JsonConversationA = "{\"initialNode\":\"0\",\"nodes\":[{\"id\":\"0\",\"nodeText\":\"Foo\",\"options\":[{\"optionText\":\"to Bar\",\"target\":\"1\"},{\"optionText\":\"to Baz\",\"target\":\"2\"}]},{\"id\":\"1\",\"nodeText\":\"Bar\",\"options\":[{\"optionText\":\"to Baz\",\"target\":\"2\"},{\"optionText\":\"quit\",\"target\":null}]},{\"id\":\"2\",\"nodeText\":\"Baz\",\"options\":[{\"optionText\":\"back to Foo\",\"target\":\"0\"}]}]}";
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
		fooOptions.add(new ConversationOption(foo, "to Bar", bar, new ArrayList<>()));
		fooOptions.add(new ConversationOption(foo, "to Baz", baz, new ArrayList<>()));
		foo.setup(fooOptions);
		List<ConversationOption> barOptions = new ArrayList<>();
		barOptions.add(new ConversationOption(bar, "to Baz", baz, new ArrayList<>()));
		barOptions.add(new ConversationOption(bar, "quit", null, new ArrayList<>()));
		bar.setup(barOptions);
		List<ConversationOption> bazOptions = new ArrayList<>();
		bazOptions.add(new ConversationOption(foo, "back to Foo", foo, new ArrayList<>()));
		baz.setup(bazOptions);
		conversationA.setup(nodes, foo);
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

}
