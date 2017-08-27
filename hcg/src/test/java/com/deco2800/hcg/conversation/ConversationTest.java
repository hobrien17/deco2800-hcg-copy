package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ConversationTest {
	private Conversation conversation;
	
	@Before
	public void initialiseConversation(){
		List<String> testConversation = new ArrayList<String>();
		testConversation.add("A");
		testConversation.add("B");
		testConversation.add("C");
		testConversation.add("D");
		
		String greeting = "Hello welcome";
		String goodbye = "Okay, goodbye";
		
		Conversation testConvo = new Conversation(greeting, goodbye, testConversation);
		this.conversation = testConvo;
	}
	
	@Test
	public void testConversation(){
		assert(conversation.nextSentence(true).equals("A"));
		assert(conversation.nextSentence(true).equals("B"));
		assert(conversation.nextSentence(true).equals("C"));
		assert(conversation.nextSentence(true).equals("D"));
		assert(conversation.nextSentence(true).equals("Okay, goodbye"));
		assert(conversation.nextSentence(true).equals("A"));
	
	}
	
	

}
