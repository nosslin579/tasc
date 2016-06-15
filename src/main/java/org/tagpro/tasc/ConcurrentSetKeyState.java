package org.tagpro.tasc;

import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentSetKeyState implements KeyState {
    private Set<Key> keyState = ConcurrentHashMap.newKeySet();

    @Override
    public boolean setKey(Key key, KeyAction keyAction) {
        return keyAction.isPushed() ? keyState.add(key) : keyState.remove(key);
    }

    @Override
    public boolean isPushed(Key key) {
        return keyState.contains(key);
    }

    @Override
    public KeyAction getStateFor(Key key) {
        return keyState.contains(key) ? KeyAction.KEYDOWN : KeyAction.KEYUP;
    }

    @Override
    public float getHorizontalAcceleration(float surface) {
        if (keyState.contains(Key.LEFT)) {
            return keyState.contains(Key.RIGHT) ? 0 : -surface;
        }
        return keyState.contains(Key.RIGHT) ? surface : 0;
    }

    @Override
    public float getVerticalAcceleration(float surface) {
        if (keyState.contains(Key.UP)) {
            return keyState.contains(Key.DOWN) ? 0 : -surface;
        }
        return keyState.contains(Key.DOWN) ? surface : 0;
    }
}
