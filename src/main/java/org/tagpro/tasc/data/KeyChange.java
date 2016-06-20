package org.tagpro.tasc.data;

public class KeyChange {
    private final Key key;
    private final KeyState keyState;
    private final int step;

    public KeyChange(Key key, KeyState keyState, int step) {
        this.key = key;
        this.keyState = keyState;
        this.step = step;
    }

    public Key getKey() {
        return key;
    }

    public KeyState getKeyState() {
        return keyState;
    }

    public int getStep() {
        return step;
    }
}
