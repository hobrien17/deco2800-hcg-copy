package com.deco2800.hcg.quests;

import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;
import java.util.HashMap;
import java.util.Map;

public class QuestArchive {
    private Quest quest;
    private static HashMap<Integer,HashMap<Integer, Integer>> initalKillLog;
    private NPC questGiver;

    private Boolean killReqCompleted = false;

    private GameManager gameManager = GameManager.get();
    private PlayerManager playerManager = (PlayerManager) GameManager.get()
            .getManager(PlayerManager.class);

    public QuestArchive(Quest quest,NPC questGiver) {
        this.quest = quest;
        this.questGiver = questGiver;

        this.initalKillLog = new HashMap<>();
        //Do we need to store a kill log?
        if (quest.getKillRequirement().size() != 0) {
            //Get the current kills for the different enemy IDs in nodes
            for (Integer node: quest.getKillRequirement().keySet()) {
                HashMap<Integer,Integer> enemyIDKills = new HashMap<>();
                for (Integer enemyID: quest.getKillRequirement().get(node).keySet()) {
                    enemyIDKills.put(enemyID,playerManager.getPlayer().killLogGet(node,enemyID));
                }
                this.initalKillLog.put(node,enemyIDKills);
            }
        }
    }

    public Quest getQuest() {
        return this.quest;
    }

    public void completeQuest() {
        //Give rewards
        // This needs to reference a frightening number of different systems
        GameManager gameManager = GameManager.get();
        World world = gameManager.getWorld();
        ItemManager itemManager = (ItemManager) gameManager.getManager(ItemManager.class);
        Player player = ((PlayerManager) gameManager.getManager(PlayerManager.class)).getPlayer();
        Inventory playerInventory = player.getInventory();
        float playerPosX = player.getPosX();
        float playerPosY = player.getPosY();
        float playerPosZ = player.getPosZ();

        // Create each new item, and either place it in the player inventory or
        // (if it doesn't fit) onto the ground
        for (Map.Entry entry: quest.getRewards().entrySet()) {
            for (int i = 0; i < (int)entry.getValue(); i++) {
                Item newItem = itemManager.getNew((String)entry.getKey());
                if (!playerInventory.addItem(newItem)) {
                    world.addEntity(new ItemEntity(playerPosX, playerPosY, playerPosZ, newItem));
                }
            }
        }
    }

    public NPC getQuestGiver() {
        return questGiver;
    }

    public Boolean currentlyCompletable() {
        return (getKillReqCompleted() && getItemReqCompleted());
    }

    private Boolean getKillReqCompleted() {
        //Check if this has being completed yet, if not then check it
        if (!this.killReqCompleted) {
            //Check if we have met the kill requirements
            for (Integer node: quest.getKillRequirement().keySet()) {
                //Make sure the current kill log contains the same nodes as the quest
                if (!playerManager.getPlayer().killLogContainsNode(node)) {
                    return false;
                }
                for (Integer enemyID: quest.getKillRequirement().get(node).keySet()) {
                    //Make sure the current kill log in the node contains the enemy ID
                    if (!playerManager.getPlayer().killLogContains(enemyID,node)) {
                        return false;
                    }
                    // Make sure the person has got the current difference in kills
                    if ((playerManager.getPlayer().killLogGet(enemyID,node) - initalKillLog.get(node).get(enemyID))
                            < quest.getKillRequirement().get(node).get(enemyID)) {
                        return false;
                    }
                }
            }
            this.killReqCompleted = true;
        }
        return true;
    }

    private Boolean getItemReqCompleted() {
        //Check if the inventory currently has enough items
        Inventory inv = playerManager.getPlayer().getInventory();

        //Todo check if the inventory has the items


        return true;
    }

    public String getQuestTitle() {
        return quest.getTitle();
    }

    public void update() {

    }






}
