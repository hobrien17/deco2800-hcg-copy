package com.deco2800.hcg.renderers;

/**
 * An object that can be rendered on the screen. Renderables should have a
 * texture that they can return when asked using the onRender function.
 *
 * Textures should be size suitable to the game.
 */
public interface Renderable {

    /**
     * Renderables must impliment the onRender function. This function allows
     * the current rendering system to request a texture from the object being
     * rendered.
     *
     * Returning null will render an error image in this items place.
     *
     * @return The texture to be rendered onto the screen
     */
    String getTexture();

    /**
     * Returns the x position of the object
     * @return the x position of the object
     */
    float getPosX();

    /**
     * Returns the y position of the object
     * @return the y position of the object
     */
    float getPosY();

    /**
     * Returns the z position of the object
     * @return the z position of the object
     */
    float getPosZ();

    /**
     * Returns the rendering length in relation to the x axis of the object.
     * @return the rendering length in relation to the x axis
     */
    float getXRenderLength();

    /**
     * Returns the rendering length in relation to the y axis of the object
     * @return the rendering length in relation to the y axis
     */
    float getYRenderLength();
}
