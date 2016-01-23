package org.tagpro.tasc.listeners;

public class BallUpdate {
    private final int id;
    private final float rx;
    private final float ry;
    private final float lx;
    private final float ly;
    private final float a;
    private final float ra;

    public BallUpdate(int id, float rx, float ry, float lx, float ly, float a, float ra) {
        this.id = id;
        this.rx = rx;
        this.ry = ry;
        this.lx = lx;
        this.ly = ly;
        this.a = a;
        this.ra = ra;
    }

    public int getId() {
        return id;
    }

    public float getRx() {
        return rx;
    }

    public float getRy() {
        return ry;
    }

    public float getLx() {
        return lx;
    }

    public float getLy() {
        return ly;
    }

    public float getA() {
        return a;
    }

    public float getRa() {
        return ra;
    }

    @Override
    public String toString() {
        return "BallUpdate{" +
                "id=" + id +
                ", rx=" + rx +
                ", ry=" + ry +
                ", lx=" + lx +
                ", ly=" + ly +
                ", a=" + a +
                ", ra=" + ra +
                '}';
    }
}
