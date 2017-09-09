package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.Texture;
import com.deco2800.hcg.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests to ensure TextureManager is in a working state.
 *
 * @author Richy McGregor
 */
public class TextureManagerTests extends BaseTest {

    // This is the only actually important test here
    // Check that TextureManager is able to read all the required resources
    @Test
    public void testConstructor() {
        try {
            TextureManager textureManager = new TextureManager();
        } catch (com.badlogic.gdx.utils.GdxRuntimeException e) {
            Assert.fail("There is almost certainly a misspelled filename in TextureManager");
        }
    }

    // Check that .getTexture is sane
    @Test
    public void testGetTexture() {
        TextureManager textureManager = new TextureManager();
        Texture error = textureManager.getTexture("error");
        Assert.assertNotNull("Unable to get error texture", error);
        Texture nonsense = textureManager.getTexture("THIS_SHOULD_NEVER_EXIST");
        Assert.assertNull("Able to get non-existent texture", nonsense);
    }

    // Check that .saveTexture is sane
    @Test
    public void testSaveTexture() {
        TextureManager textureManager = new TextureManager();
        Texture test = textureManager.getTexture("test");
        Assert.assertNull("Test should not yet be loaded", test);
        textureManager.saveTexture("test","resources/misc/test.png");
        test = textureManager.getTexture("test");
        Assert.assertNotNull("Unable to get test texture", test);
    }

}
