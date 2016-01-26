package org.tagpro.tasc;

public interface KeyState {
    boolean setKey(Key key, KeyAction keyAction);

    boolean isPushed(Key key);
}
