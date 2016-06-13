package org.tagpro.tasc.box2d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.BallUpdate;
import org.tagpro.tasc.data.PlayerState;
import org.tagpro.tasc.data.Update;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSidePredictionLogger implements GameSubscriber, Box2DClientSidePredictor.ClientSidePredictionObserver {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Integer id;
    private Map<Integer, PlayerState> estimateMap = new ConcurrentHashMap<>();
    private int successfulPrediction = 0, failedPrediction = 0;
    private DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void onId(int id) {
        this.id = id;
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        Update self = updates.get(id);
        if (self == null) {
            return;
        }
        BallUpdate actual = self.getBallUpdate();
        if (actual == null) {
            return;
        }
        PlayerState estimate = estimateMap.get(step);
        if (estimate == null) {
            return;
        }
        String rx = df.format(estimate.getRx());
        String ry = df.format(estimate.getRy());
        String ly = df.format(estimate.getLy());
        String lx = df.format(estimate.getLx());
        log.debug("Result: (actual,estimate) rx:(" + actual.getRx() + "," + rx + ") ry:(" + actual.getRy() + "," + ry + ") lx:(" + actual.getLx() + "," + lx + ") " + " ly:(" + actual.getLy() + "," + ly + ")");
        compare(actual.getRx(), estimate.getRx(), "rx");
        compare(actual.getRy(), estimate.getRy(), "ry");
        compare(actual.getLx(), estimate.getLx(), "lx");
        compare(actual.getLy(), estimate.getLy(), "ly");
        estimateMap.clear();

    }

    private void compare(float actual, float estimate, String field) {
        String logString = field + " estimate failed, actual:" + actual + " estimate:" + estimate;
        if (Math.abs(actual - estimate) > 0.2) {
            log.error(logString);
        } else if (Math.abs(actual - estimate) > 0.02) {
            log.warn(logString);
        }
    }

    @Override
    public void onPredict(int step, PlayerState playerState) {
        estimateMap.put(step, playerState);
    }
}
