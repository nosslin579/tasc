package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.BallUpdate;
import org.tagpro.tasc.data.PlayerState;
import org.tagpro.tasc.data.Update;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ClientSidePredictionLogger implements GameSubscriber, ClientSidePredictionObserver {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Integer id;
    private Map<Integer, PlayerState> estimateMap = new HashMap<>();
    private DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void onId(int id) {
        this.id = id;
    }

    @Override
    public void predicatedLocation(int step, PlayerState self) {
        estimateMap.put(step, self);
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
        log.info("Result: (actual,estimate) rx:(" + actual.getRx() + "," + rx + ") ry:(" + actual.getRy() + "," + ry + ") lx:(" + actual.getLx() + "," + lx + ") " + " ly:(" + actual.getLy() + "," + ly + ")");
    }
}
