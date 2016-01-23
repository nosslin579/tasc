package org.tagpro.tasc;

public enum TileType {
    EMPTY(0, "Empty space"),
    WALL(1, "Square Wall"),
    WALL3(1.1, "45 degree wall (◣)"),
    WALL2(1.2, "45 degree wall (◤)"),
    WALL1(1.3, "45 degree wall (◥)"),
    WALL4(1.4, "45 degree wall (◢)"),
    FLOOR(2, "Regular floor"),
    RED_FLAG(3, "Red flag"),
    RED_FLAG_TAKEN(3.1, "Red flag (taken)"),
    BLUEFLAG(4, "blueflag	Blue flag"),
    BLUEFLAG_TAKEN(4.1, " Blue flag(taken)"),
    SPEEDPAD(5, "Speedpad (active)"),
    SPEEDPAD_INACTIVE(5.1, "Speedpad (inactive)"),
    POWERUP(6, "Powerup subgroup"),
    GRIP(6.1, " grip Jukejuice / grip"),
    ROLLING_BOMB(6.2, " bomb Rolling bomb"),
    TAGPRO(6.3, " tagpro TagPro"),
    SPEED(6.4, " speed Max speed"),
    SPIKE(7, " Spike"),
    BUTTON(8, " Button"),
    INACTIVE_GATE(9, " Inactive gate"),
    GREEN_GATE(9.1, " Green gate"),
    RED_GATE(9.2, " Red gate"),
    BLUE_GATE(9.3, " Blue gate"),
    BOMB(10, " Bomb"),
    BOMB_INACTIVE(10.1, " Inactive bomb"),
    Redteamtile(11, " Red teamtile"),
    Blue_teamtile(12, " Blue teamtile"),
    ACTIVE_PORTAL(13, " Active portal"),
    INACTIVE_PORTAL(13.1, " Inactive portal"),
    SP(14, " Speedpad (red) (active)"),
    A(14.1, "Speedpad (red) (inactive)"),
    B(15, "Speedpad (blue) (active)"),
    C(15.1, "Speedpad (blue) (inactive)"),
    YELLOW_FLAG(16, "yellowflag Yellow flag"),
    YELLOW_FLAG_TAKEN(16.1, "Yellow flag (taken)"),
    f(17, "Red endzone"),
    g(18, "Blue endzone"),
    h(19, "redpotato Red potato"),
    i(19.1, "Red potato taken"),
    j(20, "bluepotato Blue potato"),
    k(20.1, "Blue potato taken"),
    l(21, "yellowpotato Yellow potato"),
    m(21.1, "Yellow potato taken"),
    UNKNOWN(-1,"");

    private final Object id;
    private final String description;

    TileType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    TileType(double id, String description) {
        this.id = id;
        this.description = description;
    }

    public static TileType resolve(Object o) {
        for (TileType t : values()) {
            if (t.id.equals(o)) {
                return t;
            }
        }
        return UNKNOWN;
    }

    public Object getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
