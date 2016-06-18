package org.tagpro.tasc.extras;

import org.json.JSONObject;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.Tiles;
import org.tagpro.tasc.data.*;
import org.tagpro.tasc.starter.Starter;

import java.util.List;
import java.util.Map;

public class GameStateService implements GameSubscriber {
    private volatile boolean connected = false;
    private int id = -1;
    private Tiles map = new Tiles();
    private GameState gameState = GameState.UNKNOWN;
    private PlayerAttribute[] playerAttribute = new PlayerAttribute[9];

    public static GameStateService create(Starter starter) {
        GameStateService ret = new GameStateService();
        starter.addListener(ret);
        return ret;
    }

    @Override
    public void onPreConnect() {
        for (int i = 0; i < playerAttribute.length; i++) {
            playerAttribute[i] = new PlayerAttribute();
        }
    }

    @Override
    public void onConnect() {
        this.connected = true;
    }

    @Override
    public void onDisconnect() {
        this.connected = false;
    }

    @Override
    public void onId(int id) {
        this.id = id;
    }

    @Override
    public void map(List<Tile> tiles) {
        this.map.setTiles(tiles);
    }

    @Override
    public void onMapUpdate(Tile tile) {
        this.map.setTile(tile);
    }

    @Override
    public void time(int time, GameState gameState) {
        //less_todo add track time
        this.gameState = gameState;
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        for (Update update : updates.values()) {
            PlayerAttribute pa = this.playerAttribute[update.getId()];
            JSONObject jsonObject = update.getPlayerUpdateObject();
            if (jsonObject.has("dead")) {
                pa.setDead(jsonObject.optBoolean("dead"));
            }
            if (jsonObject.has("bomb")) {
                pa.setBomb(jsonObject.optBoolean("bomb"));
            }
            if (jsonObject.has("name")) {
                pa.setName(jsonObject.optString("name"));
            }
            if (jsonObject.has("flag")) {
                Object flag = jsonObject.opt("flag");
                pa.setFlag(Flag.resolve(flag));
            }
            if (jsonObject.has("team")) {
                int team = jsonObject.optInt("team");
                pa.setTeam(Team.resolve(team));
            }
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public int getId() {
        return id;
    }

    public Tiles getMap() {
        return map;
    }

    public GameState getGameState() {
        return gameState;
    }

    public PlayerAttribute[] getPlayerAttribute() {
        return playerAttribute;
    }
}
