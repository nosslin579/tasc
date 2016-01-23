package org.tagpro.tasc;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.listeners.BallUpdate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TagProWorld extends World {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final float DAMPING = .5f;
    public static final float DT = 1f / 60f;
    public static final long STEP_NS = TimeUnit.SECONDS.toNanos(1) / 60;
    // Damping factor.
    public static final float D = 1 - DAMPING * DT;
    public static final int SCALE = 100;
    private volatile int worldStep = 0;
    private final Player self;


    private final Map<Integer, Player> players = new HashMap<>(1);

    public TagProWorld(Integer idSelf, int numberOfPlayers) {
        super(new Vec2());
        for (int id = 1; id <= numberOfPlayers; id++) {
            Player player = new Player();
            Body ballBody = createBallBody(id);
            player.setBody(ballBody);
            player.setId(id);
            players.put(id, player);
        }
        self = players.get(idSelf);
    }

    public Player getSelf() {
        return self;
    }

    private Body createBallBody(int id) {
        CircleShape ball = new CircleShape();
        ball.setRadius(.19f);

        BodyDef ballDef = new BodyDef();
        ballDef.userData = id;
        ballDef.angularDamping = DAMPING;
        ballDef.linearDamping = DAMPING;
        ballDef.type = BodyType.DYNAMIC;
        Body body = createBody(ballDef);

        final FixtureDef ballFixture = new FixtureDef();
        ballFixture.density = 1f;
        ballFixture.friction = .5f;
        ballFixture.restitution = .2f;
        ballFixture.shape = ball;
        body.createFixture(ballFixture);

        //outside of map
        body.setTransform(new Vec2(-100f, -100f), 0);

        return body;
    }

    public void proceedToStep(int step) {
        if (worldStep == 0) {
            worldStep = step;
        }
        if (worldStep == step) {
//            log.debug("Already up to step" + step);
            return;
        }
        log.debug("Stepping from:" + worldStep + " to:" + step);
        while (worldStep < step) {
            step();
//            log.debug(StepService.DT + " " + world.getBodyList().getPosition() + " " + world.getBodyList().getLinearVelocity());
        }
        log.debug("World is now at:" + worldStep);
    }

    public int step() {
        step(DT, 8, 3);
        for (Player player : players.values()) {
            player.updateVelocity();
//            log.debug("Player velocity updated" + player);
        }
        return ++worldStep;
    }

    public Player getPlayer(Integer id) {
        return players.get(id);
    }

    public void update(int step, Map<Integer, Update> updates) {
        for (Update u : updates.values()) {
            BallUpdate ballUpdate = u.getBallUpdate();
            if (ballUpdate != null) {
                int id = u.getId();
                Player player = players.get(id);
                player.setBodyPositionAndVelocity(ballUpdate);
                worldStep = step;
            }
        }
    }


    /**
     * @return The predicted velocity
     */
    public static float calculateSpeed(float initialVelocity, float acceleration, int steps) {
/*
   * Expansion of \sum_{i=0}^{n-1} ad^{n-i}
   * + initial velocity term.
   */
        double v = initialVelocity * Math.pow(D, steps);// Initial velocity term
        double speed = v + acceleration * ((D - Math.pow(D, steps + 1)) / (1 - D));
        return (float) speed;
    }

    /**
     * @return {number} - The predicted position
     */
    public static float calculatePosition(float initialVelocity, float ac, int steps) {

        // Expansion of \sum_{i=1}^n scale * dt * getVelocity(n)
        double sum = (D - Math.pow(D, steps + 1)) / (1 - D);
        return (float) (SCALE * DT * (initialVelocity * sum + (ac / (1 - D)) * (D * steps - D * sum)));
    }

    /**
     * Given an initial velocity, target velocity, and acceleration, gives
     * the number of steps necessary to reach that target velocity.
     *
     * @return {number} - The number of steps required to reach the
     * target velocity, or null if there was a constraint violation.
     * Likely float, use floor/ceil as appropriate.
     */
    public static float calculateSteps(float initialVelocity, float ac, float targetVelocity) {
        if (ac == 0) {
            if (initialVelocity == targetVelocity) {
                return 0;
            } else {
                throw new IllegalArgumentException("You will never reach that velocity");//null;
            }
        }
        if (initialVelocity > targetVelocity && ac >= 0) throw new IllegalArgumentException("Todo find out why");//null;
        if (initialVelocity < targetVelocity && ac <= 0) throw new IllegalArgumentException("Todo find out why");//null;
        // Inverse of v = getVelocity(n).
        return (float) ((Math.log(targetVelocity * (1 - D) - ac * D) - Math.log(initialVelocity * (1 - D) - ac * D)) / Math.log(D));
    }

    public void setStep(int step) {
        this.worldStep = step;
    }
}
