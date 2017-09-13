package com.deco2800.hcg.worlds;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;

public enum NPCs {
  SHOP, QUEST;
  
  /**
   * Return an instance of the NPC at the given position, with the given fname and sname
   * and texture.
   * @param x - x position to place the entity
   * @param y - y position to place the entity
   * @param fName - first name
   * @param sName - last name
   * @param texture - texture string
   * @return the NPC
   */
  AbstractEntity spawn(float x, float y, String fName, String sName, String texture){
    switch(this){
      case SHOP:
        return new ShopNPC(x, y, fName, sName, texture);     
      case QUEST:
        return new QuestNPC(x, y, fName, sName, texture);     
      default:
          return null;
    }
  }

}
