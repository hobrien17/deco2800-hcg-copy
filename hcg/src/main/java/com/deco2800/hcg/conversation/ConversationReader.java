package com.deco2800.hcg.conversation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ConversationReader {
	//May be incorporated with the builder later possibly, not sure yet
	
	private final static String EMPTY_LINE = "";
	
	static List<ConversationNode> readConversation(String fileName) throws FileNotFoundException{
		//TODO
		Scanner scanner = new Scanner(new FileReader(fileName));
		
		AtomicInteger lineNumber = new AtomicInteger();
		lineNumber.set(0);
		return null;
		
	}
}
