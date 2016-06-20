package org.tagpro.tasc;

import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Command {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Socket socket;
    private AtomicInteger keyPressCounter = new AtomicInteger(0);
    private List<KeyObserver> observers = new ArrayList<>();

    public Command() {
    }

    public void key(Key key, KeyState keyState) {
        try {
            final JSONObject keyObject = new JSONObject();
            int count = keyPressCounter.getAndIncrement();
            keyObject.put("t", count);
            keyObject.put("k", key.getCommand());
            socket.emit(keyState.getCommand(), keyObject);
            for (KeyObserver observer : observers) {
                observer.keyChanged(key, keyState, count);
            }
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
        log.info("Disconnecting");
        socket.disconnect();
    }

    public void addObserver(KeyObserver observer) {
        this.observers.add(observer);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public interface KeyObserver {
        void keyChanged(Key key, KeyState keyState, int count);
    }
}
