package org.tagpro.tasc.extras;

import org.tagpro.tasc.PlayerState;

public interface Estimator {

    PlayerState estimate(int step);
}
