package org.tagpro.tasc.listeners;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;

import java.util.Arrays;

public class ChatListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ChatListener(GamePublisher game) {
        super(game);
    }

    @Override
    public void event(GamePublisher publisher, Object... args) throws JSONException {
        log.debug("Call length:" + args.length + " args:" + Arrays.toString(args));
        try {
            final JSONObject chatObject = (JSONObject) args[0];
            final String from = chatObject.getString("from");
            final String message = chatObject.getString("message");
            final String to = chatObject.getString("to");
            final String color = chatObject.optString("c");
            final boolean mod = chatObject.optBoolean("mod");
            publisher.chat(to, from, message, mod, color);
        } catch (JSONException e) {
            throw new RuntimeException("Error chat", e);
        }

    }
}
