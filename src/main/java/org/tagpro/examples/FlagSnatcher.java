package org.tagpro.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.GameState;
import org.tagpro.tasc.data.Tile;
import org.tagpro.tasc.data.TileType;
import org.tagpro.tasc.data.Update;
import org.tagpro.tasc.extras.CommandFix;
import org.tagpro.tasc.extras.GameStateService;
import org.tagpro.tasc.extras.ServerStepEstimator;
import org.tagpro.tasc.starter.Starter;

import java.util.List;
import java.util.Map;

public class FlagSnatcher implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BallPredictor ballPredictor = new EquationBallPredictor();
    private final Controller controller;
    private final ServerStepEstimator stepEstimator;
    private final GameStateService gameStateService;
    private Tile blueFlag;
    private Tile redFlag;
    private boolean goToRedFlag = true;

    public FlagSnatcher(Controller controller, ServerStepEstimator stepEstimator, GameStateService gameStateService) {
        this.controller = controller;
        this.stepEstimator = stepEstimator;
        this.gameStateService = gameStateService;
    }

    public static FlagSnatcher create(Starter starter) {
        GameStateService gameSubscriber = GameStateService.create(starter);
        ServerStepEstimator stepEstimator = ServerStepEstimator.create(starter);
        Controller controller = Controller.create(starter.getCommand());

        FlagSnatcher ret = new FlagSnatcher(controller, stepEstimator, gameSubscriber);
        starter.addListener(ret);
        starter.addListener(new CommandFix(starter.getCommand()));
        starter.addListener(Gui.create(starter));
        starter.setRecordEvents(true);
        return ret;
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        Update self = updates.get(gameStateService.getId());
        if (self != null && self.getBallUpdate() != null && gameStateService.getGameState() == GameState.ACTIVE) {
            Tile flag = goToRedFlag ? redFlag : blueFlag;
            controller.goTo(flag, self.getBallUpdate());
        }
    }

    @Override
    public void onMapUpdate(Tile tile) {
        log.info("Map update" + tile);
        TileType type = tile.getType();
        if (type == TileType.BLUE_FLAG_TAKEN || type == TileType.RED_FLAG) {
            goToRedFlag = true;
        } else if (type == TileType.RED_FLAG_TAKEN || type == TileType.BLUE_FLAG) {
            goToRedFlag = false;
        }
    }

    @Override
    public void map(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.getType() == TileType.BLUE_FLAG) {
                this.blueFlag = tile;
            } else if (tile.getType() == TileType.RED_FLAG) {
                this.redFlag = tile;
            }
        }
    }
}
