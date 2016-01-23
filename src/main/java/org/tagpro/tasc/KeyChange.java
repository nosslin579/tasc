package org.tagpro.tasc;

public class KeyChange {
    private final Key key;
    private final KeyAction keyAction;
    private final int stepAtServer;

    public KeyChange(Key key, KeyAction keyAction, int stepAtServer) {
        this.key = key;
        this.keyAction = keyAction;
        this.stepAtServer = stepAtServer;
    }

    public Key getKey() {
        return key;
    }

    public KeyAction getKeyAction() {
        return keyAction;
    }

    public int getStepAtServer() {
        return stepAtServer;
    }
}
