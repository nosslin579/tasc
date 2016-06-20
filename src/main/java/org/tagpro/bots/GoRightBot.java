package org.tagpro.bots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.GameState;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyState;
import org.tagpro.tasc.extras.CommandFix;
import org.tagpro.tasc.extras.ExitWhenDead;
import org.tagpro.tasc.extras.MaxTime;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class GoRightBot implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Command command;

    public GoRightBot(Command command) {
        this.command = command;
    }

    @Override
    public void time(int time, GameState state) {
        if (state == GameState.ACTIVE) {
            log.info("Game active ########################################################################");
            command.key(Key.RIGHT, KeyState.KEYDOWN);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        Starter s = new Starter("GoRightBotLeaveWhenDead");
        s.addListener(new ExitWhenDead(s.getCommand()));
        s.addListener(new MaxTime(s.getCommand(), 7, TimeUnit.SECONDS));
        s.addListener(new CommandFix(s.getCommand()));
        s.addListener(new GoRightBot(s.getCommand()));
        s.setRecordEvents(true);
        s.start();
    }
}
