package me.lorenzo0111.bedwars.api.game;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

@RequiredArgsConstructor
@Data
public abstract class AbstractGame {
    protected final UUID uuid = UUID.randomUUID();
    protected final GameConfiguration config;
    protected final List<Player> players = new ArrayList<>();
    protected final Map<ChatColor, List<Player>> teams = new HashMap<>();
    protected GameState state = GameState.WAITING;

    public abstract void start();
    public abstract void stop();
    public abstract boolean isLoading();
}
