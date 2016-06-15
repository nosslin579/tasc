package org.tagpro.examples;

import org.tagpro.tasc.KeyState;
import org.tagpro.tasc.data.BallUpdate;

public interface BallPredictor {
    BallUpdate predict(BallUpdate ballUpdate, int stepsForward, KeyState keyState, float surfaceAcceleration);

    float predictPositionAtHalt(float currentVelocity, float currentPos, float surfaceAcceleration);
}
