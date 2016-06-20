package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.DaemonThreadFactory;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.GameState;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyChange;
import org.tagpro.tasc.data.KeyState;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FixedMovement implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final KeyChange[] keyChanges;
    private final Command command;

    public FixedMovement(Command command, KeyChange... keyChanges) {
        this.keyChanges = keyChanges;
        this.command = command;
    }

    public static FixedMovement createRightThenLeftMovement(Command command) {
        return new FixedMovement(command, new KeyChange(Key.RIGHT, KeyState.KEYDOWN, 0), new KeyChange(Key.RIGHT, KeyState.KEYUP, 3000), new KeyChange(Key.LEFT, KeyState.KEYDOWN, 3000));
    }

    @Override
    public void time(int time, GameState state) {
        if (state == GameState.ACTIVE) {
            log.info("Game active ######################################################################################################################################");
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory(getClass().getSimpleName()));
            for (KeyChange integerKeyEntry : keyChanges) {
                executor.schedule(() -> command.key(integerKeyEntry.getKey(), integerKeyEntry.getKeyState()), integerKeyEntry.getStep(), TimeUnit.MILLISECONDS);
            }
        }
    }
}
