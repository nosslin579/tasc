package org.tagpro.bots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.*;
import org.tagpro.tasc.extras.ServerStepEstimator;

import java.util.Map;

public class Precision implements GameSubscriber, ServerStepEstimator.ServerStepObserver {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final GamePad gamePad;
    private final ServerStepEstimator stepEstimator;
    private final BallPredictor ballPredictor = new EquationBallPredictor();

    private int id;
    private volatile BallUpdate lastUpdate;
    private volatile int lastUpdateStep;

    private int c = 0;

    public Precision(GamePad gamePad, ServerStepEstimator stepEstimator) {
        this.gamePad = gamePad;
        this.stepEstimator = stepEstimator;
    }

    @Override
    public void onEstimateStep(int step) {
        int diffSteps = step - lastUpdateStep;
        BallUpdate current = ballPredictor.predict(lastUpdate, diffSteps, gamePad.getKeyState(), 0.025f);
        float positionIfReverse = ballPredictor.predictPositionAtHalt(current.getLx(), current.getRx(), 0.025f);

//        log.info("Ac:" + verticalAc + " LastV:" + lastUpdate.getLx() + " CurrentV:" + currentVelocity + " SUSS:" + stepsUntilStandStill);
        log.info("CurrentP:" + current.getRx() + " positionIfReverse:" + positionIfReverse + " c" + c++);
        if (lastUpdate.getLx() < -1.4f && positionIfReverse < 0.33f && gamePad.isPushed(Key.LEFT)) {
            gamePad.key(Key.RIGHT, KeyState.KEYDOWN);
            c = 0;
        } else if (lastUpdate.getLx() > 1.4f && positionIfReverse > 5 && gamePad.isPushed(Key.RIGHT)) {
            gamePad.key(Key.LEFT, KeyState.KEYDOWN);
            c = 0;
        }
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        Update self = updates.get(id);
        if (self != null && self.getBallUpdate() != null) {
            this.lastUpdate = self.getBallUpdate();
            this.lastUpdateStep = step;
            log.info("Update rx:" + lastUpdate.getRx() + " step:" + step);
        }
    }

    @Override
    public void time(int time, GameState gameState) {
        if (gameState == GameState.ACTIVE) {
            gamePad.key(Key.LEFT, KeyState.KEYDOWN);
            stepEstimator.addListener(this);
        }
    }

    @Override
    public void onId(int id) {
        this.id = id;
    }

}
