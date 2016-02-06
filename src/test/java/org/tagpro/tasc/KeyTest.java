package org.tagpro.tasc;

import org.tagpro.tasc.data.Key;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

@Test
public class KeyTest {

    public void testKey() {
        Assert.assertTrue(Key.isD1AboveD2(4, 315));
        Assert.assertFalse(Key.isD1AboveD2(179, 181));

        Assert.assertEquals(Math.sin(Math.toRadians(90)), 1.0d);

        Assert.assertFalse(Key.isD1RightOfD2(2, 5));
        Assert.assertTrue(Key.isD1RightOfD2(90, 270));
        Assert.assertFalse(Key.isD1RightOfD2(315, 5));
        Assert.assertFalse(Key.isD1RightOfD2(271, 275));
        Assert.assertTrue(Key.isUp(275d));

        Assert.assertEquals(Key.getKeys(90, 95), Arrays.asList(Key.DOWN, Key.RIGHT));
        Assert.assertEquals(Key.getKeys(95, 94), Arrays.asList(Key.RIGHT));

        Assert.assertEquals(Key.getKeys(2, 5), Arrays.asList(Key.UP, Key.RIGHT));
        Assert.assertEquals(Key.getKeys(2, 1), Arrays.asList(Key.UP));

        Assert.assertEquals(Key.getKeys(183, 188), Arrays.asList(Key.DOWN, Key.LEFT));
        Assert.assertEquals(Key.getKeys(183, 181), Arrays.asList(Key.DOWN));

        Assert.assertEquals(Key.getKeys(271, 275), Arrays.asList(Key.UP, Key.LEFT));
        Assert.assertEquals(Key.getKeys(272, 271), Arrays.asList(Key.LEFT));

//        Assert.assertEquals(MathUtil.normalizeDegree(-45), 315);

//        final double sin = Math.sin(Math.toDegrees(-10));
//        final double v = Math.toDegrees(Math.sinh(sin));
//        Assert.assertEquals(v,-10);


//        Assert.assertEquals(MathUtil.calculateDegree(0, 0, 1, 1), 135d);//down=-90
//        Assert.assertEquals(MathUtil.calculateDegree(0, 0, 0, 1), 180d);//down=-90
//        Assert.assertEquals(MathUtil.calculateDegree(0, 0, 1, 0), 90d);//right=0
//        Assert.assertEquals(MathUtil.calculateDegree(0, 0, 0, 1), 0d);//up=90
//        Assert.assertEquals(MathUtil.calculateDegree(1, 0, -1, 0), 270d);//left=180


    }
}