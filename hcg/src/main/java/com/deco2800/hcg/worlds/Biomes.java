package com.deco2800.hcg.worlds;

import com.badlogic.gdx.graphics.Texture;

public enum Biomes {
  GRASS;

  Texture getTexture(TileTypes type){
    switch (this){
      case GRASS:
        switch (type){
          case GROUND:
            return new Texture("resources/maps/environment/grass.png");
          case PATH:
            return new Texture("resources/maps/environment/spikes.png");
        }
    }
    return null;
  }
  
}
