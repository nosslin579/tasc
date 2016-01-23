package org.tagpro.tasc.listeners;

import io.socket.emitter.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class LogEmitterListener implements Emitter.Listener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String prefix;

    public LogEmitterListener(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void call(Object... args) {
        log.debug("Event:" + prefix + " args:" + Arrays.toString(args));
    }
}
