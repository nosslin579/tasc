package org.tagpro.examples;

import org.tagpro.tasc.box2d.Box2DClientSidePredictor;
import org.tagpro.tasc.box2d.ClientSidePredictionLogger;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;
import org.tagpro.tasc.data.KeyChange;
import org.tagpro.tasc.extras.*;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class Bots {
    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        if (args.length != 1) {
            throw new IllegalArgumentException("No bot to start");
        } else if (args[0].equals("PredictionBot")) {
            startPredictionBot();
        } else if (args[0].equals("GoRightBotLeaveWhenDead")) {
            startGoRightBotLeaveWhenDead();
        } else if (args[0].equals("Precision")) {
            startPrecision();
        }
    }

    private static void startPrecision() throws InterruptedException, IOException, URISyntaxException {
        Starter s = new Starter("Precision");
        Controller controller = Controller.create(s.getCommand());
        ServerStepEstimator stepEstimator = new ServerStepEstimator(s.getCommand());

        Precision precision = new Precision(controller, stepEstimator);
        s.addListener(new MaxTime(s.getCommand(), 60, TimeUnit.SECONDS));
        s.addListener(new CommandFix(s.getCommand()));
        s.addListener(stepEstimator);
        s.addListener(precision);
        s.start();
    }

    private static void startGoRightBotLeaveWhenDead() throws InterruptedException, IOException, URISyntaxException {
        Starter s = new Starter("GoRightBotLeaveWhenDead");
        s.addListener(new ExitWhenDead(s.getCommand()));
        s.addListener(new MaxTime(s.getCommand(), 7, TimeUnit.SECONDS));
        s.addListener(new CommandFix(s.getCommand()));
        s.addListener(new FixedMovement(s.getCommand(), new KeyChange(Key.RIGHT, KeyAction.KEYDOWN, 0)));
        s.start();
    }

    public static void startPredictionBot() throws IOException, URISyntaxException, InterruptedException {
        Starter s = new Starter("PredictionBot");

        ServerStepEstimator stepEstimator = new ServerStepEstimator(s.getCommand());
        Box2DClientSidePredictor clientSidePredictor = new Box2DClientSidePredictor();
        ClientSidePredictionLogger clientSidePredictionLogger = new ClientSidePredictionLogger();

        stepEstimator.addListener(clientSidePredictor);
        clientSidePredictor.addListener(clientSidePredictionLogger);

        s.addListener(clientSidePredictor);
        s.addListener(clientSidePredictionLogger);
        s.addListener(stepEstimator);
        s.addListener(new MaxTime(s.getCommand(), 4, TimeUnit.SECONDS));
        s.addListener(FixedMovement.createRightThenLeftMovement(s.getCommand()));

        s.getCommand().addObserver(clientSidePredictor);

        s.start();
    }


}
