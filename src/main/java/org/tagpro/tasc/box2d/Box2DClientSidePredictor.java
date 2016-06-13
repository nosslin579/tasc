package org.tagpro.tasc.box2d;

import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.*;
import org.tagpro.tasc.extras.ServerStepEstimator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Box2DClientSidePredictor implements GameSubscriber, ServerStepEstimator.ServerStepObserver {

    private final TagProWorld world = new TagProWorld(1);
    private int id;
    private List<ClientSidePredictionObserver> observers = new ArrayList<>();

    public void addListener(ClientSidePredictionObserver observer) {
        this.observers.add(observer);
    }

    public PlayerState predict(int step) {
        synchronized (this) {
            world.proceedToStep(step);
            return world.getPlayer(1).getPlayerState();
        }
    }

    @Override
    public void map(List<Tile> tiles) {
        world.setMap(tiles);
    }

    @Override
    public void keyPressed(Key key, KeyAction keyAction, int count) {
        world.getPlayer(1).setKey(key, keyAction);
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        Update update = updates.get(id);
        if (update == null) {
            //Not self player
            return;
        }

        final BallUpdate ballUpdate = update.getBallUpdate();
        if (ballUpdate != null) {
            synchronized (this) {
                world.getPlayer(1).setBodyPositionAndVelocity(ballUpdate);
                world.setStep(step);
            }
        }
    }

    @Override
    public void onId(int id) {
        this.id = id;
    }

    @Override
    public void onEstimateStep(int step) {
        PlayerState playerState = predict(step);
        for (ClientSidePredictionObserver observer : observers) {
            observer.onPredict(step, playerState);
        }
    }

    public interface ClientSidePredictionObserver {
        void onPredict(int step, PlayerState playerState);
    }
}
