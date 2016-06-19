package org.tagpro.tasc.data;

public class PlayerAttribute {
    private boolean dead = true;
    private Flag flag = Flag.NONE;
    private boolean bomb = false;
    private String name = "";
    private Team team = Team.OTHER;

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public boolean hasFlag() {
        return flag == Flag.RED || flag == Flag.BLUE;
    }

    @Override
    public String toString() {
        return "PlayerAttribute{" +
                "dead=" + dead +
                ", flag=" + flag +
                ", bomb=" + bomb +
                ", name='" + name + '\'' +
                ", team=" + team +
                '}';
    }
}
