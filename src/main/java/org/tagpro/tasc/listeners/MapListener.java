package org.tagpro.tasc.listeners;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;
import org.tagpro.tasc.Tile;
import org.tagpro.tasc.TileType;

import java.util.ArrayList;
import java.util.List;

public class MapListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());


    public MapListener(GamePublisher publisher) {
        super(publisher);
    }

    @Override
    void event(GamePublisher publisher, Object[] args) throws Exception {
        final JSONObject mapObject = (JSONObject) args[0];
        List<Tile> ret = new ArrayList<>();
        final JSONArray tilesArray = mapObject.getJSONArray("tiles");
        for (int x = 0; x < tilesArray.length(); x++) {
            final JSONArray jsonArrayx = tilesArray.getJSONArray(x);
            for (int y = 0; y < jsonArrayx.length(); y++) {
                final Object tileId = jsonArrayx.get(y);
                ret.add(new Tile(x, y, TileType.resolve(tileId)));
            }
        }
        publisher.map(ret);
    }

}
