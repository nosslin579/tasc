package org.tagpro.tasc.extras;

import org.tagpro.tasc.PlayerState;

public interface EstimateObserver {

    /**
     * This observer estimate where ball is and how fast it is going.
     * Implementing classes must may miss out steps.
     *
     * @param step the step where self is estimated
     * @param self estimate of own ball speed and location at server plus ping
     */
    void currentEstimatedLocation(int step, PlayerState self);
}
