package org.tagpro.tasc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.data.GameState;
import org.tagpro.tasc.data.Tile;
import org.tagpro.tasc.data.Update;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class GamePublisher {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private GameSubscriber[] subscribers;
    private final Executor internal;


    public GamePublisher(Executor internal) {
        this.internal = internal;
    }

    public Executor getExecutor() {
        return internal;
    }

    public void connect() {
        log.debug("connect");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.onConnect();
        }
    }

    public void exception(Exception e, Object[] args) {
        log.debug("exception");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.onException(e, args);
        }
    }

    public void update(int step, Map<Integer, Update> ret) {
        log.debug("update");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.onUpdate(step, ret);
        }
    }

    public void id(int id) {
        log.debug("id");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.onId(id);
        }
    }

    public void disconnect() {
        log.debug("disconnect");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.onDisconnect();
        }
    }

    public void banned() {
        log.debug("banned");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.banned();
        }

    }

    public void chat(String to, String from, String message, boolean mod, String color) {
        log.debug("chat");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.chat(to, from, message, mod, color);
        }
    }

    public void spawn() {
        log.debug("spawn");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.spawn();
        }
    }

    public void time(int time, GameState gameState) {
        log.debug("time");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.time(time, gameState);
        }
    }

    public void map(List<Tile> tiles) {
        log.debug("map");
        for (GameSubscriber subscriber : subscribers) {
            subscriber.map(tiles);
        }
    }

    public void end(String groupId, int time, String winner) {
        for (GameSubscriber subscriber : subscribers) {
            subscriber.end(groupId, time, winner);
        }

    }

    public void full() {
        for (GameSubscriber subscriber : subscribers) {
            subscriber.full();
        }
    }

    public void score(String score) {
        for (GameSubscriber subscriber : subscribers) {
            subscriber.score(score);
        }
    }

    public void setSubscribers(List<GameSubscriber> subscribers) {
        this.subscribers = subscribers.stream().toArray(GameSubscriber[]::new);
    }

    public void preConnect() {
        for (GameSubscriber subscriber : subscribers) {
            subscriber.onPreConnect();
        }
    }

    public void mapUpdate(Tile tile) {
        for (GameSubscriber subscriber : subscribers) {
            subscriber.onMapUpdate(tile);
        }
    }
}
