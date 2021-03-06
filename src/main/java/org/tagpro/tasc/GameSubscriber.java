package org.tagpro.tasc;

import org.tagpro.tasc.data.GameState;
import org.tagpro.tasc.data.Tile;
import org.tagpro.tasc.data.Update;

import java.util.List;
import java.util.Map;

public interface GameSubscriber {

    default void onConnect() {
    }

    default void onException(Exception e, Object[] args) {
    }

    /**
     * Fired each time p is sent to client
     *
     * @param step    the current step
     * @param updates Map of each player where key is id.
     */
    default void onUpdate(int step, Map<Integer, Update> updates) {
    }

    default void onId(int id) {
    }

    default void onDisconnect() {
    }

    default void map(List<Tile> tiles) {
    }

    default void time(int time, GameState gameState) {
    }

    default void banned() {
    }

    default void chat(String to, String from, String message, boolean mod, String color) {
    }

    default void spawn() {
    }

    default void end(String groupId, int time, String winner) {
    }

    default void full() {
    }

    default void score(String score) {
    }

    default void onPreConnect() {
    }

    default void onMapUpdate(Tile tile) {
    }
}
