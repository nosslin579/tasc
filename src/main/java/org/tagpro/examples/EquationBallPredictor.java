package org.tagpro.examples;

import org.tagpro.tasc.KeyState;
import org.tagpro.tasc.box2d.TagProWorld;
import org.tagpro.tasc.data.BallUpdate;

public class EquationBallPredictor implements BallPredictor {
    @Override
    public BallUpdate predict(BallUpdate ballUpdate, int stepsForward, KeyState keyState, float surface) {

        float verticalAc = keyState.getVerticalAcceleration(surface);
        float horizontalAc = keyState.getHorizontalAcceleration(surface);

        float lx = TagProWorld.calculateSpeed(ballUpdate.getLx(), horizontalAc, stepsForward);
        float ly = TagProWorld.calculateSpeed(ballUpdate.getLy(), verticalAc, stepsForward);

        float rx = ballUpdate.getRx() + TagProWorld.calculatePosition(ballUpdate.getLx(), horizontalAc, stepsForward);
        float ry = ballUpdate.getRy() + TagProWorld.calculatePosition(ballUpdate.getLy(), verticalAc, stepsForward);

        return new BallUpdate(ballUpdate.getId(), rx, ry, lx, ly, 0, 0);
    }

    @Override
    public float predictPositionAtHalt(float currentVelocity, float currentPos, float surfaceAcceleration) {
        float acceleration = currentVelocity > 0 ? -surfaceAcceleration : surfaceAcceleration;
        int suss = TagProWorld.calculateStepsUntilStandStill(currentVelocity, acceleration);
        return currentPos + TagProWorld.calculatePosition(currentVelocity, acceleration, suss);
    }
}
