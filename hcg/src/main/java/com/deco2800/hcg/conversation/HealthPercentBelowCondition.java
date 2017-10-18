package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

public class HealthPercentBelowCondition extends AbstractConversationCondition {

    boolean negate;
    int healthPercent;

    public HealthPercentBelowCondition(boolean negate, int healthPercent) {
        this.negate = negate;
        this.healthPercent = healthPercent;
    }

    @Override
    public boolean testCondition(QuestNPC talkingTo) {
        Player player = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
        int currentPercent = (100 * player.getHealthCur()) / player.getHealthMax();
        if (!negate) {
            return currentPercent < healthPercent;
        } else {
            return currentPercent >= healthPercent;
        }
    }

    @Override
    public String toString() {
        if (!negate) {
            return "healthPercentBelow|" + healthPercent;
        } else {
            return "!healthPercentBelow|" + healthPercent;
        }
    }

}
