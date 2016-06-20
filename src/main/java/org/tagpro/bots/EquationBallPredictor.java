package org.tagpro.bots;

import org.tagpro.tasc.PressedKeys;
import org.tagpro.tasc.box2d.TagProWorld;
import org.tagpro.tasc.data.BallUpdate;

public class EquationBallPredictor implements BallPredictor {
    @Override
    public BallUpdate predict(BallUpdate ballUpdate, int stepsForward, PressedKeys pressedKeys, float surface) {

        float verticalAc = pressedKeys.getVerticalAcceleration(surface);
        float horizontalAc = pressedKeys.getHorizontalAcceleration(surface);

        float lx = TagProWorld.calculateSpeed(ballUpdate.getLx(), horizontalAc, stepsForward);
        float ly = TagProWorld.calculateSpeed(ballUpdate.getLy(), verticalAc, stepsForward);

        float rx = ballUpdate.getRx() + TagProWorld.calculatePosition(ballUpdate.getLx(), horizontalAc, stepsForward);
        float ry = ballUpdate.getRy() + TagProWorld.calculatePosition(ballUpdate.getLy(), verticalAc, stepsForward);

        return new BallUpdate(ballUpdate.getId(), rx, ry, lx, ly, 0, 0);
    }

    @Override
    public float predictPositionAtHalt(float currentVelocity, float currentPos, float surfaceAcceleration) {
        int suss = calculateStepsUntilStandStill(currentVelocity, surfaceAcceleration);

        float acceleration = currentVelocity > 0 ? -surfaceAcceleration : surfaceAcceleration;
        return currentPos + TagProWorld.calculatePosition(currentVelocity, acceleration, suss);
    }

    private int calculateStepsUntilStandStill(float initialVelocity, float acceleration) {
        float v = Math.abs(initialVelocity);
        float ac = Math.abs(acceleration);
        return (int) TagProWorld.calculateSteps(v, -ac, 0f);
    }
}
