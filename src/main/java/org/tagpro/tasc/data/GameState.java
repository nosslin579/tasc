package org.tagpro.tasc.data;

public enum GameState {
    ACTIVE(1), ENDED(2), NOT_YET_STARTED(3);
    private final int stateId;

    GameState(int stateId) {
        this.stateId = stateId;
    }

    public static GameState resolve(int stateId) {
        for (GameState gameState : values()) {
            if (stateId == gameState.stateId) {
                return gameState;
            }
        }
        throw new IllegalArgumentException("No such state" + stateId);
    }
}
