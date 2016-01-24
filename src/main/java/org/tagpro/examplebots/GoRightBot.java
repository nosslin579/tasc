package org.tagpro.examplebots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.*;
import org.tagpro.tasc.extras.CommandFix;
import org.tagpro.tasc.extras.MaxTime;
import org.tagpro.tasc.listeners.GameState;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Bot that goes right on test map and then leaves.
 */
public class GoRightBot implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Command command;
    private GameState state;

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        GoRightBot bot = new GoRightBot();
        Starter s = new Starter();
        s.addListener(bot);
        s.addListener(new MaxTime(7, TimeUnit.SECONDS));
        s.addListener(new CommandFix());
        s.start();
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        for (Update update : updates.values()) {
            if (update.getPlayerUpdateObject().optBoolean("dead", false) && state == GameState.ACTIVE) {
                log.info("Successfully killed myself");
                command.disconnect();
                System.exit(0);
            }
        }
    }

    @Override
    public void time(int time, GameState state) {
        this.state = state;
        if (state == GameState.ACTIVE) {
            log.info("Game active #################################################################################################################");
            command.key(Key.RIGHT, KeyAction.KEYDOWN);
        }
    }

    @Override
    public void init(Command command) {
        this.command = command;
    }

    @Override
    public void chat(String to, String from, String message, boolean mod, String color) {
        if (message.equals("pls")) {
            command.disconnect();
        }
    }
}
