package conversation;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
	
	private List<String> conversation;
	private String greeting;
	private String goodbye;
	private boolean active; // condition to check whether conversation is active
	private int iterator;
	
	public Conversation(String greeting, String goodbye, List<String>conversation){
		conversation = new ArrayList<String>();
		this.conversation.addAll(conversation);
		active = false;
		iterator = 0;
		this.greeting = greeting;
	}
	
	public boolean conversationActive(){
		return this.active;
	}
	
	public String greet(){
		return this.greeting;
	}
	
	public String nextSentence(boolean answer){
		if(answer == false){
			active = false;
			return goodbye;
		}
		if(iterator == conversation.size()-1){
			active= false;
			iterator = -1;
			return goodbye;
		}
		iterator++;
		return conversation.get(iterator);
	}
	
}
