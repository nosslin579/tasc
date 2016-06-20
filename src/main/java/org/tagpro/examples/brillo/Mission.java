package org.tagpro.examples.brillo;

import org.tagpro.tasc.data.Update;

import java.util.Map;

public interface Mission {
    void act(int step, Map<Integer, Update> updates, MissionBot bot);

    MissionStatus getMissionStatus(MissionBot bot);

    Mission getConsecutiveMission();
}
