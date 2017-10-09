package com.deco2800.hcg.actions.conversation_action_manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.deco2800.hcg.actions.Action;

public class ConversationActions {

    Map<String, Action> actions;
    
    public ConversationActions(){
        actions = new HashMap<String, Action>();
    }
    
    public ConversationActions(List<Entry<String,Action>> entries){
        actions = new HashMap<String, Action>();
        for(Entry<String, Action> entry : entries){
            String string = entry.getKey();
            Action action = entry.getValue();
            actions.put(string, action);
        }
    }
}
