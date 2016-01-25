package org.tagpro.tasc.listeners;

import io.socket.emitter.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

import java.util.Arrays;

public abstract class TagProServerListener implements Emitter.Listener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final GamePublisher publisher;

    public TagProServerListener(GamePublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public final void call(Object... args) {
        log.debug("Call length:" + args.length + " args:" + Arrays.toString(args));
        publisher.getExecutor().execute(() -> {
            try {
                event(publisher, args);
            } catch (Exception e) {
                publisher.exception(e, args);
            }
        });
    }

    abstract void event(GamePublisher publisher, Object[] args) throws Exception;
}
