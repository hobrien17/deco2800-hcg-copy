package com.deco2800.hcg.entities;

import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.renderers.CustomRenderable;
import com.deco2800.hcg.shading.LightEmitter;
import com.deco2800.hcg.util.WorldUtil;

public class ItemEntity extends AbstractEntity implements Tickable, CustomRenderable, LightEmitter {

	protected Item item;
	
	public ItemEntity(float posX, float posY, float posZ, Item item) {
		super(posX, posY, posZ, 0.5F, 0.5F, 0.5F);
		this.item = item;
	}

	@Override
	public void onTick(long gameTickCount) {
		Optional<AbstractEntity> entity = WorldUtil.closestEntityToPosition(this.getPosX(), this.getPosY(), 
				1f, Player.class);
		if(entity.isPresent()) {
			Player player = (Player)entity.get();
			if(player.addItemToInventory(item)) {
				GameManager.get().getWorld().removeEntity(this);
				((SoundManager)GameManager.get().getManager(SoundManager.class)).playSound(
				        String.format("loot%d", MathUtils.random(1, 2)));
			}
		}
		if (!this.item.isStackable()) {
			return;
		}
		List<AbstractEntity> otherItems = WorldUtil.allEntitiesToPosition(
				this.getPosX(), this.getPosY(), 1.5f, ItemEntity.class);
		for (AbstractEntity other : otherItems) {
			if (other == this) {
				continue;
			}
			
			ItemEntity otherItem = (ItemEntity) other;

			if (!this.item.sameItem(otherItem.getItem())) {
				continue;
			}

			if (otherItem.getItem().addToStack(this.getItem().getStackSize())) {
				GameManager.get().getWorld().removeEntity(this);
				break;
			}
		}
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
        
        float width = tileWidth * this.getXRenderLength();
        float height = (texture.getHeight() / aspect) * this.getYRenderLength();
        
        if(this.item.getRarity() != ItemRarity.COMMON) {
            Color precolour = batch.getColor();
            Color colour = this.getItem().getRarity().colour;
            batch.setColor(colour);
    
            batch.draw(beam, posX, posY, tileWidth / 2, beam.getHeight() / 3);
            
            batch.setColor(precolour);
        }
        
        batch.draw(texture, posX, posY, width, height);
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
