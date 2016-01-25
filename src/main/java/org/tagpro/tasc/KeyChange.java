package org.tagpro.tasc;

public class KeyChange {
    private final Key key;
    private final KeyAction keyAction;
    private final int step;

    public KeyChange(Key key, KeyAction keyAction, int step) {
        this.key = key;
        this.keyAction = keyAction;
        this.step = step;
    }

    public Key getKey() {
        return key;
    }

    public KeyAction getKeyAction() {
        return keyAction;
    }

    public int getStep() {
        return step;
    }
}
