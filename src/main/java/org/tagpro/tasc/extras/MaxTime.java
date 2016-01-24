package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.listeners.GameState;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MaxTime implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final int maxTime;
    private final TimeUnit timeUnit;
    private ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
    private Command command;


    public MaxTime(int maxTime, TimeUnit timeUnit) {
        this.maxTime = maxTime;
        this.timeUnit = timeUnit;
    }

    @Override
    public void init(Command command) {
        this.command = command;
    }

    @Override
    public void time(int time, GameState gameState) {
        if (gameState == GameState.ACTIVE) {
            e.schedule(() -> {
                log.error("Exit by timer:" + maxTime + " " + timeUnit);
                command.disconnect();
            }, maxTime, timeUnit);

        }
    }
}
