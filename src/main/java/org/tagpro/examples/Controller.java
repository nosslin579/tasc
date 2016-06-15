package org.tagpro.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.ConcurrentSetKeyState;
import org.tagpro.tasc.KeyState;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;

public class Controller implements Command.KeyObserver {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Command command;

    private final ConcurrentSetKeyState keyState = new ConcurrentSetKeyState();

    public Controller(Command command) {
        this.command = command;
    }

    public static Controller create(Command command) {
        Controller ret = new Controller(command);
        command.addObserver(ret);
        return ret;
    }


    @Override
    public void keyChanged(Key key, KeyAction keyAction, int count) {
        log.info("Key pressed:" + key + " " + keyAction);
        keyState.setKey(key, keyAction);
    }

    public void key(Key key, KeyAction keyAction) {
        if (keyState.getStateFor(key) == keyAction) {
            log.warn("Key already at state. " + key + "=" + keyAction);
        } else {
            if (keyAction == KeyAction.KEYDOWN && keyState.getStateFor(key.getOpposite()) == KeyAction.KEYDOWN) {
                //release opposite key
                command.key(key.getOpposite(), KeyAction.KEYUP);
            }
            command.key(key, keyAction);
        }
    }

    public boolean isPushed(Key key) {
        return keyState.isPushed(key);
    }

    public KeyState getKeyState() {
        return keyState;
    }
}
