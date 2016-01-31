package org.tagpro.tasc.extras;

import org.tagpro.tasc.KeyChange;
import org.tagpro.tasc.PlayerState;

import java.util.List;

public interface Estimator {

    PlayerState estimate(int step, List<KeyChange> keyChanges);
}
