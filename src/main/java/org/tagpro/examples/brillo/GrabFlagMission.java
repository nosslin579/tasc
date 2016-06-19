package org.tagpro.examples.brillo;

import org.tagpro.tasc.data.*;
import org.tagpro.tasc.extras.GameStateService;

import java.util.Map;

public class GrabFlagMission implements Mission {

    @Override
    public void act(int step, Map<Integer, Update> updates, MissionBot bot) {
        final GameStateService gameStateService = bot.getGameStateService();
        Update self = updates.get(gameStateService.getId());
        if (self != null && self.getBallUpdate() != null && gameStateService.getGameState() == GameState.ACTIVE) {
            Team team = gameStateService.getSelfPlayerAttribute().getTeam();
            Tile flagTile = gameStateService.getMap().getTile(team.getOppositeFlagTileType());
            bot.getController().goTo(flagTile, self.getBallUpdate());
        }
    }

    @Override
    public MissionStatus getMissionStatus(MissionBot bot) {
        final PlayerAttribute self = bot.getGameStateService().getSelfPlayerAttribute();
        if (self.hasFlag()) {
            return MissionStatus.SUCCESS;
        } else if (bot.getGameStateService().hasAnyTeamMemberOppositeFlag()) {
            return MissionStatus.FAIL;//not really fail
        }
        return MissionStatus.IN_PROGRESS;
    }

    @Override
    public String toString() {
        return "GrabFlagMission{}";
    }
}
