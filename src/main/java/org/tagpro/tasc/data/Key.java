package org.tagpro.tasc.data;

import java.util.Collection;
import java.util.TreeSet;

public enum Key {
    UP(), DOWN(), LEFT(), RIGHT(), SPACE;

    public String getCommand() {
        return name().toLowerCase();
    }

    public Key getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        return SPACE;
    }

    public static Collection<Key> getKeys(double currentDegree, double desiredDegree) {
        Collection<Key> ret = new TreeSet<>();
        if (desiredDegree < 0 || desiredDegree > 360 || currentDegree < 0 || currentDegree > 360) {
            throw new IllegalStateException("Degree out of bounds:" + desiredDegree + " " + currentDegree);
        } else if (desiredDegree < 45) {
            ret.add(UP);
        } else if (desiredDegree < 135) {
            ret.add(RIGHT);
        } else if (desiredDegree < 225) {
            ret.add(DOWN);
        } else if (desiredDegree < 315) {
            ret.add(LEFT);
        } else {
            ret.add(UP);
        }

        if (isDown(desiredDegree) && isD1AboveD2(currentDegree, desiredDegree)) {
            ret.add(DOWN);
        }
        if (isUp(desiredDegree) && !isD1AboveD2(currentDegree, desiredDegree)) {
            ret.add(UP);
        }
        if (isLeft(desiredDegree) && isD1RightOfD2(currentDegree, desiredDegree)) {
            ret.add(LEFT);
        }
        if (isRight(desiredDegree) && !isD1RightOfD2(currentDegree, desiredDegree)) {
            ret.add(RIGHT);
        }

        return ret;
    }

    public static boolean isD1AboveD2(double d1, double d2) {
        return Math.cos(Math.toRadians(d1)) > Math.cos(Math.toRadians(d2));
    }

    public static boolean isD1RightOfD2(double d1, double d2) {
        return Math.sin(Math.toRadians(d1)) > Math.sin(Math.toRadians(d2));
    }

    static boolean isDown(double degree) {
        return degree > 90 && degree < 270;
    }

    public static boolean isUp(double degree) {
        return degree < 90 || degree > 270;
    }

    static boolean isLeft(double degree) {
        return degree > 180;
    }

    static boolean isRight(double degree) {
        return degree < 180;
    }

}
