package me.lorenzo0111.bedwars.api;

import me.lorenzo0111.bedwars.api.data.PlayerStats;
import me.lorenzo0111.bedwars.api.game.AbstractGameManager;
import me.lorenzo0111.bedwars.api.game.TeamAssigner;
import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.items.SpecialItem;
import me.lorenzo0111.bedwars.api.scoreboard.ScoreboardHook;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BedwarsAPI {
    AbstractGameManager getGameManager();

    TeamAssigner getTeamAssigner();
    void setTeamAssigner(TeamAssigner assigner);

    void setHologramHook(HologramHook hook);
    void setScoreboardHook(ScoreboardHook hook);

    void registerSpecialItem(SpecialItem item) throws IllegalArgumentException;

    CompletableFuture<PlayerStats> getStats(UUID uuid);
    void updateStats(UUID uuid, PlayerStats stats);
}
