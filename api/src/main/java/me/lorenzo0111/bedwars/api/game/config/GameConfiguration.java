package me.lorenzo0111.bedwars.api.game.config;

import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameConfiguration implements ConfigurationSerializable {
    private final String id;
    private final Map<ChatColor, TeamConfig> teams = new HashMap<>();
    private int arenas;
    private int playersPerTeam;

    public GameConfiguration(String id) {
        this.id = id;
    }

    @SuppressWarnings({"unused", "unchecked"})
    public GameConfiguration(Map<String, Object> data) {
        this.id = (String) data.get("id");
        this.arenas = (int) data.get("arenas");
        this.playersPerTeam = (int) data.get("playersPerTeam");

        Map<String, Object> teams = (Map<String, Object>) data.get("teams");
        teams.forEach((color, config) -> this.teams.put(ChatColor.valueOf(color),
                new TeamConfig((Map<String, Object>) config)));
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> teams = new HashMap<>();
        this.teams.forEach((color, config) -> teams.put(color.name(), config.serialize()));

        return Map.of(
                "id", id,
                "arenas", arenas,
                "playersPerTeam", playersPerTeam,
                "teams", teams
        );
    }
}
