package org.tagpro.tasc;

import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStateCheckCommand extends Command {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private KeyState keyState = new ConcurrentSetKeyState();

    public KeyStateCheckCommand(Socket socket, GamePublisher publisher) {
        super(socket, publisher);
    }

    public void key(Key key, KeyAction keyAction) {
        boolean changed = keyState.setKey(key, keyAction);
        if (changed) {
            super.key(key, keyAction);
            if (keyAction.getBooleanValue() && keyState.isPushed(key.getOpposite())) {
                key(key.getOpposite(), KeyAction.KEYUP);
            }
        }
    }
}
