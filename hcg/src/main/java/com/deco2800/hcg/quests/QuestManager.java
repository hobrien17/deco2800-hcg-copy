package com.deco2800.hcg.quests;

import com.deco2800.hcg.conversation.GiveItemsAction;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.Manager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.ResourceLoadException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Quest manager handles quest interaction in the game
 *
 * @author Harry Guthrie
 */
public class QuestManager extends Manager {
    HashMap<String,Quest> quests;

    HashMap<QuestNPC,QuestArchive> questLog;
    ArrayList<QuestArchive> completedLog;

    private PlayerManager playerManager = (PlayerManager) GameManager.get()
            .getManager(PlayerManager.class);

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

    public void addQuest(QuestNPC npc, String questName) throws ResourceLoadException {
        if (!quests.containsKey(questName)) {
            throw new ResourceLoadException("quests doesn't contain the key"+ questName + "quest hash map only contains values:"+ " "+ quests.values()); //todo write a proper exception
        }
        QuestArchive qa = new QuestArchive(quests.get(questName),npc);
        questLog.put(npc,qa);
    }

    public void completeQuest(QuestNPC npc) {
        if (!questLog.containsKey(npc)) {
            throw new ResourceLoadException(""); //todo write a proper exception
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

    public Boolean isQuestActive (QuestNPC npc, String questName) {
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
    public ArrayList<QuestArchive> getCompletedQuests() {
        return completedLog;
    }

    public HashMap<QuestNPC,QuestArchive> getCompleteableQuests() {
        HashMap<QuestNPC,QuestArchive> completeableLog = new HashMap<>();
        for (Map.Entry<QuestNPC,QuestArchive> entry: questLog.entrySet()) {
            if (canQuestBeCompleted(entry.getKey())) {
                completeableLog.put(entry.getKey(),entry.getValue());
            }
        }
        return completeableLog;
    }

    public HashMap<QuestNPC,QuestArchive> getUnCompleteableQuests() {
        HashMap<QuestNPC,QuestArchive> unCompleteableLog = new HashMap<>();
        for (Map.Entry<QuestNPC,QuestArchive> entry: questLog.entrySet()) {
            if (!canQuestBeCompleted(entry.getKey())) {
                unCompleteableLog.put(entry.getKey(),entry.getValue());
            }
        }
        return unCompleteableLog;
    }

    private boolean canQuestBeCompleted(QuestNPC npc) {
        return questLog.get(npc).currentlyCompletable();
    }

    /**
     *  ----------------------------- Functions for the initial load of quests -----------------------------
     */

    /**
     * Replaces currently loaded quests (if they exist) and replaces them with a new set of quests
     */
    public void loadAllQuests() {
        HashMap<String,Quest> tmpQ = new HashMap<>();
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
