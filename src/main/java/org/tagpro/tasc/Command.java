package org.tagpro.tasc;

import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Command {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Socket socket;
    private final GamePublisher publisher;
    private AtomicInteger keyPressCounter = new AtomicInteger(0);

    public Command(Socket socket, GamePublisher publisher) {
        this.socket = socket;
        this.publisher = publisher;
    }

    public void key(Key key, KeyAction keyAction) {
        try {
            final JSONObject keyObject = new JSONObject();
            int count = keyPressCounter.getAndIncrement();
            keyObject.put("t", count);
            keyObject.put("k", key.getCommand());
            socket.emit(keyAction.getCommand(), keyObject);
            publisher.keyPressed(key, keyAction, count);
        } catch (JSONException e) {
            throw new RuntimeException("Key failed", e);
        }
    }

    public void chat(String message, boolean toAll) {
        try {
            final JSONObject chatObject = new JSONObject();
            chatObject.put("message", message);
            chatObject.put("toAll", toAll);
            socket.emit("chat", chatObject);
        } catch (JSONException e) {
            throw new RuntimeException("Chat failed", e);
        }
    }

    public void name(String name) {
        socket.emit("name", name);
    }

    public void disconnect() {
        socket.disconnect();
        publisher.disconnect();
    }
}
