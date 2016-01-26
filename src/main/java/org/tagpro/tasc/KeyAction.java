package org.tagpro.tasc;

public enum KeyAction {
    KEYUP(false), KEYDOWN(true);
    private final boolean booleanValue;

    KeyAction(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getCommand() {
        return name().toLowerCase();
    }

    public Boolean isPushed() {
        return booleanValue;
    }
}
