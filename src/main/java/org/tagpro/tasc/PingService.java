package org.tagpro.tasc;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PingService implements Runnable, Emitter.Listener, GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Socket socket;
    /**
     * Key is pingId and value is time ping sent in ns
     */
    private final Map<Integer, Long> pingSentLog = new HashMap<>();

    private int pingIdCounter = 0;
    private long currentPing = 0;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory("PingService"));


    public PingService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void onConnect() {
        socket.on("pr", this);
        scheduledExecutorService.scheduleAtFixedRate(this, 2, 2, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        log.debug("Ping id:" + pingIdCounter++);
        try {
            final JSONObject pingObject = new JSONObject();
            pingObject.put("id", pingIdCounter);
            pingObject.put("c", currentPing);
            pingSentLog.put(pingIdCounter, System.nanoTime());
            socket.emit("p", pingObject);
        } catch (JSONException e1) {
            throw new RuntimeException("Ping failed", e1);
        }
    }

    @Override
    public void call(Object... args) {
        log.debug("Call length:" + args.length + " args:" + Arrays.toString(args));
        final Integer pingId = (Integer) args[0];
        final Long sent = pingSentLog.get(pingId);
        pingSentLog.remove(pingId);

        final long ping = System.nanoTime() - sent;
        this.currentPing = TimeUnit.NANOSECONDS.toMillis(ping);

        log.info("Ping is:" + currentPing);
    }

    public long getCurrentPing() {
        return currentPing;
    }
}
