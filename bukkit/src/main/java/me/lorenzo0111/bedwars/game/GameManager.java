package me.lorenzo0111.bedwars.game;

import lombok.Getter;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.api.game.AbstractGameManager;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameManager implements AbstractGameManager {
    private final BedwarsPlugin plugin;
    private final File folder;
    @Getter
    private final List<AbstractGame> games = new ArrayList<>();

    public GameManager(@NotNull BedwarsPlugin plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder(), "games");
    }

    @Override
    public void reload() {
        this.stop();

        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }

        for (File file : Objects.requireNonNullElse(
                this.folder.listFiles(file -> file.getName().endsWith(".yml")), new File[0])) {
            if (!file.exists()) {
                continue;
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            GameConfiguration gameConfig = (GameConfiguration) config.get("config");

            if (gameConfig == null) continue;

            for (int i = 0; i < gameConfig.getArenas(); i++) {
                AbstractGame game = new Game(gameConfig);
                this.games.add(game);
            }
        }
    }

    @Override
    public void stop() {
        this.games.forEach(AbstractGame::stop);
        this.games.clear();
    }

    @Override
    public @Nullable AbstractGame getGame(UUID id) {
        return this.games.stream()
                .filter(game -> game.getUuid().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @Nullable AbstractGame findGame() {
        return this.games.stream()
                .filter(game -> game.getState().canJoin())
                .filter(game -> game.getPlayers().size() < game.getConfig().getPlayersPerTeam() * game.getConfig().getTeams().size())
                .findFirst()
                .orElse(null);
    }


}
