package org.tagpro.tasc.data;

public class KeyUpdate {
    private final int id;
    private final Key key;
    private final KeyAction keyAction;
    private final int counter;

    public KeyUpdate(int id, Key key, KeyAction keyAction, int counter) {
        this.id = id;
        this.key = key;
        this.keyAction = keyAction;
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public Key getKey() {
        return key;
    }

    public KeyAction getKeyAction() {
        return keyAction;
    }

    public int getCounter() {
        return counter;
    }
}
