package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.*;
import org.tagpro.tasc.listeners.BallUpdate;
import org.tagpro.tasc.listeners.GameState;
import org.tagpro.tasc.listeners.KeyUpdate;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SyncService implements GameSubscriber, Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Logger recordLogger = LoggerFactory.getLogger(RecordListener.class);
    private final SyncObserver observer;
    private final Executor publisherExecutor;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory("SyncService"));
    //Key=count, Value=KeyAction
    private Map<Integer, KeyChange> unregisteredKeyChanges = new ConcurrentHashMap<>();
    private volatile int id = -1;
    private AtomicInteger stepAtServer = new AtomicInteger(0);
    private final Map<Key, KeyAction> state = new ConcurrentHashMap<>();
    private Command command;
    private volatile TagProWorld world = new TagProWorld(1);


    public SyncService(SyncObserver observer, Executor publisherExecutor) {
        this.observer = observer;
        this.publisherExecutor = publisherExecutor;
        state.put(Key.LEFT, KeyAction.KEYUP);
        state.put(Key.RIGHT, KeyAction.KEYUP);
        state.put(Key.UP, KeyAction.KEYUP);
        state.put(Key.DOWN, KeyAction.KEYUP);
        state.put(Key.SPACE, KeyAction.KEYUP);
    }

    @Override
    public void init(Command command) {
        this.command = command;
    }

    @Override
    public void keyPressed(Key key, KeyAction keyAction, int count) {
        if (state.get(key) == keyAction) {
            log.warn("Key at correct state, no need to send again. Key:" + key + "=" + keyAction.getBooleanValue() + " KeyState=" + state);
        }
        int step = stepAtServer.get();
        log.info("Sending keyaction to server. key:" + key + "=" + keyAction.getBooleanValue() + " counter:" + count + " expected step at server:" + step);
        state.put(key, keyAction);
        if (!state.values().contains(KeyAction.KEYDOWN)) {
            log.debug("Not pressing any key");
        }
        unregisteredKeyChanges.put(count, new KeyChange(key, keyAction, step));

        if (recordLogger.isDebugEnabled()) {
            String sep = RecordListener.SEPARATOR2;
            recordLogger.debug("o" + sep + System.nanoTime() + sep + key.getCommand() + sep + key + sep + keyAction);
        }
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
            int expectedStepAtServer = unregisteredKeyChange.getStepAtServer();
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


        final BallUpdate ballUpdate = update.getBallUpdate();
        if (ballUpdate != null) {
            //updating self
            scheduledExecutorService.submit(() -> {
                world.getPlayer(1).setBodyPositionAndVelocity(ballUpdate);
                world.setStep(step);
            });
        }

    }

    @Override
    public void onId(int id) {
        this.id = id;
        world = new TagProWorld(1);
        long initialDelay = TimeUnit.SECONDS.toNanos(1);
        scheduledExecutorService.scheduleAtFixedRate(this, initialDelay, TagProWorld.STEP_NS, TimeUnit.NANOSECONDS);
    }

    @Override
    public void time(int time, GameState gameState) {
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
        //Because we want the possibility to make the bot single threaded.
        Collection<KeyChange> values = unregisteredKeyChanges.values();
        List<KeyChange> es = new ArrayList<>(values);
        int step = stepAtServer.incrementAndGet();
        world.proceedToStep(step);
        PlayerState player = world.getPlayer(1).getPlayerState();
        publisherExecutor.execute(() -> {
            try {
                observer.currentLocation(player);
            } catch (Exception e) {
                log.error("Error executing step:" + stepAtServer, e);
                System.exit(1);
            }
        });
    }

}
