package org.tagpro.tasc;

import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class KeyStateCheckCommand extends Command {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Set<Key> keyState = new HashSet<>();//ConcurrentHashMap.newKeySet();

    public KeyStateCheckCommand(Socket socket, GamePublisher publisher) {
        super(socket, publisher);
    }

    public void key(Key key, KeyAction keyAction) {
        synchronized (this) {
            boolean changed = keyAction.getBooleanValue() ? keyState.add(key) : keyState.remove(key);
            if (changed) {
                super.key(key, keyAction);
            }
            if (keyState.contains(Key.DOWN) && keyState.contains(Key.UP) || keyState.contains(Key.LEFT) && keyState.contains(Key.RIGHT)) {
                log.warn("Illegal key state:" + keyState);
            }
        }
    }
}
