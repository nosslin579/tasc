package org.tagpro.tasc;

import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;

public interface KeyState {
    boolean setKey(Key key, KeyAction keyAction);

    boolean isPushed(Key key);
}
