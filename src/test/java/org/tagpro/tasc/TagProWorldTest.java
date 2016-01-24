package org.tagpro.tasc;

import org.jbox2d.common.Vec2;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TagProWorldTest {

    public static final float AC = 0.025f;
    public static final float DELTA = 0.005f;

    @Test
    public void testStep() throws Exception {
        TagProWorld tagProWorld = new TagProWorld(1);
        Player player = tagProWorld.getPlayer(1);
        player.setKey(Key.DOWN, KeyAction.KEYDOWN);
        player.getBody().setTransform(new Vec2(0, 5.2f), 0);
        player.updateVelocity();
        int step = 876;
        for (; step < 901; step++) {
            tagProWorld.step();
        }
        Assert.assertEquals(step, 901, "step");
        Assert.assertEquals(player.getBody().getPosition().y, 5.33f, DELTA, "ry");
        Assert.assertEquals(player.getBody().getLinearVelocity().y, 0.59f, DELTA, "ly");
        for (; step < 916; step++) {
            tagProWorld.step();
        }
        Assert.assertEquals(step, 916, "step");
        Assert.assertEquals(player.getBody().getPosition().y, 5.5f, DELTA, "ry");
        Assert.assertEquals(player.getBody().getLinearVelocity().y, 0.87f, DELTA, "ly");


    }
}