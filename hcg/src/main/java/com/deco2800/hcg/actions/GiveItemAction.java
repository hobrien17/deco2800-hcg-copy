package com.deco2800.hcg.actions;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

public class GiveItemAction implements Action {
    private Item item;
    private PlayerManager playerManager;
    
    public GiveItemAction(Item item){
        this.item = item;
        this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
    }
    
    private void givePlayerItem(){
        if(playerManager.getPlayer().getInventory().canInsert(item)){
            playerManager.getPlayer().getInventory().addItem(item);
        }
    }

    @Override
    public void executeAction() {
        givePlayerItem();
    }

}
