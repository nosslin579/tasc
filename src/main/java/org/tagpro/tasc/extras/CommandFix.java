package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.Key;
import org.tagpro.tasc.KeyAction;
import org.tagpro.tasc.listeners.GameState;

/**
 * The first key press never reach server for unknown reasons. This listener will send space key to circumvent that.
 */
public class CommandFix implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Command command;

    @Override
    public void init(Command command) {
        this.command = command;
    }

    @Override
    public void time(int time, GameState gameState) {
        if (gameState == GameState.NOT_YET_STARTED) {
            command.key(Key.SPACE, KeyAction.KEYDOWN);
        }
    }
}
