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
    HashMap<QuestNPC,Quest> questLog;
    HashMap<QuestNPC,ArrayList<Quest>> completedLog;

    private PlayerManager playerManager = (PlayerManager) GameManager.get()
            .getManager(PlayerManager.class);

    /**
     *  Creates a new quest manager
     */
    public QuestManager() {
        quests = new HashMap<>();
        questLog = new HashMap<>();
        completedLog = new HashMap<>();
        loadAllQuests();
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
        return questLog.getOrDefault(npc, null);
    }

    public void addQuest(QuestNPC npc, String questName) throws ResourceLoadException {
        if (!quests.containsKey(questName)) {
            throw new ResourceLoadException(""); //todo write a proper exception
        }
        questLog.put(npc,quests.get(questName));
    }

    public void completeQuest(QuestNPC npc) {
        if (!questLog.containsKey(npc)) {
            throw new ResourceLoadException(""); //todo write a proper exception
        }
        //Give the player the reward
        GiveItemsAction gia = new GiveItemsAction(questLog.get(npc).getRewards());

        //Set the quest to complete
        completedLog.putIfAbsent(npc,new ArrayList<>());
        completedLog.get(npc).add(questLog.get(npc));
        questLog.remove(npc);
    }


    /**
     * ------------------------------------- Functions for the quest UI -------------------------------------
     */
    public HashMap<QuestNPC,ArrayList<Quest>> getCompletedQuests() {
        return completedLog;
    }

    public HashMap<QuestNPC,Quest> getCompleteableQuests() {
        HashMap<QuestNPC,Quest> completeableLog = new HashMap<>();
        for (Map.Entry<QuestNPC,Quest> entry: questLog.entrySet()) {
            if (canQuestBeCompleted(entry.getValue())) {
                completeableLog.put(entry.getKey(),entry.getValue());
            }
        }
        return completeableLog;
    }

    public HashMap<QuestNPC,Quest> getUnCompleteableQuests() {
        HashMap<QuestNPC,Quest> unCompleteableLog = new HashMap<>();
        for (Map.Entry<QuestNPC,Quest> entry: questLog.entrySet()) {
            if (!canQuestBeCompleted(entry.getKey())) {
                unCompleteableLog.put(entry.getKey(),entry.getValue());
            }
        }
        return unCompleteableLog;
    }

    private boolean canQuestBeCompleted(QuestNPC npc) {
        //Get the quest
        Quest q = questLog.get(npc);

        //This can be done by storing the kill log when the quest is first activated to check the difference
        //TODO check the kill log requirement


        //Todo check the inventory requirement
        int counter = 0; //To keep track of amount of each item
        for (Map.Entry entry: q.getItemRequirement().entrySet()) {
            Inventory inv = playerManager.getPlayer().getInventory();

        }





        return true;
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
