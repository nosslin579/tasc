package org.tagpro.tasc.data;

public enum KeyState {
    KEYUP(false), KEYDOWN(true);
    private final boolean booleanValue;

    KeyState(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getCommand() {
        return name().toLowerCase();
    }

    public Boolean isPushed() {
        return booleanValue;
    }
}
