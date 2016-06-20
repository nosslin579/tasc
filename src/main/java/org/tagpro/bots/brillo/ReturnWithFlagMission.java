package org.tagpro.bots.brillo;

import org.tagpro.tasc.data.GameState;
import org.tagpro.tasc.data.Team;
import org.tagpro.tasc.data.Tile;
import org.tagpro.tasc.data.Update;
import org.tagpro.tasc.extras.GameStateService;

import java.util.Map;

public class ReturnWithFlagMission implements Mission {
    @Override
    public void act(int step, Map<Integer, Update> updates, MissionBot bot) {
        final GameStateService gameStateService = bot.getGameStateService();
        Update self = updates.get(gameStateService.getId());
        if (self != null && self.getBallUpdate() != null && gameStateService.getGameState() == GameState.ACTIVE) {
            Team ownTeam = gameStateService.getSelfPlayerAttribute().getTeam();
            Tile ownFlagTile = gameStateService.getMap().getTile(ownTeam.getFlag().getTileType());
            bot.getGamePad().goTo(ownFlagTile, self.getBallUpdate());
        }
    }

    @Override
    public MissionStatus getMissionStatus(MissionBot bot) {
        if (bot.getGameStateService().getSelfPlayerAttribute().hasFlag()) {
            return MissionStatus.IN_PROGRESS;
        }
        return MissionStatus.SUCCESS;
    }

    @Override
    public Mission getConsecutiveMission() {
        return new GrabFlagMission();
    }

    @Override
    public String toString() {
        return "ReturnWithFlagMission{}";
    }
}
