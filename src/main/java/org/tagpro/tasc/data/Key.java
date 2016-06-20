package org.tagpro.tasc.data;

public enum Key {
    UP(), DOWN(), LEFT(), RIGHT(), SPACE;

    public String getCommand() {
        return name().toLowerCase();
    }

    public Key getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        return SPACE;
    }
}
