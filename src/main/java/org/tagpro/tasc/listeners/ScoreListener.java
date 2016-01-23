package org.tagpro.tasc.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

import java.util.Arrays;

public class ScoreListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ScoreListener(GamePublisher publisher) {
        super(publisher);
    }

    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
        publisher.score(Arrays.toString(args));
    }
}
