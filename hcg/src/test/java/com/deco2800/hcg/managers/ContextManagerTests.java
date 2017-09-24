package com.deco2800.hcg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.BaseTest;
import com.deco2800.hcg.contexts.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Test functionality of the Context Manager.
 * This is complicated be several factors:
 * <ul>
 * <li>Because Gdx.gl is mocked, touching a Stage will cause a crash</li>
 * <li>Because GameManager is static, all tests must share a single instance of ContextManager</li>
 * <li>When the last Context in a Context Manager is popped, it will terminate execution</li>
 * </ul>
 * @author Richy McGregor
 */
public class ContextManagerTests extends BaseTest {

    private static ContextManager contextManager;
    private static Context bottom;

    // Create the Game Manager and Context Manager used in all tests
    // In order to prevent premature termination, a TestContext called "Bottom" is pushed
    @BeforeClass
    public static void setupCM() {
        Manager uncastCM = GameManager.get().getManager(ContextManager.class);
        Assert.assertTrue(uncastCM instanceof ContextManager);
        contextManager = (ContextManager) uncastCM;
        Assert.assertNull(contextManager.currentContext());
        bottom = new TestContext("Bottom");
        Assert.assertEquals("Bottom", bottom.toString());
        contextManager.pushContext(bottom);
        Assert.assertSame(bottom, contextManager.currentContext());
    }


    // Remove unwanted Contexts so that "Bottom" is on the top of the Context stack
    @Before
    public void cleanCM() {
        int count = 0;
        while (contextManager.currentContext() != bottom) {
            Assert.assertTrue(count < 100);
            contextManager.popContext();
            count += 1;
        }
    }


    // Run through a series of Context push and pop operations to ensure Context Manager functions normally
    @Test
    public void basicTest() {

        // base context
        TestContext baseContext = new TestContext();
        contextManager.pushContext(baseContext);
        Assert.assertSame(baseContext, getTop());
        Assert.assertEquals("", getTop().toString());

        // click "foo" to add a foo context
        getTop().clickAddFoo();
        Assert.assertNotEquals(baseContext, getTop());
        Assert.assertNotSame(baseContext, getTop());
        Assert.assertEquals("Foo", getTop().toString());

        // manually pop back to base context
        contextManager.popContext();
        Assert.assertSame(baseContext, getTop());
        Assert.assertEquals("", getTop().toString());

        // click "bar" to add a bar context
        getTop().clickAddBar();
        Assert.assertNotSame(baseContext, getTop());
        Assert.assertEquals("Bar", getTop().toString());

        // press "back" to go back to base context
        getTop().clickBack();
        Assert.assertSame(baseContext, getTop());
        Assert.assertEquals("", getTop().toString());

        // Drop back to Bottom
        contextManager.popContext();
        Assert.assertSame(bottom, contextManager.currentContext());
    }


    // Build a deep context stack and then unwind it, to ensure Context Manager can handle many contexts at once
    @Test
    public void depthTest() {
        final String testString = "StartFooFooBarFooFooBarFooFooBarFooFooBarFooFooBarFooFooBar";
        contextManager.pushContext(new TestContext("Start"));
        for (int i = 0; i < 6; i++) {
            getTop().clickAddFoo();
            getTop().clickAddFoo();
            getTop().clickAddBar();
        }
        Assert.assertEquals(testString, getTop().toString());
        int depth = 0;
        while (contextManager.currentContext() != bottom) {
            Assert.assertTrue(depth < 100); // Only YOU can prevent infinite loops!
            contextManager.popContext();
            depth += 1;
        }
        Assert.assertEquals(1+6*3, depth);
    }


    // Convenience method to grab the top of the context stack, and cast it to a TestContext
    private TestContext getTop() {
        Context currentContext = contextManager.currentContext();
        Assert.assertTrue(currentContext instanceof TestContext);
        return (TestContext) currentContext;
    }


    // I know having this here is kind of ugly and out of place,
    // but it's handy for testing and I'd rather not throw it away
    private static class TestContext extends Context {

        private Label desc;
        private TextButton addFoo;
        private TextButton addBar;
        private TextButton back;
        private Table table;

        TestContext() {
            this("");
        }

        TestContext(String name) {

            ContextManager cm = (ContextManager) GameManager.get().getManager(ContextManager.class);

            Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

            table = new Table();
            table.setFillParent(true);

            desc = new Label(name, skin);
            addFoo = new TextButton("+Foo", skin);
            addBar = new TextButton("+Bar", skin);
            back = new TextButton("back", skin);

            table.add(desc);
            table.add(addFoo);
            table.add(addBar);
            table.add(back);
            //stage.addActor(table);

            addFoo.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    cm.pushContext(new TestContext(name+"Foo"));
                }
            });

            addBar.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    cm.pushContext(new TestContext(name+"Bar"));
                }
            });

            back.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    cm.popContext();
                }
            });

        }

        @Override
        public String toString() {
            return desc.getText().toString();
        }

        public void clickAddFoo() {
            addFoo.toggle();
        }

        public  void clickAddBar() {
            addBar.toggle();
        }

        public void clickBack() {
            back.toggle();
        }

        @Override
        public void dispose() {}

        @Override
        public void show() {}

        @Override
        public void hide() {}

        @Override
        public void pause() {}

        @Override
        public void resume() {}

        @Override
        public void render(float delta) {}

        @Override
        public void resize(int width, int height) {}

        @Override
        public void onTick(long gameTickCount) {}

        @Override
        public boolean ticksRunning() {
            return false;
        }

    }

}
