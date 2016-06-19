package org.tagpro.examples.brillo;

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
            Team team = gameStateService.getSelfPlayerAttribute().getTeam();
            Tile flagTile = gameStateService.getMap().getTile(team.getFlag().getTileType());
            bot.getController().goTo(flagTile, self.getBallUpdate());
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
    public String toString() {
        return "ReturnWithFlagMission{}";
    }
}
