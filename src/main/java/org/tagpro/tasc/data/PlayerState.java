package org.tagpro.tasc.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PlayerState extends BallUpdate{
    private final Set<Key> pressedKeys;

    public PlayerState(int id, float rx, float ry, float lx, float ly, float a, float ra, Collection<Key> pressedKeys) {
        super(id, rx, ry, lx, ly, a, ra);
        this.pressedKeys = new HashSet<>(pressedKeys);
    }

    public Set<Key> getPressedKeys() {
        return pressedKeys;
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "pressedKeys=" + pressedKeys +
                '}' + super.toString();
    }
}
