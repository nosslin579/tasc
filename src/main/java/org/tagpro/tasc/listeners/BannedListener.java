package org.tagpro.tasc.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

import java.util.Arrays;

public class BannedListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public BannedListener(GamePublisher game) {
        super(game);
    }


    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
        log.info("Call length:" + args.length + " args:" + Arrays.toString(args));
        publisher.banned();
    }
}
