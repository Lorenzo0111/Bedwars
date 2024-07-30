package me.lorenzo0111.bedwars.api.game.config;

import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class GameConfiguration implements ConfigurationSerializable {
    private final String id;
    private final Map<ChatColor, TeamConfig> teams = new HashMap<>();
    private final Map<Material, List<ConfigLocation>> generators = new HashMap<>();
    private ConfigLocation spectatorSpawn;
    private int arenas = -1;
    private int minPlayers = -1;
    private int playersPerTeam = -1;

    public GameConfiguration(String id) {
        this.id = id;
    }

    @SuppressWarnings({"unused", "unchecked"})
    public GameConfiguration(Map<String, Object> data) {
        this.id = (String) data.get("id");
        this.arenas = (int) data.get("arenas");
        this.minPlayers = (int) data.get("minPlayers");
        this.playersPerTeam = (int) data.get("playersPerTeam");

        Map<String, Object> teams = (Map<String, Object>) data.get("teams");
        teams.forEach((color, config) -> this.teams.put(ChatColor.valueOf(color),
                new TeamConfig((Map<String, Object>) config)));
    }

    public TeamConfig getTeam(ChatColor color) {
        return this.teams.computeIfAbsent(color, ignored -> new TeamConfig());
    }

    public List<ConfigLocation> getGenerators(Material material) {
        return this.generators.computeIfAbsent(material, ignored -> new ArrayList<>());
    }

    public boolean isComplete() {
        return this.arenas >= 0 &&
                this.playersPerTeam >= 1 &&
                this.minPlayers >= 1 &&
                !this.teams.isEmpty() &&
                this.teams.values().stream().allMatch(TeamConfig::isComplete) &&
                this.spectatorSpawn != null;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> teams = new HashMap<>();
        this.teams.forEach((color, config) -> teams.put(color.name(), config.serialize()));

        return Map.of(
                "id", id,
                "arenas", arenas,
                "minPlayers", minPlayers,
                "playersPerTeam", playersPerTeam,
                "teams", teams
        );
    }

    public boolean save(File folder) {
        try {
            File file = new File(folder, id + ".yml");
            if (!file.exists() && !file.createNewFile()) return false;

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("config", this);
            config.save(file);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
