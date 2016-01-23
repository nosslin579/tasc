package org.tagpro.tasc.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

public class ConnectListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ConnectListener(GamePublisher publisher) {
        super(publisher);
    }

    @Override
    public void event(GamePublisher publisher, Object... args) {
        publisher.connect();
    }
}
