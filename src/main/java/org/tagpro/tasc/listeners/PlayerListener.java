package org.tagpro.tasc.listeners;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GamePublisher;
import org.tagpro.tasc.Key;
import org.tagpro.tasc.KeyAction;
import org.tagpro.tasc.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerListener extends TagProServerListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public PlayerListener(GamePublisher publisher) {
        super(publisher);
    }

    //42["p",{"u":[{"id":1,"rx":16.39,"ry":7.31,"lx":0,"ly":0,"a":0,"ra":-53.5170171996834}],"t":4426}]
//42["p",{"u":[{"id":1,"rx":8.17,"ry":7.02,"lx":-1.94,"ly":-0.91},{"id":5,"rx":3.17,"ry":3.05,"lx":-0.78,"ly":-0.93},{"id":7,"rx":9,"ry":1.55,"lx":-5.83,"ly":0.42,"a":1.61,"ra":16.714225248499403}],"t":1499}]
//42["p",[{"id":4,"name":"jjpoole","team":2,"flag":null,"potatoFlag":null,"selfDestructSoon":null,"grip":false,"speed":false,"tagpro":true,"bomb":false,"dead":false,"directSet":false,"s-tags":0,"s-pops":0,"s-grabs":0,"s-returns":0,"s-captures":0,"s-drops":0,"s-support":0,"s-hold":0,"s-prevent":0,"s-powerups":1,"score":17,"points":0,"up":-35,"down":36,"left":26,"right":-19,"ms":2.5,"ac":0.025,"auth":true,"flair":{"x":0,"y":0,"description":"Daily Leader Board Winner"},"den":1,"degree":166,"rx":10.27,"ry":8.61,"lx":-1.97,"ly":-0.06,"a":2.4,"ra":8.631333307494346}]]

    //42["p",{"u":[{"id":1,"score":67},{"id":3,"flag":null,"potatoFlag":null,"selfDestructSoon":null,"dead":true,"s-pops":9,"s-drops":5,"score":9,"rx":13.49,"ry":14.51,"lx":0,"ly":0,"a":0,"ra":0,"up":-693,"down":-701,"left":703,"right":-702},{"id":4,"s-tags":5,"s-returns":5,"score":56,"rx":13.63,"ry":14.87,"lx":0.96,"ly":2.44,"ra":-0.009841188007807483},{"id":6,"rx":14.58,"ry":14.5,"lx":0.85,"a":0.94,"ra":-1.5936264650340266},{"id":8,"rx":13.33,"ry":14.16,"lx":-0.07,"ly":-0.81,"a":-1.41,"ra":-8.522596493444787}],"t":15330}]
    @Override
    public void event(GamePublisher publisher, Object... args) throws JSONException {
        if (args[0] instanceof JSONArray) {
            log.debug("Ignoring " + args[0]);
            return;
        }
        final JSONObject updateObject = (JSONObject) args[0];
        final int step = updateObject.getInt("t");
        final JSONArray updateArray = updateObject.getJSONArray("u");

        List<BallUpdate> ballUpdateList = new ArrayList<>();
        List<KeyUpdate> keyUpdateList = new ArrayList<>();

        Map<Integer, Update> ret = new HashMap<>();

        for (int i = 0; i < updateArray.length(); i++) {
            JSONObject playerUpdateObject = updateArray.getJSONObject(i);


            BallUpdate ballUpdate = null;
            if (playerUpdateObject.has("rx") && playerUpdateObject.has("lx")) {
                //{"a":0,"rx":14.69,"ry":2.92,"id":1,"lx":0.08,"ly":0.38,"ra":0}
                ballUpdate = ballUpdate(playerUpdateObject);
                ballUpdateList.add(ballUpdate);

            }
//            if (playerUpdateObject.has("right") || playerUpdateObject.has("left") || playerUpdateObject.has("up") || playerUpdateObject.has("down")) {
            //{"t":733,"u":[{"id":1,"up":-13,"down":14}]}
            List<KeyUpdate> keyUpdate = keyUpdate(playerUpdateObject, step);
            keyUpdateList.addAll(keyUpdate);
//            }
            int id = playerUpdateObject.optInt("id");
            ret.put(id, new Update(id, ballUpdate, keyUpdate, playerUpdateObject));
        }

        publisher.update(step, ret);
    }

    private List<KeyUpdate> keyUpdate(JSONObject keyObject, int step) throws JSONException {
        List<KeyUpdate> ret = new ArrayList<>();
        int id = keyObject.getInt("id");
        for (Key key : Key.values()) {
            if (keyObject.has(key.getCommand())) {
                final int counter = keyObject.getInt(key.getCommand());
                KeyAction keyAction = counter <= 0 ? KeyAction.KEYUP : KeyAction.KEYDOWN;
                ret.add(new KeyUpdate(id, key, keyAction, Math.abs(counter)));
            }
        }
        return ret;
    }

    private BallUpdate ballUpdate(JSONObject playerUpdate) throws JSONException {
        final int id = playerUpdate.getInt("id");
        final float rx = (float) playerUpdate.getDouble("rx");
        final float ry = (float) playerUpdate.getDouble("ry");
        final float lx = (float) playerUpdate.getDouble("lx");
        final float ly = (float) playerUpdate.getDouble("ly");
        final float a = (float) playerUpdate.optDouble("a");
        final float ra = (float) playerUpdate.optDouble("ra");
        return new BallUpdate(id, rx, ry, lx, ly, a, ra);
    }
}
