package org.tagpro.tasc.data;

import org.json.JSONObject;

import java.util.List;

public class Update {
    private final int id;
    private final BallUpdate ballUpdate;
    private final List<KeyUpdate> keyUpdate;
    private final JSONObject playerUpdateObject;

    public Update(int id, BallUpdate ballUpdate, List<KeyUpdate> keyUpdate, JSONObject playerUpdateObject) {
        this.id = id;
        this.ballUpdate = ballUpdate;
        this.keyUpdate = keyUpdate;
        this.playerUpdateObject = playerUpdateObject;
    }

    public int getId() {
        return id;
    }

    public BallUpdate getBallUpdate() {
        return ballUpdate;
    }

    public List<KeyUpdate> getKeyUpdate() {
        return keyUpdate;
    }

    public JSONObject getPlayerUpdateObject() {
        return playerUpdateObject;
    }
}
