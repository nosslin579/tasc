package org.tagpro.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.ConcurrentSetKeyState;
import org.tagpro.tasc.KeyState;
import org.tagpro.tasc.data.BallUpdate;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;
import org.tagpro.tasc.data.Tile;

import java.awt.geom.Point2D;

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

    public boolean goTo(Tile tile, BallUpdate update) {
        if (tile == null) {
            stop();
            return false;
        }
        Point2D.Float distance = goTo(tile.getX(), tile.getY(), update);
        return Math.abs(distance.x) < 0.5f && Math.abs(distance.y) < 0.5f;
    }

    public void stop() {
        for (Key key : Key.values()) {
            key(key, KeyAction.KEYUP);
        }
    }

    public Point2D.Float goTo(float destinationX, float destinationY, BallUpdate update) {
        float distanceX = destinationX - update.getRx();
        float distanceY = destinationY - update.getRy();

        log.info("Setting distance X:" + distanceX + " Y:" + distanceY + " " + update);

        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            //horizontal primary
            Key primary = distanceX > 0 ? Key.RIGHT : Key.LEFT;
            key(primary, KeyAction.KEYDOWN);

            //vertical secondary
            float desiredLy = (distanceY / Math.abs(distanceX)) * Math.abs(update.getLx());
            Key secondary = desiredLy > 0 ? Key.DOWN : Key.UP;
            KeyAction action = desiredLy > update.getLy() ? KeyAction.KEYUP : KeyAction.KEYDOWN;
            key(secondary, action);
        } else {
            //vertical primary
            Key primary = distanceY > 0 ? Key.DOWN : Key.UP;
            key(primary, KeyAction.KEYDOWN);

            //horizontal secondary
            float desiredLx = (distanceX / Math.abs(distanceY)) * Math.abs(update.getLy());
            Key secondary = desiredLx > 0 ? Key.RIGHT : Key.LEFT;
            KeyAction action = desiredLx > update.getLx() ? KeyAction.KEYUP : KeyAction.KEYDOWN;
            key(secondary, action);
        }

        return new Point2D.Float(distanceX, distanceY);
    }
}
