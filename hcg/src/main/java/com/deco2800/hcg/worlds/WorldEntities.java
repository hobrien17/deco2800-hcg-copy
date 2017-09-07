package com.deco2800.hcg.worlds;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.enemy_entities.Squirrel;
import com.deco2800.hcg.entities.terrain_entities.Tree;
import com.deco2800.hcg.entities.terrain_entities.WallBlock;

public enum WorldEntities {
    WALL, TREE, SQUIRREL;
  
    // return an instance of the entity at the given position and the selected index.
    AbstractEntity Spawn(float x, float y, int index){
      switch(this){
        case WALL:
          return new WallBlock(x, y, 0f);            
        case TREE:
          return new Tree(x, y, 0f, true);
        case SQUIRREL:
          return new Squirrel(x, y, 0f, index);
      }
      return null;
      
    }

}
