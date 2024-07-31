package me.lorenzo0111.bedwars.api;

import me.lorenzo0111.bedwars.api.game.AbstractGameManager;
import me.lorenzo0111.bedwars.api.game.TeamAssigner;
import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.scoreboard.ScoreboardHook;

public interface BedwarsAPI {

    AbstractGameManager getGameManager();

    TeamAssigner getTeamAssigner();
    void setTeamAssigner(TeamAssigner assigner);

    void setHologramHook(HologramHook hook);
    void setScoreboardHook(ScoreboardHook hook);
}
