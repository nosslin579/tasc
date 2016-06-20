package org.tagpro.bots.brillo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.bots.BallPredictor;
import org.tagpro.bots.EquationBallPredictor;
import org.tagpro.bots.GamePad;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.Update;
import org.tagpro.tasc.extras.GameStateService;

import java.util.Map;

public class MissionBot implements GameSubscriber {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BallPredictor ballPredictor = new EquationBallPredictor();
    private final Brillo brillo;
    private final GamePad gamePad;
    private final GameStateService gameStateService;
    private Mission mission;

    public MissionBot(Brillo brillo, GamePad gamePad, GameStateService gameStateService) {
        this.brillo = brillo;
        this.gamePad = gamePad;
        this.gameStateService = gameStateService;
        this.mission = new PendingMission();
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        final MissionStatus missionStatus = mission.getMissionStatus(this);
        if (missionStatus != MissionStatus.IN_PROGRESS) {
            mission = brillo.missionEnd(this, missionStatus);
        }
        mission.act(step, updates, this);
    }

    public GameStateService getGameStateService() {
        return gameStateService;
    }

    public GamePad getGamePad() {
        return gamePad;
    }

    public Mission getMission() {
        return mission;
    }
}
