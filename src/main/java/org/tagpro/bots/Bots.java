package org.tagpro.bots;

import org.tagpro.tasc.box2d.Box2DClientSidePredictor;
import org.tagpro.tasc.box2d.ClientSidePredictionLogger;
import org.tagpro.tasc.extras.CommandFix;
import org.tagpro.tasc.extras.FixedMovement;
import org.tagpro.tasc.extras.MaxTime;
import org.tagpro.tasc.extras.ServerStepEstimator;
import org.tagpro.tasc.starter.GameInfo;
import org.tagpro.tasc.starter.Starter;
import org.tagpro.tasc.starter.StaticGameFinder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class Bots {
    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        if (args.length != 1) {
            throw new IllegalArgumentException("No bot to start");
        } else if (args[0].equals("PredictionBot")) {
            startPredictionBot();
        } else if (args[0].equals("Precision")) {
            startPrecision();
        }
    }

    private static void startPrecision() throws InterruptedException, IOException, URISyntaxException {
        Starter s = new Starter("Precision");
        Controller controller = Controller.create(s);
        ServerStepEstimator stepEstimator = new ServerStepEstimator(s.getCommand());
        Precision precision = new Precision(controller, stepEstimator);
        s.addListener(new MaxTime(s.getCommand(), 60, TimeUnit.SECONDS));
        s.addListener(new CommandFix(s.getCommand()));
        s.addListener(stepEstimator);
        s.addListener(precision);
        GameInfo start = s.start();

        Starter s2 = new Starter("Precision2");
        Controller controller2 = Controller.create(s2);
        ServerStepEstimator stepEstimator2 = new ServerStepEstimator(s2.getCommand());
        Precision precision2 = new Precision(controller2, stepEstimator2);
        s2.addListener(new MaxTime(s2.getCommand(), 60, TimeUnit.SECONDS));
        s2.addListener(new CommandFix(s2.getCommand()));
        s2.addListener(stepEstimator2);
        s2.addListener(precision2);
        s2.setGameFinder(new StaticGameFinder(start.getGameURI()));
        s2.start();

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
