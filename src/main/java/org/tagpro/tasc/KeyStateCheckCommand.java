package org.tagpro.tasc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;

public class KeyStateCheckCommand extends Command {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private KeyState keyState = new ConcurrentSetKeyState();

    public KeyStateCheckCommand() {
        super();
    }

    public void key(Key key, KeyAction keyAction) {
        boolean changed = keyState.setKey(key, keyAction);
        if (changed) {
            super.key(key, keyAction);
            if (keyAction.isPushed() && keyState.isPushed(key.getOpposite())) {
                key(key.getOpposite(), KeyAction.KEYUP);
            }
        }
    }

    public boolean isPushed(Key key) {
        return keyState.isPushed(key);
    }
}
