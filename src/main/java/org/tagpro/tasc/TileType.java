package org.tagpro.tasc;

public enum TileType {
    EMPTY(0, "Empty space"),
    WALL(1, "Square Wall", '◼'),
    WALL3(1.1, "45 degree wall (◣)", '◣'),
    WALL2(1.2, "45 degree wall (◤)", '◤'),
    WALL1(1.3, "45 degree wall (◥)", '◥'),
    WALL4(1.4, "45 degree wall (◢)", '◢'),
    FLOOR(2, "Regular floor"),
    RED_FLAG(3, "Red flag", '⚑'),
    RED_FLAG_TAKEN(3.1, "Red flag (taken)", '⚐'),
    BLUEFLAG(4, "Blue flag", '⚑'),
    BLUEFLAG_TAKEN(4.1, "Blue flag(taken)", '⚐'),
    SPEEDPAD(5, "Speedpad (active)", '✣'),
    SPEEDPAD_INACTIVE(5.1, "Speedpad (inactive)"),
    POWERUP(6, "Powerup subgroup",'Ⓟ'),
    GRIP(6.1, "Jukejuice / grip", 'Ⓖ'),
    ROLLING_BOMB(6.2, "Rolling bomb", 'Ⓑ'),
    TAGPRO(6.3, "TagPro",'Ⓣ'),
    SPEED(6.4, "Max speed", 'Ⓢ'),
    SPIKE(7, "Spike", '✸'),
    BUTTON(8, "Button", '⊚'),
    GATE_INACTIVE(9, "Inactive gate"),
    GATE_GREEN(9.1, "Green gate", '#'),
    GATE_RED(9.2, "Red gate", '#'),
    GATE_BLUE(9.3, "Blue gate", '#'),
    BOMB(10, "Bomb", '❂'),
    BOMB_INACTIVE(10.1, "Inactive bomb"),
    RED_TEAM_TILE(11, "Red teamtile", 'R'),
    BLUE_TEAM_TILE(12, "Blue teamtile", 'B'),
    PORTAL_ACTIVE(13, "Active portal", 'P'),
    PORTAL_INACTIVE(13.1, "Inactive portal"),
    SPEEDPAD_RED_ACTIVE(14, "Speedpad (red) (active)", '✣'),
    SPEEDPAD_RED_INACTIVE(14.1, "Speedpad (red) (inactive)"),
    SPEEDPAD_BLUE_ACTIVE(15, "Speedpad (blue) (active)", '✣'),
    SPEEDPAD_BLUE_INACTIVE(15.1, "Speedpad (blue) (inactive)"),
    YELLOW_FLAG(16, "Yellow flag", '⚑'),
    YELLOW_FLAG_TAKEN(16.1, "Yellow flag (taken)", '⚐'),
    RED_ENDZONE(17, "Red endzone", 'r'),
    BLUE_ENDZONE(18, "Blue endzone", 'b'),
    RED_POTATO(19, "Red potato"),
    RED_POTATO_TAKEN(19.1, "Red potato taken"),
    BLUE_POTATO(20, "Blue potato"),
    BLUE_POTATO_TAKEN(20.1, "Blue potato taken"),
    YELLOW_POTATO(21, "Yellow potato"),
    YELLOW_POTATO_TAKEN(21.1, "Yellow potato taken"),
    UNKNOWN(-1, "Unknown");

    private final Number id;
    private final String description;
    private char symbol;

    TileType(Number id, String description, char symbol) {
        this.id = id;
        this.description = description;
        this.symbol = symbol;
    }

    TileType(Number id, String description) {
        this(id, description, ' ');
    }

    public static TileType resolve(Number n) {
        for (TileType t : values()) {
            if (t.id.equals(n)) {
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
