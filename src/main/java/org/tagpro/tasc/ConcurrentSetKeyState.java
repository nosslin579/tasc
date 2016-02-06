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
}
