package org.tagpro.examplebots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.*;
import org.tagpro.tasc.extras.Box2DEstimator;
import org.tagpro.tasc.extras.EstimateObserver;
import org.tagpro.tasc.extras.MaxTime;
import org.tagpro.tasc.extras.SyncService;
import org.tagpro.tasc.listeners.BallUpdate;
import org.tagpro.tasc.listeners.GameState;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SyncedBot implements GameSubscriber, EstimateObserver {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Command command;
    private Integer id;
    private Map<Integer, PlayerState> estimateMap = new HashMap<>();
    private DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        Starter s = new Starter();

        SyncedBot bot = new SyncedBot();
        Box2DEstimator estimator = new Box2DEstimator();

        s.addListener(estimator);
        s.addListener(new SyncService(bot, estimator));
        s.addListener(new MaxTime(4, TimeUnit.SECONDS));
        s.addListener(bot);

        s.start();
    }


    @Override
    public void time(int time, GameState state) {
        if (state == GameState.ACTIVE) {
            log.info("Game active ######################################################################################################################################");
            command.key(Key.RIGHT, KeyAction.KEYDOWN);
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                command.key(Key.RIGHT, KeyAction.KEYUP);
                command.key(Key.LEFT, KeyAction.KEYDOWN);
            }, 2, TimeUnit.SECONDS);
        }
    }

    @Override
    public void init(Command command) {
        log.info("Init bot");
        this.command = command;
    }

    @Override
    public void onId(int id) {
        this.id = id;
    }

    @Override
    public void currentEstimatedLocation(int step, PlayerState self) {
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
