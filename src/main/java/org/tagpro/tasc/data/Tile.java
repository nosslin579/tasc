package org.tagpro.tasc.data;

public class Tile {
    private final int mapX;
    private final int mapY;
    private final TileType type;

    public Tile(int mapX, int mapY, TileType o) {
        this.mapX = mapX;
        this.mapY = mapY;
        this.type = o;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public TileType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        return mapX == tile.mapX && mapY == tile.mapY;

    }

    @Override
    public int hashCode() {
        int result = mapX;
        result = 31 * result + mapY;
        return result;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "mapX=" + mapX +
                ", mapY=" + mapY +
                ", type=" + type +
                '}';
    }

    public float getX() {
        return mapX * 0.4f;
    }

    public float getY() {
        return mapY * 0.4f;
    }
}
