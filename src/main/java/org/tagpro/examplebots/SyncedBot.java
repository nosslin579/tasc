package org.tagpro.examplebots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.*;
import org.tagpro.tasc.extras.MaxTime;
import org.tagpro.tasc.extras.SyncObserver;
import org.tagpro.tasc.extras.SyncService;
import org.tagpro.tasc.listeners.GameState;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class SyncedBot implements GameSubscriber, SyncObserver {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Command command;

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        SyncedBot bot = new SyncedBot();
        Starter s = new Starter();
        s.addListener(new SyncService(bot, s.getExecutor()));
        s.addListener(new MaxTime(2, TimeUnit.SECONDS));
        s.addListener(bot);
        s.start();
    }


    @Override
    public void time(int time, GameState state) {
        if (state == GameState.ACTIVE) {
            log.info("Game active ######################################################################################################################################");
            command.key(Key.RIGHT, KeyAction.KEYDOWN);
        }
    }

    @Override
    public void init(Command command) {
        log.info("Init bot");
        this.command = command;
    }

    @Override
    public void currentLocation(int step, PlayerState self) {
        log.info("Estimating player at " + step + " " + self);
    }
}
