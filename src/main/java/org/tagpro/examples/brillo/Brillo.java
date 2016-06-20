package org.tagpro.examples.brillo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.examples.Controller;
import org.tagpro.examples.Gui;
import org.tagpro.tasc.extras.CommandFix;
import org.tagpro.tasc.extras.GameStateService;
import org.tagpro.tasc.starter.GameInfo;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;

public class Brillo {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Mission grabFlagMission = new GrabFlagMission();
    private final Mission returnWithFlagMission = new ReturnWithFlagMission();
    private final Mission pendingMission = new PendingMission();

    public Mission getNewMission(Mission mission, MissionStatus missionStatus) {
        log.info("Current:" + mission + " Mission status:" + missionStatus);
        if (mission == grabFlagMission && missionStatus == MissionStatus.SUCCESS) {
            return returnWithFlagMission;
        } else if (mission == returnWithFlagMission && missionStatus != MissionStatus.IN_PROGRESS) {
            return grabFlagMission;
        } else if (mission == pendingMission) {
            return grabFlagMission;
        }
        return grabFlagMission;
    }

    public Mission getPendingMission() {
        return pendingMission;
    }

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        final Brillo brillo = new Brillo();

        Starter starter = new Starter("Brillo1");
        GameStateService gameStateService = GameStateService.create(starter);
        Controller controller = Controller.create(starter);
        MissionBot missionBot1 = new MissionBot(brillo, controller, gameStateService);

        starter.addListener(missionBot1);
        starter.addListener(Gui.create(starter));
        starter.setRecordEvents(true);
        starter.addListener(new CommandFix(starter.getCommand()));
        GameInfo start = starter.start();
    }
}