package me.lorenzo0111.bedwars.listeners;

import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class StatsListener implements Listener {
    private final BedwarsPlugin plugin;

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        plugin.getStatsManager().load(event.getPlayer());
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        plugin.getStatsManager().unload(event.getPlayer());
    }

    @EventHandler
    public void onEnd(@NotNull BedwarsEndEvent event) {
        event.getWinners().forEach(player -> plugin.getStatsManager().get(player).addWin());
    }

    @EventHandler
    public void onKill(@NotNull BedwarsKillEvent event) {
        if (event.getKiller() != null)
            plugin.getStatsManager().get(event.getKiller()).addKill();
        plugin.getStatsManager().get(event.getKilled()).addDeath();
    }

    @EventHandler
    public void onRemove(@NotNull BedwarsPlayerRemoveEvent event) {
        plugin.getStatsManager().get(event.getPlayer()).addLoss();
    }

    @EventHandler
    public void onStart(@NotNull BedwarsStartEvent event) {
        event.getGame().getPlayers().forEach(player ->
                plugin.getStatsManager().get(player).addGame());
    }

    @EventHandler
    public void onBedDestroy(@NotNull BedwarsBedDestroyEvent event) {
        plugin.getStatsManager().get(event.getPlayer()).addBed();
    }

}
