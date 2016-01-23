package org.tagpro.tasc.listeners;

import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetCookieEmitterListener implements Emitter.Listener {
    private final Map<String, String> setCookie = new HashMap<String, String>();

    public SetCookieEmitterListener(String tagProId) {
        this.setCookie.put("tagpro", tagProId);
    }

    @Override
    public void call(Object... args) {
        Transport transport = (Transport) args[0];

        transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                @SuppressWarnings("unchecked")
                Map<String, List<String>> headers = (Map<String, List<String>>) args[0];
                List<String> cookie = headers.get("Cookie");
                if (cookie == null) {
                    cookie = new ArrayList<String>();
                    headers.put("Cookie", cookie);
                }
                for (Map.Entry<String, String> s : setCookie.entrySet()) {
                    cookie.add(s.getKey() + "=" + s.getValue() + ";");
                }
            }
        });

        transport.on(Transport.EVENT_RESPONSE_HEADERS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                @SuppressWarnings("unchecked")
                Map<String, List<String>> headers = (Map<String, List<String>>) args[0];
                final List<String> setCookieValues = headers.getOrDefault("Set-Cookie", new ArrayList<String>());
                for (String s : setCookieValues) {
                    final String[] split = s.split("=");
                    setCookie.put(split[0], split[1]);
                }
            }
        });
    }
}
