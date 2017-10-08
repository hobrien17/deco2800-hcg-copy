package com.deco2800.hcg.entities;

import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.renderers.CustomRenderable;
import com.deco2800.hcg.shading.LightEmitter;
import com.deco2800.hcg.util.WorldUtil;
import com.deco2800.hcg.worlds.World;

public class ItemEntity extends AbstractEntity implements Tickable, CustomRenderable, LightEmitter {

	protected Item item;

	public ItemEntity(float posX, float posY, float posZ, Item item) {
		super(posX, posY, posZ, 0.5F, 0.5F, 0.5F);
		this.item = item;
	}

	@Override
	public void onTick(long gameTickCount) {
		Optional<AbstractEntity> entity = WorldUtil.closestEntityToPosition(this.getPosX(), this.getPosY(), 
				1.2f, Player.class);
		if(entity.isPresent()) {
			Player player = (Player)entity.get();
			if(player.addItemToInventory(item)) {
				GameManager.get().getWorld().removeEntity(this);
			}
		}
		if(this.item.isStackable()) {
			List<AbstractEntity> otherItems = WorldUtil.allEntitiesToPosition(this.getPosX(), this.getPosY(), 
					1.5f, ItemEntity.class);
			for(AbstractEntity other : otherItems) {
				if (other != this) {
					ItemEntity otherItem = (ItemEntity)other;
				
					if(!this.item.sameItem(otherItem.getItem())) {
						continue;
					}
					
					if (otherItem.getItem()
							.addToStack(this.getItem().getStackSize())) {
						GameManager.get().getWorld().removeEntity(this);
						break;
					}
				}
			}
		}
		
        /*World world = GameManager.get().getWorld();
        for(AbstractEntity entity : world.getEntities()) {
            
            // Allow players to pick items up
			if (entity instanceof Player && this.distance(entity) <= 1.2
					& ((Player) entity).addItemToInventory(this.getItem())) {
				world.removeEntity(this);
				break;
			}

            // Check to see if we're colliding with any other item entities and try
            // and merge with them if we can
			if (this.item.isStackable() && entity instanceof ItemEntity
					&& this.distance(entity) <= 1.5 && entity != this) {
				ItemEntity otherItem = (ItemEntity) entity;

				if (!this.getItem().sameItem(otherItem.getItem())) {
					continue;
				}

				if (otherItem.getItem()
						.addToStack(this.getItem().getStackSize())) {
					world.removeEntity(this);
					break;
				}
			}
		}*/
	}

	@Override
	public String getTexture() {
		return this.item.getTexture();
	}

	/**
	 * Returns the item this entity is holding
	 * 
	 * @return This entity's item
	 */
	public Item getItem() {
		return this.item;
	}

    @Override
    public void customDraw(SpriteBatch batch, float posX, float posY, float tileWidth, float tileHeight, float aspect,
            TextureManager textureManager) {
        Texture texture = textureManager.getTexture(this.getItem().getTexture());
        Texture beam = textureManager.getTexture("loot_beam");
        
        if(this.item.getRarity() != ItemRarity.COMMON) {
            Color precolour = batch.getColor();
            Color colour = this.getItem().getRarity().colour;
            batch.setColor(colour);
    
            batch.draw(beam, posX, posY, tileWidth * this.getXRenderLength(),
                    (beam.getHeight() / aspect) * this.getYRenderLength());
            
            batch.setColor(precolour);
        }
        
        batch.draw(texture, posX, posY, tileWidth * this.getXRenderLength(),
                (texture.getHeight() / aspect) * this.getYRenderLength());
    }

    @Override
    public Color getLightColour() {
        return this.getItem().getRarity().colour;
    }

    @Override
    public float getLightPower() {
        return this.getItem().getRarity() == ItemRarity.COMMON ? 0 : 5;
    }
}
