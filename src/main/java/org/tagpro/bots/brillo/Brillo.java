package org.tagpro.bots.brillo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.bots.GamePad;
import org.tagpro.bots.Gui;
import org.tagpro.tasc.extras.CommandFix;
import org.tagpro.tasc.extras.GameStateService;
import org.tagpro.tasc.starter.GameInfo;
import org.tagpro.tasc.starter.Starter;

import java.io.IOException;
import java.net.URISyntaxException;

public class Brillo {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public Mission missionEnd(MissionBot mission, MissionStatus missionStatus) {
        log.info("Mission end:" + mission + " with status:" + missionStatus);
        return mission.getMission().getConsecutiveMission();
    }

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        final Brillo brillo = new Brillo();

        Starter starter = new Starter("Brillo1");
        GameStateService gameStateService = GameStateService.create(starter);
        GamePad gamePad = GamePad.create(starter);
        MissionBot missionBot1 = new MissionBot(brillo, gamePad, gameStateService);

        starter.addListener(missionBot1);
        starter.addListener(Gui.create(starter));
        starter.setRecordEvents(true);
        starter.addListener(new CommandFix(starter.getCommand()));
        GameInfo start = starter.start();
    }
}