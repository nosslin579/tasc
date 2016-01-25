package org.tagpro.tasc.extras;

import org.tagpro.tasc.PlayerState;

public interface SyncObserver {

    /**
     * This method forwards world a couple of steps and tries to estimate where ball is and how fast it is going.
     * Act on this method instead of <i>update</i> where you get the ball location with up to 200ms delay.
     * Fired each step.
     *
     * @param step the step where self is estimated
     * @param self estimate of own ball speed and location at server plus ping
     */
    void currentLocation(int step, PlayerState self);
}
