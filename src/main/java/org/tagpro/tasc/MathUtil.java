package org.tagpro.tasc;

public class MathUtil {
    public static double calculateDegree(double fromX, double fromY, double toX, double toY) {
        final double aimDegree = Math.toDegrees(Math.atan2(-toY + fromY, toX - fromX));
        final double v = -aimDegree + 90;
        return v<0 ? v + 360 : v;
    }

    public static double normalizeDegree(double degree) {
        return (degree + 360) % 360;
    }
}
