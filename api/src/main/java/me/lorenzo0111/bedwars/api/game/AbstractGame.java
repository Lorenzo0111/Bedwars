package me.lorenzo0111.bedwars.api.game;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

@RequiredArgsConstructor
@Data
public abstract class AbstractGame {
    protected final UUID uuid = UUID.randomUUID();
    protected final GameConfiguration config;
    protected final List<Player> players = new ArrayList<>();
    protected final Map<ChatColor, List<Player>> teams = new HashMap<>();
    protected World world;
    protected GameState state = GameState.WAITING;

    public abstract void startCountdown();
    public abstract void abortCountdown();
    public abstract void start();
    public abstract void stop();
    public abstract boolean isLoading();
    public abstract void join(Player player);

    public abstract void onDeath(PlayerDeathEvent event);
    public abstract void onBedBreak(BlockBreakEvent event);

    public ChatColor getTeam(Player player) {
        return this.teams.entrySet().stream()
                .filter(entry -> entry.getValue().contains(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public boolean canJoin() {
        return this.state.canJoin() && this.players.size() < this.config.getPlayersPerTeam() * this.config.getTeams().size();
    }
}
