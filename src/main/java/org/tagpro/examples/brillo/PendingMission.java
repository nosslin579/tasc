package org.tagpro.examples.brillo;

import org.tagpro.tasc.data.Update;

import java.util.Map;

public class PendingMission implements Mission {

    @Override
    public void act(int step, Map<Integer, Update> updates, MissionBot bot) {
        bot.getController().stop();
    }

    @Override
    public MissionStatus getMissionStatus(MissionBot bot) {
        return MissionStatus.SUCCESS;
    }

    @Override
    public Mission getConsecutiveMission() {
        return new GrabFlagMission();
    }

    @Override
    public String toString() {
        return "PendingMission{}";
    }
}
