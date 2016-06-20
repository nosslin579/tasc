package org.tagpro.tasc.data;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlagTest {

    @Test
    public void testResolve() throws Exception {
        JSONObject jsonObject = new JSONObject("{\"flag\":null}");
        Object flagJsonObject = jsonObject.opt("flag");
        int integer = jsonObject.isNull("flag") ? 0 : jsonObject.getInt("flag");
        Flag resolve = Flag.resolve(integer);
        Assert.assertEquals(resolve, Flag.BLUE);
    }
}