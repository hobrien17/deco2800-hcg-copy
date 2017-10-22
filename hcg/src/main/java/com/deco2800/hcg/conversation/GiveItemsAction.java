package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;


public class GiveItemsAction extends AbstractConversationAction {

    private Integer itemsQuantity;
    private String itemName;

    public GiveItemsAction(String itemName, int itemQuantity) {
        this.itemName = itemName;
        this.itemsQuantity = itemQuantity;
    }

    @Override
    void executeAction(QuestNPC talkingTo) { //TODO item stacking

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
        for (int i = 0; i < itemsQuantity; i++) {
            Item newItem = itemManager.getNew(itemName);
            if (!playerInventory.addItem(newItem)) {
                world.addEntity(new ItemEntity(playerPosX, playerPosY, playerPosZ, newItem));
            }
        }
    }

    @Override
    public String toString() {
        String tS = "giveItems";

        return "giveItems" + '|' + itemName + '|' + itemsQuantity;
    }

}



