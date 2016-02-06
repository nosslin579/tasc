package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.GameState;
import org.tagpro.tasc.data.Update;

import java.util.Map;

public class ExitWhenDead implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Command command;
    private GameState state;

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
    }

    @Override
    public void init(Command command) {
        this.command = command;
    }
}
