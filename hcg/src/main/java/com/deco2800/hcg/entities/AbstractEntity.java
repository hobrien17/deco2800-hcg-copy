package com.deco2800.hcg.entities;


import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.worlds.World;

/**
 * A AbstractEntity is an item that can exist in both 3D and 2D worlds
 * AbstractEntities are rendered by Render2D and Render3D An item that does not
 * need to be rendered should not be a WorldEntity
 */
public abstract class AbstractEntity implements Renderable,
        Comparable<AbstractEntity> {

    private Box3D position;

    private float xRenderLength;

    private float yRenderLength;

    private boolean centered;

    private static float floatEpsilon = 0.0001f;

    private String texture = "error_box";
    
    private Color tint = null;

    /**
     * Creates a new AbstractEntity at the given position with the given size
     * parameters.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     * @param xLength the length of the entity in terms of the x-axis
     * @param yLength the length of the entity in terms of the y-axis
     * @param zLength the height of the entity
     */
    public AbstractEntity(float posX, float posY, float posZ, float xLength,
            float yLength,
            float zLength) {
        this(posX, posY, posZ, xLength, yLength, zLength, xLength, yLength,
                false);
    }

    /**
     * Creates a new AbstractEntity at the given position with the given size
     * and rendering parameters.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     * @param xLength the length of the entity in terms of the x-axis
     * @param yLength the length of the entity in terms of the y-axis
     * @param zLength the height of the entity
     * @param xRenderLength the render length of the entity in the x-axis
     * @param yRenderLength the render length of the entity in the y-azis
     * @param centered specifies if the entity is centered at the position or
     * not
     */
    public AbstractEntity(float posX, float posY, float posZ, float xLength,
            float yLength,
            float zLength,
            float xRenderLength, float yRenderLength, boolean centered) {
        this.xRenderLength = xRenderLength;
        this.yRenderLength = yRenderLength;
        this.centered = centered;
        float newX = posX;
        float newY = posY;

        if (centered) {
            newX += (1 - xLength / 2);
            newY += (1 - yLength / 2);
        }
        this.position = new Box3D(newX, newY, posZ, xLength, yLength, zLength);
    }

    /**
     * Creates a new AbstractEntity at the given position with the given
     * rendering parameters.
     * @param position the 3D position of the entity
     * @param xRenderLength the render length of the entity in the x-axis
     * @param yRenderLength the render length of the entity in the y-axis
     * @param centered specifies if the entity if centered at the position or
     * not
     */
    public AbstractEntity(Box3D position, float xRenderLength,
            float yRenderLength,
            boolean centered) {
        this.position = new Box3D(position);
        this.xRenderLength = xRenderLength;
        this.yRenderLength = yRenderLength;
        this.centered = centered;
    }

    /**
     * Get the X position of this AbstractWorld Entity
     *
     * @return The X position
     */
    public float getPosX() {
        float x = position.getX();
        if (this.centered) {
            x -= (1 - this.position.getYLength() / 2);
        }
        return x;
    }

    /**
     * Get the Y position of this AbstractWorld Entity
     *
     * @return The Y position
     */
    public float getPosY() {
        float y = position.getY();
        if (this.centered) {
            y -= (1 - this.position.getYLength() / 2);
        }
        return y;
    }

    /**
     * Get the Z position of this AbstractWorld Entity
     *
     * @return The Z position
     */
    public float getPosZ() {
        return position.getZ();
    }

    /**
     * Sets the position of the entity
     * @param x the x position
     * @param y the y position
     * @param z the z position
     */
    public void setPosition(float x, float y, float z) {
      float newY = y;
      float newX = x;
        if (this.centered) {
            newY += 1 - this.position.getYLength() / 2;
            newX += 1 - this.position.getXLength() / 2;
        }
        this.position.setX(newX);
        this.position.setY(newY);
        this.position.setZ(z);
    }

    /**
     * Sets the x position of the entity.
     * @param x the position to set the current x position to
     */
    public void setPosX(float x) {
        float newX = x;
        if (this.centered) {
          newX += (1 - this.position.getXLength() / 2);
        }
        this.position.setX(newX);
    }

    /**
     * Sets the y position of the entity.
     * @param y the position to set the current y position to
     */
    public void setPosY(float y) {
        float newY = y;
        if (this.centered) {
          newY += (1 - this.position.getYLength() / 2);
        }
        this.position.setY(newY);
    }

    /**
     * Sets the z position of the entity.
     * @param z the position to set the current z position to
     */
    public void setPosZ(float z) {
        this.position.setZ(z);
    }

    /**
     * Get the height value of this item. In 3D worlds this is the stack index.
     * In 2D worlds this is the height of an object
     *
     * @return height
     */
    public float getZLength() {
        return position.getZLength();
    }

    /**
     * Returns the XLength of the entity
     *
     * @return the XLength of the entity
     */
    public float getXLength() {
        return position.getXLength();
    }

    /**
     * Returns the YLength of the entity
     *
     * @return the YLength of the entity
     */
    public float getYLength() {
        return position.getYLength();
    }

    /**
     * Checks if this entity is colliding with the given entity.
     *
     * @param entity the entity to check collisions with
     * @return true if this entity is colliding with the given entity
     */
    public boolean collidesWith(AbstractEntity entity) {
        return this.position.overlaps(entity.position);
    }

    @Override
    public float getXRenderLength() {
        return this.xRenderLength;
    }

    @Override
    public float getYRenderLength() {
        return this.yRenderLength;
    }
    
    /**
     * Grows the size of the rendered sprite by xAmount and yAmount
     * 
     * @param xAmount
     * 			the amount to grow in the x-direction
     * @param yAmount
     * 			the amount to grow in the y-direction
     */
    public void growRender(float xAmount, float yAmount) {
    	xRenderLength += xAmount;
    	yRenderLength += yAmount;
    }

    /**
     * Returns a Box3D representing the location.
     *
     * @return Returns a Box3D representing the location.
     */
    public Box3D getBox3D() {
        return new Box3D(position);
    }

    /**
     * Gives the string for the texture of this entity. This does not mean the
     * texture is currently registered
     *
     * @return texture string
     */
    public String getTexture() {
        return texture;
    }

    /**
     * Sets the texture string for this entity. Check the texture is registered
     * with the TextureRegister
     *
     * @param texture String texture id
     */
    public void setTexture(String texture) {
        this.texture = texture;
    }

    /**
     * Allows sorting of WorldEntities for Isometric rendering
     */
    @Override
    public int compareTo(AbstractEntity o) {
        float cartX = this.position.getX();
        float cartY = this.getParent().getLength() - this.position.getY();

        float isoX = (cartX - cartY) / 2.0f;
        float isoY = (cartX + cartY) / 2.0f;

        float cartXo = o.getPosX();
        float cartYo = o.getParent().getLength() - o.getPosY();

        float isoXo = (cartXo - cartYo) / 2.0f;
        float isoYo = (cartXo + cartYo) / 2.0f;
        

        if (Math.abs(isoY - isoYo) <= floatEpsilon) {
            if (isoX < isoXo) {
                return 1;
            } else if (isoX > isoXo) {
                return -1;
            } else {
                return 0;
            }
        } else if (isoY < isoYo) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) o;

        if (position != null ? !position.equals(that.position)
                : that.position != null) {
            return false;
        }
        return texture != null ? texture.equals(that.texture)
                : that.texture == null;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (texture != null ? texture.hashCode() : 0);
        return result;
    }

    /**
     * Returns the parent of this world
     *
     * @return the AbstractWorld related to this world.
     * @deprecated
     */
    @Deprecated
    public World getParent() {
        return GameManager.get().getWorld();
    }

    /**
     * Returns the distance from the given entity
     * @param e the entity to find the distance of
     * @return the distanct from the given entity
     */
    public float distance(AbstractEntity e) {
        return this.getBox3D().distance(e.getBox3D());
    }

    /**
     * set the centered attributes to true - @ken
     */
    public void setCentered() {
        this.centered = true;
    }
    
    /**
     * Sets a tint colour for this entity
     * 
     * @param tint
     * 			the colour to set the tint to
     */
    public void setTint(Color tint) {
    	this.tint = tint;
    }
    
    /**
     * Removes any tint this entity may have
     */
    public void removeTint() {
    	this.tint = null;
    }
    
    /**
     * Gets the tint of this entity
     * 
     * @return the tint colour, null if there is none
     */
    public Color getTint() {
    	return tint;
    }
}
