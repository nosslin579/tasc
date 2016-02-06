package org.tagpro.tasc.listeners;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;
import org.tagpro.tasc.data.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EndListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public EndListener(GamePublisher publisher) {
        super(publisher);
    }


    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
//        log.info("Call length:" + args.length + " args:" + Arrays.toString(args));
        List<Tile> ret = new ArrayList<>();
        try {
            final JSONObject end = (JSONObject) args[0];
            final String groupId = end.optString("groupId");
            final int time = end.optInt("time");
            final String winner = end.getString("winner");
            publisher.end(groupId, time, winner);
        } catch (JSONException e) {
            throw new RuntimeException("Error " + Arrays.toString(args), e);
        }
    }

}
