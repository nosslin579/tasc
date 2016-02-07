package org.tagpro.tasc;

import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;
import org.tagpro.tasc.data.KeyChange;
import org.tagpro.tasc.extras.*;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ExampleBots {
    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        if (Arrays.asList(args).contains("PredictionBot")) {
            startPredictionBot();
        } else {
            startGoRightBotLeaveWhenDead();
        }
    }

    private static void startGoRightBotLeaveWhenDead() throws InterruptedException, IOException, URISyntaxException {
        Starter s = new Starter();
        s.addListener(new ExitWhenDead());
        s.addListener(new MaxTime(7, TimeUnit.SECONDS));
        s.addListener(new CommandFix());
        s.addListener(new FixedMovement(new KeyChange(Key.RIGHT, KeyAction.KEYDOWN, 0)));
        s.start();
    }

    public static void startPredictionBot() throws IOException, URISyntaxException, InterruptedException {
        Starter s = new Starter();

        ClientSidePredictionLogger clientSidePredictionLogger = new ClientSidePredictionLogger();
        Box2DClientSidePredictor clientSidePredictor = new Box2DClientSidePredictor();

        s.addListener(clientSidePredictor);
        s.addListener(clientSidePredictionLogger);
        s.addListener(new SyncService(clientSidePredictionLogger, clientSidePredictor));
        s.addListener(new MaxTime(4, TimeUnit.SECONDS));
        s.addListener(FixedMovement.createRightThenLeftMovement());

        s.start();
    }


}
