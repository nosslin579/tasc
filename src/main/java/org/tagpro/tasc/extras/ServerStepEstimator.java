package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.DaemonThreadFactory;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.box2d.TagProWorld;
import org.tagpro.tasc.data.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class will estimate at which step a key will be received at server.
 */
public class ServerStepEstimator implements GameSubscriber, Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final List<ServerStepObserver> observers = new ArrayList<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory("SyncService"));
    //Key=count, Value=KeyAction
    private Map<Integer, KeyChange> unregisteredKeyChanges = new ConcurrentHashMap<>();
    private volatile int id = -1;
    private AtomicInteger stepAtServer = new AtomicInteger(0);
    private Command command;

    @Override
    public void init(Command command) {
        this.command = command;
    }

    public void addListener(ServerStepObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void keyPressed(Key key, KeyAction keyAction, int count) {
        //saving pressed key for sync checking
        int step = stepAtServer.get();
        unregisteredKeyChanges.put(count, new KeyChange(key, keyAction, step));
    }


    @Override
    public void onUpdate(final int step, Map<Integer, Update> updates) {
        Update update = updates.get(id);
        if (update == null) {
            //Not self player
            return;
        }

        //Sync expected step
        for (KeyUpdate ku : update.getKeyUpdate()) {
            KeyChange unregisteredKeyChange = unregisteredKeyChanges.getOrDefault(ku.getCounter(), null);
            if (unregisteredKeyChange == null || ku.getCounter() == 0) {//counter == 0 means init
                log.warn("No logged step for: " + ku.getCounter());
                return;
            }
            int expectedStepAtServer = unregisteredKeyChange.getStep();
            int diff = step - expectedStepAtServer;
            log.debug("Adjusting server step. Counter:" + ku.getCounter() + " Expected:" + expectedStepAtServer + " Actual:" + step + " Diff:" + diff);
            unregisteredKeyChanges.remove(ku.getCounter());

            //adjusting
            if (Math.abs(diff) > 10) {
                stepAtServer.set(step);
            } else if (Math.abs(diff) <= 2) {
                log.debug("Not adjusting since diff is: " + diff);
            } else if (expectedStepAtServer > step) {
                stepAtServer.decrementAndGet();
            } else if (expectedStepAtServer < step) {
                scheduledExecutorService.submit(this);
            }
        }

    }

    @Override
    public void onId(int id) {
        this.id = id;
        long initialDelay = TimeUnit.SECONDS.toNanos(1);
        scheduledExecutorService.scheduleAtFixedRate(this, initialDelay, TagProWorld.STEP_NS, TimeUnit.NANOSECONDS);
    }

    @Override
    public void time(int time, GameState gameState) {
        //press up five times to sync stepAtServer
        if (gameState == GameState.NOT_YET_STARTED) {
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory("InitialKeyPressSync"));
            for (Integer keyTime : Arrays.asList(1000, 1200, 1400, 1600, 1800, 2000)) {
                executor.schedule(() -> command.key(Key.UP, KeyAction.KEYDOWN), keyTime, TimeUnit.MILLISECONDS);
                executor.schedule(() -> command.key(Key.UP, KeyAction.KEYUP), keyTime + 100, TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override
    public void run() {
        try {
            int step = stepAtServer.incrementAndGet();
            for (ServerStepObserver o : observers) {
                o.onEstimateStep(step);
            }
        } catch (Exception e) {
            log.error("Run method failed", e);
            command.disconnect();
            System.exit(0);
        }
    }

    public int getStepAtServer() {
        return stepAtServer.get();
    }

    public interface ServerStepObserver {
        void onEstimateStep(int step);
    }
}
