package org.tagpro.bots;

import org.tagpro.tasc.PressedKeys;
import org.tagpro.tasc.data.BallUpdate;

public interface BallPredictor {
    BallUpdate predict(BallUpdate ballUpdate, int stepsForward, PressedKeys pressedKeys, float surfaceAcceleration);

    float predictPositionAtHalt(float currentVelocity, float currentPos, float surfaceAcceleration);
}
