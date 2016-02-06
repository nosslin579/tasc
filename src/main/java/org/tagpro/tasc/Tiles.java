package org.tagpro.tasc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.data.Team;
import org.tagpro.tasc.data.Tile;
import org.tagpro.tasc.data.TileType;

import java.util.HashSet;
import java.util.Set;

public class Tiles {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Set<Tile> tiles = new HashSet<>();

    public Tile getTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getMapX() == x && tile.getMapY() == y) {
                return tile;
            }
        }
        return null;
    }

    public void logTiles() {
        String ret = System.lineSeparator();
        for (int y = 0; ; y++) {
            for (int x = 0; ; x++) {
                final Tile tile = getTile(x, y);
                if (tile != null) {
                    ret = ret + tile.getType().getId();
                } else {
                    ret = ret + System.lineSeparator();
                    break;
                }
            }
            if (getTile(0, y) == null) {
                break;
            }
        }
        log.debug(ret);
    }

    public Tile setTile(int x, int y, Object tileId) {
        final TileType tileType = TileType.resolve(tileId);
        if (tileType == TileType.UNKNOWN) {
            log.error("Unknown tile :" + tileId);
        }
        final Tile ret = new Tile(x, y, tileType);
        tiles.add(ret);
        return ret;
    }

    public Tile getFlagTile(Team team) {
        for (Tile tile : tiles) {
            if (tile.getType() == TileType.BLUEFLAG && team == Team.BLUE || tile.getType() == TileType.RED_FLAG && team == Team.RED) {
                return tile;
            } else if (tile.getType() == TileType.YELLOW_FLAG) {
                return null;
            }
        }
        return null;
    }
}

