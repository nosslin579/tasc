package org.tagpro.tasc.box2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.data.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private int id;
    private Body body;
    private float acceleration = 0.025f;
    private Set<Key> keyState = new HashSet<>(5);

    public void setBodyPositionAndVelocity(BallUpdate serverValues) {
        if (body.getWorld().isLocked()) {
            throw new IllegalStateException("World is locked");
        }
        this.id = serverValues.getId();
        this.body.setLinearVelocity(new Vec2(serverValues.getLx(), serverValues.getLy()));
        this.body.setTransform(new Vec2(serverValues.getRx(), serverValues.getRy()), serverValues.getRa());
        this.body.setAngularVelocity(serverValues.getA());
    }

    public PlayerState getPlayerState() {
        return new PlayerState(id, body.getPosition().x, body.getPosition().y, body.getLinearVelocity().x, body.getLinearVelocity().y, body.getAngle(), body.getAngularVelocity(), keyState);
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public int getId() {
        return id;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public boolean isLeft() {
        return keyState.contains(Key.LEFT);
    }

    public boolean isRight() {
        return keyState.contains(Key.RIGHT);
    }

    public boolean isUp() {
        return keyState.contains(Key.UP);
    }

    public boolean isDown() {
        return keyState.contains(Key.DOWN);
    }

    public void setKey(Key key, KeyAction keyAction) {
        if (keyAction == KeyAction.KEYDOWN) {
            keyState.add(key);
            float x = body.getLinearVelocity().x;
            float y = body.getLinearVelocity().y;

            if (key == Key.LEFT) {
                x -= acceleration;
            }
            if (key == Key.RIGHT) {
                x += acceleration;
            }
            if (key == Key.UP) {
                y -= acceleration;
            }
            if (key == Key.DOWN) {
                y += acceleration;
            }
            body.setLinearVelocity(new Vec2(x, y));
        } else {
            keyState.remove(key);
        }
    }

    public void updateVelocity() {
        //todo add max speed restriction
        float x = body.getLinearVelocity().x;
        float y = body.getLinearVelocity().y;

        if (isLeft()) {
            x -= acceleration;
        }
        if (isRight()) {
            x += acceleration;
        }
        if (isUp()) {
            y -= acceleration;
        }
        if (isDown()) {
            y += acceleration;
        }
        //this method wakes body
        body.setLinearVelocity(new Vec2(x, y));

    }

    public void setId(int id) {
        this.id = id;
    }


    public void setKeyState(Set<Key> pressedKeys) {
        this.keyState = pressedKeys;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", lx=" + body.getLinearVelocity().x +
                ", ly=" + body.getLinearVelocity().y +
                ", rx=" + body.getPosition().x +
                ", ry=" + body.getPosition().y +
                ", keyState=" + keyState +
                '}';
    }

    public void updateKeys(List<KeyUpdate> keyUpdate) {
        for (KeyUpdate k : keyUpdate) {
            if (k.getKeyAction().isPushed()) {
                keyState.add(k.getKey());
                //todo update velocity
            } else {
                keyState.remove(k.getKey());
            }
        }
    }
}
