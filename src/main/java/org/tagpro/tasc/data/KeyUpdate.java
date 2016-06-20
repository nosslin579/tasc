package org.tagpro.tasc.data;

public class KeyUpdate {
    private final int id;
    private final Key key;
    private final KeyState keyState;
    private final int counter;

    public KeyUpdate(int id, Key key, KeyState keyState, int counter) {
        this.id = id;
        this.key = key;
        this.keyState = keyState;
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public Key getKey() {
        return key;
    }

    public KeyState getKeyState() {
        return keyState;
    }

    public int getCounter() {
        return counter;
    }
}
