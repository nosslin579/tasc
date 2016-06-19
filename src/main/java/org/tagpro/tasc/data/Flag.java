package org.tagpro.tasc.data;

import java.util.Objects;

public enum Flag {
    NONE(null, TileType.UNKNOWN), RED(1, TileType.RED_FLAG), BLUE(2, TileType.BLUE_FLAG), YELLOW(3, TileType.YELLOW_FLAG);

    private final Object id;
    private final TileType tileType;

    Flag(Object id, TileType tileType) {
        this.id = id;
        this.tileType = tileType;
    }

    public static Flag resolve(Object id) {
        for (Flag flag : values()) {
            if (Objects.equals(id, flag.id)) {
                return flag;
            }
        }
        throw new IllegalArgumentException("No such flag:" + id + " class:" + (id == null ? "" : id.getClass()));
    }

    public TileType getTileType() {
        return tileType;
    }
}
