package me.lorenzo0111.bedwars.listeners;

import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
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
}
