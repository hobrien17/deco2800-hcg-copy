package com.deco2800.hcg.quests;

import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.enemyentities.EnemyType;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;

import java.util.EnumMap;
import java.util.Map;

public class QuestArchive {
    private Quest quest;
    private EnumMap<EnemyType, Integer> initalKillLog;
    private NPC questGiver;

    private Boolean killReqCompleted = false;

    private GameManager gameManager = GameManager.get();
    private PlayerManager playerManager = (PlayerManager) GameManager.get()
            .getManager(PlayerManager.class);

    public QuestArchive(Quest quest,NPC questGiver) {
        this.quest = quest;
        this.questGiver = questGiver;

        Player player = ((PlayerManager) gameManager.getManager(PlayerManager.class)).getPlayer();

        this.initalKillLog = new EnumMap<>(EnemyType.class);
        //Store the initial kill log plus the current amount of kills
        if (quest.getKillRequirement().size() != 0) {
            //Get the current kills for the different enemy IDs in nodes
            for (Map.Entry<EnemyType,Integer> kvp: quest.getKillRequirement().entrySet()) {
                //Get the initial kill log value
                this.initalKillLog.put(kvp.getKey(),player.killLogGet(kvp.getKey()));
            }
        }
    }

    public Quest getQuest() {
        return this.quest;
    }

    public void completeQuest() {
        //Give rewards
        // This needs to reference a frightening number of different systems
        gameManager = GameManager.get();
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

        //Remove required items if needed
        if (quest.getItemRequirement().size() > 0) {
            for (Map.Entry<String,Integer> itemReq: quest.getItemRequirement().entrySet()) {
                playerInventory.removeItem(itemReq.getKey().replace("_", " "),itemReq.getValue());
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
            for (EnemyType enemyID: quest.getKillRequirement().keySet()) {
                //Make sure the current kill log in the node contains the enemy ID
                if (!playerManager.getPlayer().killLogContains(enemyID)) {
                    return false;
                }
                if (playerManager.getPlayer().killLogGet(enemyID) <
                        initalKillLog.get(enemyID) + quest.getKillRequirement().get(enemyID)) {
                    return false;
                }
            }
            this.killReqCompleted = true;
        }
        return true;
    }

    private Boolean getItemReqCompleted() {
        Player player = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
        for(String item: quest.getItemRequirement().keySet()){
            if (player.getInventory().numberOf(item.replace("_"," ")) <
                    quest.getItemRequirement().get(item)) {
                return false;
            }
        }
        return true;
    }

    public String getQuestTitle() {
        return quest.getTitle();
    }


    public Map<EnemyType, Integer> getInitalKillLog() {
        return initalKillLog;
    }
}
