package org.tagpro.tasc.data;

public enum Flag {
    NONE(0, TileType.UNKNOWN), RED(1, TileType.RED_FLAG), BLUE(2, TileType.BLUE_FLAG), YELLOW(3, TileType.YELLOW_FLAG);

    private final int id;
    private final TileType tileType;

    Flag(int id, TileType tileType) {
        this.id = id;
        this.tileType = tileType;
    }

    public static Flag resolve(int id) {
        for (Flag flag : values()) {
            if (id == flag.id) {
                return flag;
            }
        }
        throw new IllegalArgumentException("No such flag:" + id);
    }

    public TileType getTileType() {
        return tileType;
    }
}
