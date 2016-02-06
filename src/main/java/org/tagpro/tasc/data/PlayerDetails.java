package org.tagpro.tasc.data;

public class PlayerDetails {
    private final int id;
    private final Team team;
    private final String name;
    private final int flag;

    public PlayerDetails(int id, Team team, String name, int flag) {
        this.id = id;
        this.team = team;
        this.name = name;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public int getFlag() {
        return flag;
    }
}
