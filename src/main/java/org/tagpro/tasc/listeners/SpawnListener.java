package org.tagpro.tasc.listeners;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

public class SpawnListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public SpawnListener(GamePublisher game) {
        super(game);
    }

    @Override
    public void event(GamePublisher publisher, Object... args) throws JSONException {
        //todo add args
        //{"t":2,"w":3000,"x":1760.0000000000002,"y":240.00000000000003}
        publisher.spawn();
    }
}
