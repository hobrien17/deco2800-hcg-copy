package com.deco2800.hcg.worlds;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.enemy_entities.Hedgehog;
import com.deco2800.hcg.entities.enemy_entities.MushroomTurret;
import com.deco2800.hcg.entities.enemy_entities.Squirrel;
import com.deco2800.hcg.entities.terrain_entities.Tree;
import com.deco2800.hcg.entities.terrain_entities.WallBlock;

public enum WorldEntities {
    WALL, TREE, SQUIRREL, HEDGEHOG, MUSHROOMTURRET;
  
    /**
     * Return an instance of the entity at the given position and the selected index.
     * @param x - x position to place the entity
     * @param y - y position to place the entity
     * @param index - the index
     * @return the entity
     */
    AbstractEntity spawn(float x, float y, int index){
      switch(this){
        case WALL:
          return new WallBlock(x, y, 0f);            
        case TREE:
          return new Tree(x, y, 0f, true);
        case SQUIRREL:
          return new Squirrel(x, y, 0f, index);
        case HEDGEHOG:
          return new Hedgehog(x, y, 0f, index);
        case MUSHROOMTURRET:
            return new MushroomTurret(x, y, 0f, index);
        default:
        	return null;
      }
    }

}
