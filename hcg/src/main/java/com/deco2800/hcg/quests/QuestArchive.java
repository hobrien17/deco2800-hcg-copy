package com.deco2800.hcg.quests;

import com.deco2800.hcg.entities.npc_entities.NPC;

public class QuestArchive {
    private Quest quest;
    private NPC questGiver;
    private Boolean completed = false;
    private Boolean handedIn = false;

    public QuestArchive(Quest quest,NPC questGiver) {
        this.quest = quest;
        this.questGiver = questGiver;
    }

    public String getQuestGiverName() {
        return (questGiver.getFirstName() + " " + questGiver.getSurname());
    }


    public Boolean getCompleted() {
        return completed;
    }

    public Boolean getHandedIn() {
        return handedIn;
    }

    public String getQuestTitle() {
        return quest.getTitle();
    }

    public void update() {

    }






}
