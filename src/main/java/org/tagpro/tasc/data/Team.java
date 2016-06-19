package org.tagpro.tasc.data;

public enum Team {
    BLUE(2, Flag.BLUE), RED(1, Flag.BLUE), SPECTATOR(3, Flag.NONE), OTHER(0, Flag.NONE);
    private final int id;
    private final Flag flag;

    Team(int i, Flag flag) {
        id = i;
        this.flag = flag;
    }

    public static Team resolve(int id) {
        for (Team team : values()) {
            if (team.id == id) {
                return team;
            }
        }
        return OTHER;
    }

    public Team getOpposite() {
        if (this == BLUE) {
            return RED;
        } else if (this == RED) {
            return BLUE;
        } else {
            return this;
        }
    }

    public TileType getOppositeFlagTileType() {
        if (this == BLUE) {
            return TileType.RED_FLAG;
        } else if (this == RED) {
            return TileType.BLUE_FLAG;
        } else {
            return TileType.YELLOW_FLAG;
        }
    }

    public Flag getFlag() {
        return flag;
    }
}
