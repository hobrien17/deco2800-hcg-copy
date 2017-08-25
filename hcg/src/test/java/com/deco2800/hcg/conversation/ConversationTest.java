package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConversationTest {
	
	@Test
	public void testConversation(){
		List<String> testConversation = new ArrayList<String>();
		testConversation.add("A");
		testConversation.add("B");
		testConversation.add("C");
		testConversation.add("D");
		
		String greeting = "Hello welcome";
		String goodbye = "Okay, goodbye";
		
		Conversation testConvo = new Conversation(greeting, goodbye, testConversation);
		
		assert(testConvo.nextSentence(true).equals("A"));
		assert(testConvo.nextSentence(true).equals("B"));
		assert(testConvo.nextSentence(true).equals("C"));
		assert(testConvo.nextSentence(true).equals("D"));
		assert(testConvo.nextSentence(true).equals("Okay, goodbye"));
		assert(testConvo.nextSentence(true).equals("A"));
	
	}
	
	

}
