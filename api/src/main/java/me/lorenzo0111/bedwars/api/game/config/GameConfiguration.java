package me.lorenzo0111.bedwars.api.game.config;

import lombok.Data;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameConfiguration {
    private final Map<ChatColor, TeamConfig> teams = new HashMap<>();
}
