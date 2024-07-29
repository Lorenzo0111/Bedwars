package me.lorenzo0111.bedwars.api;

import me.lorenzo0111.bedwars.api.game.AbstractGameManager;
import me.lorenzo0111.bedwars.api.game.TeamAssigner;

public interface BedwarsAPI {

    AbstractGameManager getGameManager();

    TeamAssigner getTeamAssigner();
    void setTeamAssigner(TeamAssigner assigner);

}
