package org.tagpro.examples.brillo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.examples.BallPredictor;
import org.tagpro.examples.Controller;
import org.tagpro.examples.EquationBallPredictor;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.Update;
import org.tagpro.tasc.extras.GameStateService;

import java.util.Map;

public class MissionBot implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BallPredictor ballPredictor = new EquationBallPredictor();
    private final Brillo brillo;
    private final Controller controller;
    private final GameStateService gameStateService;
    private Mission mission;

    public MissionBot(Brillo brillo, Controller controller, GameStateService gameStateService) {
        this.brillo = brillo;
        this.controller = controller;
        this.gameStateService = gameStateService;
        this.mission = brillo.getPendingMission();
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        final MissionStatus missionStatus = mission.getMissionStatus(this);
        if (missionStatus != MissionStatus.IN_PROGRESS) {
            mission = brillo.getNewMission(mission, missionStatus);
            log.info("Got new mission:" + mission);
        }
        mission.act(step, updates, this);
    }

    public GameStateService getGameStateService() {
        return gameStateService;
    }

    public Controller getController() {
        return controller;
    }
}
