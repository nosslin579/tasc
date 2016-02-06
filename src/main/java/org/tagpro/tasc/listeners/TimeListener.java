package org.tagpro.tasc.listeners;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;
import org.tagpro.tasc.data.GameState;

public class TimeListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public TimeListener(GamePublisher publisher) {
        super(publisher);
    }

    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
        //{"time":720000,"state":1}
        JSONObject timeObject = (JSONObject) args[0];
        int time = timeObject.getInt("time");
        int stateId = timeObject.getInt("state");
        GameState gameState = GameState.resolve(stateId);
        publisher.time(time, gameState);
    }

}
