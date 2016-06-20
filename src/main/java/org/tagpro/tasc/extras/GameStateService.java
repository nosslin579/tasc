package org.tagpro.tasc.extras;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GameMap;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.*;
import org.tagpro.tasc.starter.Starter;

import java.util.List;
import java.util.Map;

public class GameStateService implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private volatile boolean connected = false;
    private Integer id = null;
    private GameMap map = new GameMap();
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
            if (jsonObject.has("team")) {
                int team = jsonObject.optInt("team");
                pa.setTeam(Team.resolve(team));
            }
            if (jsonObject.has("flag")) {
                Object flag = jsonObject.optJSONObject("flag");
                pa.setFlag(Flag.resolve(flag));
                log.info("Setting flag " + pa);
            }
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public Integer getId() {
        return id;
    }

    public GameMap getMap() {
        return map;
    }

    public GameState getGameState() {
        return gameState;
    }

    public PlayerAttribute[] getPlayerAttribute() {
        return playerAttribute;
    }

    public PlayerAttribute getSelfPlayerAttribute() {
        return playerAttribute[id];
    }

    public boolean hasAnyTeamMemberOppositeFlag() {
        for (PlayerAttribute p : playerAttribute) {
            if (p.hasFlag() && p.getTeam() == getSelfPlayerAttribute().getTeam()) {
                return true;
            }
        }
        return false;
    }
}
