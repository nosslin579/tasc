package org.tagpro.tasc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.data.Team;
import org.tagpro.tasc.data.Tile;
import org.tagpro.tasc.data.TileType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMap {

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

    public void setTile(Tile tile) {
        boolean didNotContainTile = tiles.add(tile);
        if (didNotContainTile) {
            log.warn("A new tile was added:" + tile);
        }
    }

    public Tile getFlagTile(Team team) {
        for (Tile tile : tiles) {
            if (tile.getType() == TileType.BLUE_FLAG && team == Team.BLUE || tile.getType() == TileType.RED_FLAG && team == Team.RED) {
                return tile;
            } else if (tile.getType() == TileType.YELLOW_FLAG) {
                return null;
            }
        }
        return null;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles.clear();
        this.tiles.addAll(tiles);
    }

    public Tile getTile(TileType tileType) {
        for (Tile tile : tiles) {
            if (tile.getType() == tileType) {
                return tile;
            }
        }
        return null;
    }
}

