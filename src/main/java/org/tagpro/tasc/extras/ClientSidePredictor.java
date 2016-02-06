package org.tagpro.tasc.extras;

import org.tagpro.tasc.PlayerState;

/**
 * Predicts ball.
 * https://en.wikipedia.org/wiki/Client-side_prediction
 */
public interface ClientSidePredictor {

    PlayerState predict(int step);
}
