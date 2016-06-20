package org.tagpro.tasc;

import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyState;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentSetPressedKeys implements PressedKeys {
    private Set<Key> keyState = ConcurrentHashMap.newKeySet();

    public boolean setKey(Key key, KeyState keyState) {
        return keyState.isPushed() ? this.keyState.add(key) : this.keyState.remove(key);
    }

    @Override
    public boolean isPushed(Key key) {
        return keyState.contains(key);
    }

    @Override
    public KeyState getStateFor(Key key) {
        return keyState.contains(key) ? KeyState.KEYDOWN : KeyState.KEYUP;
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
