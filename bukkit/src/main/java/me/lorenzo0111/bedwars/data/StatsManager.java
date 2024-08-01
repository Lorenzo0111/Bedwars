package me.lorenzo0111.bedwars.data;

import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.data.PlayerStats;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class StatsManager {
    private final BedwarsPlugin plugin;
    private final Map<Player, PlayerStats> stats = new HashMap<>();

    public void load(@NotNull Player player) {
        plugin.getDatabase().getStats(player.getUniqueId()).thenAccept(stats ->
                this.stats.put(player, stats));
    }

    public void unload(@NotNull Player player) {
        this.stats.remove(player);
    }

    public PlayerStats get(@NotNull Player player) {
        return this.stats.get(player);
    }
}
