package org.tagpro.bots;

import org.tagpro.tasc.data.BallUpdate;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ControllerTest {

    @Test
    public void testGoToLeftFromStandstill() throws Exception {
        MockController c = new MockController();
        c.goTo(1f, 1f, new BallUpdate(1, 8f, 1f, -1f, 0f, 0f, 0f));
        final Map<Key, KeyAction> expected = new HashMap<>();
        expected.put(Key.LEFT, KeyAction.KEYDOWN);
        expected.put(Key.UP, KeyAction.KEYUP);
        Assert.assertEquals(c.getPressedKeys(), expected);
    }

    @Test
    public void testGoToLeftUpFromToMuchDownSpeed() throws Exception {
        MockController c = new MockController();
        c.goTo(1f, 1f, new BallUpdate(1, 8.4f, 3.6f, -1f, 5f, 0f, 0f));
        final Map<Key, KeyAction> expected = new HashMap<>();
        expected.put(Key.LEFT, KeyAction.KEYDOWN);
        expected.put(Key.UP, KeyAction.KEYDOWN);
        Assert.assertEquals(c.getPressedKeys(), expected);
    }

    @Test
    public void testGoToLeftUpFromToMuchUpSpeed() throws Exception {
        MockController c = new MockController();
        c.goTo(1f, 1f, new BallUpdate(1, 8.4f, 3.6f, -1f, -5f, 0f, 0f));
        final Map<Key, KeyAction> expected = new HashMap<>();
        expected.put(Key.LEFT, KeyAction.KEYDOWN);
        expected.put(Key.UP, KeyAction.KEYUP);
        Assert.assertEquals(c.getPressedKeys(), expected);
    }

    @Test
    public void testGoToLeftDownFromToMuchDownSpeed() throws Exception {
        MockController c = new MockController();
        c.goTo(1f, 5f, new BallUpdate(1, 8.4f, 3.6f, -1f, 5f, 0f, 0f));
        final Map<Key, KeyAction> expected = new HashMap<>();
        expected.put(Key.LEFT, KeyAction.KEYDOWN);
        expected.put(Key.DOWN, KeyAction.KEYUP);
        Assert.assertEquals(c.getPressedKeys(), expected);
    }

    @Test
    public void testGoToLeftDownFromToMuchUpSpeed() throws Exception {
        MockController c = new MockController();
        c.goTo(1f, 5f, new BallUpdate(1, 8.4f, 3.6f, -1f, -5f, 0f, 0f));
        final Map<Key, KeyAction> expected = new HashMap<>();
        expected.put(Key.LEFT, KeyAction.KEYDOWN);
        expected.put(Key.DOWN, KeyAction.KEYDOWN);
        Assert.assertEquals(c.getPressedKeys(), expected);
    }


    private class MockController extends Controller {
        private Map<Key, KeyAction> pressedKeys = new HashMap<>();

        public MockController() {
            super(null, null);
        }

        @Override
        public void key(Key key, KeyAction keyAction) {
            pressedKeys.put(key, keyAction);
        }

        public Map<Key, KeyAction> getPressedKeys() {
            return pressedKeys;
        }
    }
}