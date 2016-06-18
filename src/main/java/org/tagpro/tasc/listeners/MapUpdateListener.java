package org.tagpro.tasc.listeners;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;
import org.tagpro.tasc.data.Tile;
import org.tagpro.tasc.data.TileType;

public class MapUpdateListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());


    public MapUpdateListener(GamePublisher publisher) {
        super(publisher);
    }

    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
        final JSONArray tiles = (JSONArray) args[0];
        for (int i = 0; i < tiles.length(); i++) {
            final JSONObject jsonTile = tiles.getJSONObject(i);
            final int x = jsonTile.getInt("x");
            final int y = jsonTile.getInt("y");
            final Object tileId = jsonTile.get("v");
            publisher.mapUpdate(new Tile(x, y, TileType.resolve(tileId)));
        }
    }

}
