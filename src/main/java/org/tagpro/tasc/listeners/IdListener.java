package org.tagpro.tasc.listeners;

import io.socket.emitter.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

public class IdListener extends TagProServerListener implements Emitter.Listener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public IdListener(GamePublisher publisher) {
        super(publisher);
    }

    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
        publisher.id((int) args[0]);
    }
}
