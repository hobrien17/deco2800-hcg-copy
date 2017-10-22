package com.deco2800.hcg.worlds;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.enemyentities.Hedgehog;
import com.deco2800.hcg.entities.enemyentities.MushroomTurret;
import com.deco2800.hcg.entities.enemyentities.Snail;
import com.deco2800.hcg.entities.enemyentities.Squirrel;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.terrain_entities.Barbeque;
import com.deco2800.hcg.entities.terrain_entities.Boulder;
import com.deco2800.hcg.entities.terrain_entities.ConcreteWall;
import com.deco2800.hcg.entities.terrain_entities.HouseWORoof;
import com.deco2800.hcg.entities.terrain_entities.HouseWRoof;
import com.deco2800.hcg.entities.terrain_entities.IceBoulder;
import com.deco2800.hcg.entities.terrain_entities.Invisible;
import com.deco2800.hcg.entities.terrain_entities.LargeTree;
import com.deco2800.hcg.entities.terrain_entities.MushroomTreeThick;
import com.deco2800.hcg.entities.terrain_entities.MushroomTreeThin;
import com.deco2800.hcg.entities.terrain_entities.PicketFenceEW;
import com.deco2800.hcg.entities.terrain_entities.PicketFenceNS;
import com.deco2800.hcg.entities.terrain_entities.Rock;
import com.deco2800.hcg.entities.terrain_entities.SludgeBarrel;
import com.deco2800.hcg.entities.terrain_entities.Swing;
import com.deco2800.hcg.entities.terrain_entities.Tree;
import com.deco2800.hcg.entities.terrain_entities.TreeType;
import com.deco2800.hcg.entities.terrain_entities.WallBlock;
import com.deco2800.hcg.entities.terrain_entities.WarningSign;
import com.deco2800.hcg.items.stackable.HealthPotion;

public enum WorldEntities {

    WALL, TREE, SQUIRREL, HEDGEHOG, MUSHROOMTURRET, POT, UPOT, CORPSE, ICETREE, BOULDER, ROCK, ICEBOULDER, HOUSE, SLUDGEBARREL, MUSHROOMTREETHICK, MUSHROOMTREETHIN,
    GREENTREE, LARGETREE, SWING, HOUSEWITHROOF, HOUSEWITHOUTROOF, PICKETFENCEEW, PICKETFENCENS, CONCRETEWALL, ITEM, SNAIL, LEAFLESSTREE, INVISIBLE, BARBEQUE,
    WARNINGSIGN;

    /**
     * Return an instance of the entity at the given position and the selected
     * index.
     *
     * @param x - x position to place the entity
     * @param y - y position to place the entity
     * @param index - the index
     * @return the entity
     */
    AbstractEntity spawn(float x, float y, int id) {
        switch (this) {
            case WALL:
                return new WallBlock(x, y, 0f);
            case TREE:
                return new Tree(x, y, 0f, true);
            case SNAIL:
                return new Snail(x, y, 0f, id);
            case SQUIRREL:
                return new Squirrel(x, y, 0f, id);
            case HEDGEHOG:
                return new Hedgehog(x, y, 0f, id);
            case POT:
                return new Pot(x, y, 0f);
            case UPOT:
            	Pot pot = new Pot(x, y, 0f);
            	pot.unlock();
            	return pot;
            case MUSHROOMTURRET:
                return new MushroomTurret(x, y, 0f, id);
            case CORPSE:
                return new BasicCorpse(x, y, 0f);
            case ICETREE:
                return new Tree(x, y, 0f, TreeType.SNOWY);
            case LEAFLESSTREE:
              return new Tree(x, y, 0f, TreeType.LEAFLESS);
            case BOULDER:
                return new Boulder(x, y, 0f);
            case ROCK:
                return new Rock(x, y, 0f);
            case ICEBOULDER:
                return new IceBoulder(x, y, 0f);
            case SLUDGEBARREL:
                return new SludgeBarrel(x, y, 0f);
            case MUSHROOMTREETHICK:
                return new MushroomTreeThick(x, y, 0f);
            case MUSHROOMTREETHIN:
                return new MushroomTreeThin(x, y, 0f);
            case GREENTREE:
                return new Tree(x, y, 0f, TreeType.LEAFY);
            case LARGETREE:
                return new LargeTree(x, y, 0f);
            case SWING:
                return new Swing(x, y, 0f);
            case HOUSEWITHROOF:
                return new HouseWRoof(x, y, 0f);
            case HOUSEWITHOUTROOF:
                return new HouseWORoof(x, y, 0f);
            case PICKETFENCEEW:
                return new PicketFenceEW(x, y, 0f);
            case PICKETFENCENS:
                return new PicketFenceNS(x, y, 0f);
            case CONCRETEWALL:
                return new ConcreteWall(x, y, 0f);
            case ITEM:
            	return new ItemEntity(x, y, 0f, new HealthPotion(10));
            case INVISIBLE:
                return new Invisible(x, y, 0f);
            case BARBEQUE:
                return new Barbeque(x, y, 0f);
            case WARNINGSIGN:
                return new WarningSign(x, y, 0f);
            default:
                return null;
        }
    }
}