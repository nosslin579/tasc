package org.tagpro.tasc.starter;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.listeners.LogEmitterListener;

import java.util.Arrays;

public class Joiner {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Socket socket;
    private String foundWorldUrl;
    private String[] logListenerEvents = new String[]{"CreatingWorld", "Disabled", "FoundWorld", "Full", "GroupLeaderNotInTheJoiner", "port", "TrollControl", "WaitForEligibility", "WaitingForMembers"};

    public Joiner(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        socket.connect();
        socket.on("FoundWorld", new FoundWorldListener(this));
        for (String logListenerEvent : logListenerEvents) {
            socket.on(logListenerEvent, new LogEmitterListener(logListenerEvent));
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Disconnecting join socket");
            socket.disconnect();
        }));
    }

    public void stop() {
        socket.disconnect();
        socket.off();
    }

    public String getFoundWorldUrl() throws InterruptedException {
        synchronized (this) {
            wait();
        }
        return foundWorldUrl;
    }

    private class FoundWorldListener implements Emitter.Listener {
        private final Joiner joiner;

        public FoundWorldListener(Joiner joiner) {
            this.joiner = joiner;
        }

        @Override
        public void call(Object... args) {
            try {
                final JSONObject jsonObject = (JSONObject) args[0];
                joiner.foundWorldUrl = jsonObject.getString("url");
                synchronized (joiner) {
                    joiner.notifyAll();
                }
            } catch (JSONException e) {
                throw new RuntimeException("Parsing failed:" + Arrays.toString(args), e);
            }
        }
    }
}