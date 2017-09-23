package com.deco2800.hcg.managers;

import com.deco2800.hcg.BaseTest;
import org.junit.Test;

public class ConversationManagerTests extends BaseTest {

    // The most basic test imaginable
    @Test
    public void testConstructor() {
        ConversationManager conversationManager = new ConversationManager();
        System.out.println(conversationManager.getConversationNames());
    }

}
