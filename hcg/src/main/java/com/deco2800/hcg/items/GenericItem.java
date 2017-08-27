package com.deco2800.hcg.items;

public abstract class GenericItem implements Item {
    protected int baseValue;
    protected int itemWeight;
    protected String itemName;
    protected String texture;

    /**
     * Retrieves an items display name
     *
     * @return Name of item as String
     */
    @Override
    public String getName() {
        return this.itemName;
    }

    /**
     * Retrieves the base value of this particular item.
     *
     * @return The base value of this item.
     */
    @Override
    public int getBaseValue() {
        return this.baseValue;
    }

    /**
     * Function for setting the icon of an item
     * Implemented similar to the AbstractEntitry texture. Be sure to register texture with
     * TextureRegister before assigning the texture to a item
     *
     * @param texture : filename of texture
     * @throws IllegalArgumentException if texture is an invalid file name
     */
    public void setTexture(String texture) throws IllegalArgumentException {
        // TODO: implement textures
    }

    @Override
    public boolean equals(Item item) {
        return this == item;
    }
}
