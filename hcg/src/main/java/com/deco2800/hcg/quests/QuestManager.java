package com.deco2800.hcg.quests;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.managers.Manager;
import com.deco2800.hcg.managers.ResourceLoadException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Quest manager handles quest interaction in the game
 *
 * @author Harry Guthrie
 */
public class QuestManager extends Manager {
    Map<String,Quest> quests;

    Map<QuestNPC,QuestArchive> questLog;
    List<QuestArchive> completedLog;

    /**
     *  Creates a new quest manager
     */
    public QuestManager() {
        quests = new HashMap<>();
        questLog = new HashMap<>();
        completedLog = new ArrayList<>();
    }

    /**
     * Returns the loaded Quest associated with the Quest Name
     *
     * @param questName - name of the quest in the .json file
     * @return the Quest object if it exists if not null
     */
    public Quest getQuest(String questName) {
        return quests.getOrDefault(questName, null);
    }

    public Quest getActiveNPCQuest(QuestNPC npc) {
        if (questLog.containsKey(npc)) {
            return questLog.get(npc).getQuest();
        }
        return null;
    }

    public void addQuest(QuestNPC npc, String questName) {
        if (!quests.containsKey(questName)) {
            throw new ResourceLoadException("quests doesn't contain the key " + questName + " quest hash map only contains values:"+ " "+ quests.keySet());
        }
        QuestArchive qa = new QuestArchive(quests.get(questName),npc);
        questLog.put(npc,qa);
    }

    public void completeQuest(QuestNPC npc) {
        if (!questLog.containsKey(npc)) {
            throw new ResourceLoadException("The Quest log does not contain a quest for the npc (" +
                    npc.getFirstName() + " " + npc.getSurname() + ")");
        }
        //Complete the quest - note does not check if completable
        questLog.get(npc).completeQuest();
        completedLog.add(questLog.get(npc));
        questLog.remove(npc);
    }

    //Bools for use during conversation
    public Boolean isQuestNotStarted (QuestNPC npc, String questName) {
        if (questLog.containsKey(npc)) {
            return true;
        } else {
            for (QuestArchive qa: completedLog) {
                if (qa.getQuestGiver().equals(npc) && qa.getQuest().getTitle().equals(questName)) {
                    return false;
                }
            }
            return true;
        }
    }

    public Boolean isQuestActive (QuestNPC npc) {
        return (questLog.containsKey(npc));
    }
    public Boolean isQuestCompleted (QuestNPC npc, String questName)  {
        for (QuestArchive qa: completedLog) {
            if (qa.getQuestGiver().equals(npc) && qa.getQuest().getTitle().equals(questName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * ------------------------------------- Functions for the quest UI -------------------------------------
     */
    public List<QuestArchive> getCompletedQuests() {
        return completedLog;
    }

    public Map<QuestNPC,QuestArchive> getCompleteableQuests() {
        HashMap<QuestNPC,QuestArchive> completeableLog = new HashMap<>();
        for (Map.Entry<QuestNPC,QuestArchive> entry: questLog.entrySet()) {
            if (canQuestBeCompleted(entry.getKey())) {
                completeableLog.put(entry.getKey(),entry.getValue());
            }
        }
        return completeableLog;
    }

    public Map<QuestNPC,QuestArchive> getUnCompleteableQuests() {
        HashMap<QuestNPC,QuestArchive> unCompleteableLog = new HashMap<>();
        for (Map.Entry<QuestNPC,QuestArchive> entry: questLog.entrySet()) {
            if (!canQuestBeCompleted(entry.getKey())) {
                unCompleteableLog.put(entry.getKey(),entry.getValue());
            }
        }
        return unCompleteableLog;
    }

    public boolean canQuestBeCompleted(QuestNPC npc) {
        return questLog.get(npc).currentlyCompletable();
    }

    /**
     *  ----------------------------- Functions for the initial load of quests -----------------------------
     */

    /**
     * Replaces currently loaded quests (if they exist) and replaces them with a new set of quests
     */
    public void loadAllQuests() {
        QuestReader qr = new QuestReader();
        quests = qr.loadAllQuests();
    }

    /**
     * Loads a single quest in as specified from the fp (filepath argument)
     *
     * @param fp - the full path of the json file of the quest
     */
    public void loadQuest(String fp) {
        QuestReader qr = new QuestReader();
        Quest tmpQ = qr.loadQuest(fp);
        if (quests.containsKey(tmpQ.getTitle())) {
            throw new ResourceLoadException("Quest (" + tmpQ.getTitle() + ") already added from fp (" + fp + ")");
        }
    }

}
