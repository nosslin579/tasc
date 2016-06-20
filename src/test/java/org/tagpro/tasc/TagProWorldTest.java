package org.tagpro.tasc;

import org.jbox2d.common.Vec2;
import org.tagpro.tasc.box2d.Player;
import org.tagpro.tasc.box2d.TagProWorld;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyState;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Real values from http://maptest.newcompte.fr/
 * 15:43:17.095 p {"t":616,"u":[{"a":0,"rx":2,"ry":4.4,"id":1,"lx":0,"ly":0,"ra":0}]}
 * 15:43:17.121 p {"t":618,"u":[{"id":1,"right":1}]}
 * 15:43:17.338 p {"t":631,"u":[{"a":0,"rx":2.04,"ry":4.4,"id":1,"lx":0.33,"ly":0,"ra":0}]}
 * 15:43:17.586 p {"t":646,"u":[{"a":0,"rx":2.16,"ry":4.4,"id":1,"lx":0.65,"ly":0,"ra":0}]}
 * 15:43:17.837 p {"t":661,"u":[{"a":0,"rx":2.35,"ry":4.4,"id":1,"lx":0.92,"ly":0,"ra":0}]}
 * 15:43:18.088 p {"t":676,"u":[{"a":0,"rx":2.61,"ry":4.4,"id":1,"lx":1.17,"ly":0,"ra":0}]}
 * 15:43:18.339 p {"t":691,"u":[{"a":0,"rx":2.92,"ry":4.4,"id":1,"lx":1.38,"ly":0,"ra":0}]}
 * 15:43:18.586 p {"t":706,"u":[{"a":0,"rx":3.29,"ry":4.4,"id":1,"lx":1.58,"ly":0,"ra":0}]}
 * 15:43:18.837 p {"t":721,"u":[{"a":0,"rx":3.7,"ry":4.4,"id":1,"lx":1.74,"ly":0,"ra":0}]}
 * 15:43:19.087 p {"t":736,"u":[{"a":0,"rx":4.15,"ry":4.4,"id":1,"lx":1.89,"ly":0,"ra":0}]}
 * 15:43:19.337 p {"t":751,"u":[{"a":0,"rx":4.63,"ry":4.4,"id":1,"lx":2.02,"ly":0,"ra":0}]}
 * 15:43:19.588 p {"t":766,"u":[{"a":0,"rx":5.15,"ry":4.4,"id":1,"lx":2.14,"ly":0,"ra":0}]}
 * 15:43:19.838 p {"t":781,"u":[{"a":0,"rx":5.69,"ry":4.4,"id":1,"lx":2.24,"ly":0,"ra":0}]}
 * 15:43:20.087 p {"t":796,"u":[{"a":0,"rx":6.26,"ry":4.4,"id":1,"lx":2.33,"ly":0,"ra":0}]}
 * 15:43:20.337 p {"t":811,"u":[{"a":0,"rx":6.84,"ry":4.4,"id":1,"lx":2.41,"ly":0,"ra":0}]}
 * 15:43:20.587 p {"t":826,"u":[{"a":0,"rx":7.45,"ry":4.4,"id":1,"lx":2.48,"ly":0,"ra":0}]}
 * 15:43:20.838 p {"t":841,"u":[{"a":0,"rx":8.07,"ry":4.4,"id":1,"lx":2.52,"ly":0,"ra":0}]}
 * 15:43:21.088 p {"t":856,"u":[{"a":0,"rx":8.69,"ry":4.4,"id":1,"lx":2.5,"ly":0,"ra":0}]}
 * 15:43:21.337 p {"t":871,"u":[{"a":0,"rx":9.31,"ry":4.4,"id":1,"lx":2.51,"ly":0,"ra":0}]}
 * 15:43:21.588 p {"t":886,"u":[{"a":0,"rx":9.94,"ry":4.4,"id":1,"lx":2.52,"ly":0,"ra":0}]}
 */
public class TagProWorldTest {

    public static final float AC = 0.025f;
    public static final float DELTA = 0.005f;

    @Test
    public void testStep() throws Exception {
        TagProWorld tagProWorld = new TagProWorld(1);
        Player player = tagProWorld.getPlayer(1);
        player.setKey(Key.RIGHT, KeyState.KEYDOWN);
        player.getBody().setTransform(new Vec2(2f, 0f), 0);
        player.updateVelocity();
        tagProWorld.setStep(619);
        tagProWorld.proceedToStep(751);
//      {"t":751,"u":[{"a":0,"rx":4.63,"ry":4.4,"id":1,"lx":2.02,"ly":0,"ra":0}]}
        Assert.assertEquals(player.getBody().getPosition().x, 4.63f, DELTA, "rx");
        Assert.assertEquals(player.getBody().getLinearVelocity().x, 2.02f, DELTA, "lx");
        tagProWorld.proceedToStep(841);
//      {"t":841,"u":[{"a":0,"rx":8.07,"ry":4.4,"id":1,"lx":2.52,"ly":0,"ra":0}]}
        Assert.assertEquals(player.getBody().getPosition().x, 8.07f, DELTA, "rx");
        Assert.assertEquals(player.getBody().getLinearVelocity().x, 2.52f, DELTA * 4, "lx");


    }
}