package org.tagpro.tasc.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

public class FullListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public FullListener(GamePublisher publisher) {
        super(publisher);
    }

    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
        publisher.full();
    }
}
