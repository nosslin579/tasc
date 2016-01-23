package org.tagpro.tasc;

import org.tagpro.tasc.listeners.GameState;

import java.util.List;
import java.util.Map;

public interface GameSubscriber {

    default void onConnect() {
    }

    default void onException(Exception e, Object[] args) {
    }

    default void onUpdate(int step, Map<Integer, Update> ret) {
    }

    default void onId(int id) {
    }

    default void onDisconnect() {
    }

    default void map(List<Tile> tiles) {
    }

    default void keyPressed(Key key, KeyAction keyAction, int count) {
    }

    default void time(int time, GameState gameState) {
    }

    default void disconnect() {
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

    default void init(Command command) {
    }
}
