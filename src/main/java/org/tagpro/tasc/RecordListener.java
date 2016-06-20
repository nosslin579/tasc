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
    public static final String SEPARATOR = " ";

    public RecordListener(String recordEventName, String separator) {
        this.recordEventName = recordEventName;
    }

    public RecordListener(String recordEventName) {
        this(recordEventName, SEPARATOR);
    }


    @Override
    public void call(Object... args) {
        String line = recordEventName;
        for (Object arg : args) {
            line = line + SEPARATOR + arg;
        }
        log.debug(line);
    }
}