package org.tagpro.tasc.extras;

import org.tagpro.tasc.KeyChange;

import java.util.List;

public interface SyncObserver {
    void step(int serverStep, List<KeyChange> unregisteredKeyChanges);
}
