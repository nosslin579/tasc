package org.tagpro.tasc;

public enum Team {
    BLUE(2), RED(1), SPECTATOR(3), OTHER(0);
    private final int id;

    Team(int i) {
        id = i;
    }

    public static Team resolve(int id) {
        for (Team team : values()) {
            if (team.id == id) {
                return team;
            }
        }
        return OTHER;
    }
}
