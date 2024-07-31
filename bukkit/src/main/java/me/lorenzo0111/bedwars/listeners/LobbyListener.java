package me.lorenzo0111.bedwars.listeners;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.scoreboard.WrappedScoreboard;
import me.lorenzo0111.bedwars.hooks.scoreboard.ScoreboardHookWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyListener implements Listener {
    private final WrappedScoreboard scoreboard;

    public LobbyListener(BedwarsPlugin plugin) {
        this.scoreboard = ScoreboardHookWrapper.getHook()
                .create(
                        plugin.getMessage("scoreboards.lobby.title"),
                        () -> plugin.getMessages("scoreboards.lobby.lines")
                                .stream()
                                .map(line -> line.replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                                        .replace("%max_players%", String.valueOf(Bukkit.getMaxPlayers()))
                                        .replace("%games%", String.valueOf(plugin.getGameManager().getGames().size()))
                                )
                                .toList()
                );
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.scoreboard.show(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.scoreboard.hide(event.getPlayer());
    }
}
