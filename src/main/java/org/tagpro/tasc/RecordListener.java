package org.tagpro.tasc;

import io.socket.emitter.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For debugging only
 */
public class RecordListener implements Emitter.Listener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String recordEventName;
    public static final String SEPARATOR1 = "-sdfkwef1nal4viv6al8sdk9fslkd6jf4sl3d4kfew4kn4a-";
    public static final String SEPARATOR2 = " ";

    public RecordListener(String recordEventName) {
        this.recordEventName = recordEventName;
    }

    @Override
    public void call(Object... args) {
        String line = "i" + SEPARATOR2 + System.nanoTime() + SEPARATOR2 + recordEventName;
        int i = 0;
        for (Object arg : args) {
            line = line + SEPARATOR2 + args[i++];
        }
        log.debug(line);
    }
}