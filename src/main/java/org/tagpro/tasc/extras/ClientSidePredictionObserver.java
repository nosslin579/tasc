package org.tagpro.tasc.extras;

import org.tagpro.tasc.data.PlayerState;

public interface ClientSidePredictionObserver {

    /**
     * This observer predicts where ball is and how fast it is going.
     * Implementing classes may chose to skip steps.
     *
     * @param step the step where self is estimated
     * @param self estimate of own ball speed and location at server plus ping
     */
    void predicatedLocation(int step, PlayerState self);
}
