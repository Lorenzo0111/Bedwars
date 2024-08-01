package me.lorenzo0111.bedwars.hooks;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.data.PlayerStats;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class BedwarsPlaceholders extends PlaceholderExpansion {
    private final BedwarsPlugin plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "bedwars";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Lorenzo0111";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        String[] realParams = params.split("_");
        if (realParams.length != 2) return null;

        if (realParams[0].equalsIgnoreCase("stats")) {
            PlayerStats stats = plugin.getStatsManager().get(player);

            return switch (realParams[1].toLowerCase()) {
                case "kills" -> String.valueOf(stats.getKills());
                case "deaths" -> String.valueOf(stats.getDeaths());
                case "wins" -> String.valueOf(stats.getWins());
                case "losses" -> String.valueOf(stats.getLosses());
                case "beds" -> String.valueOf(stats.getBeds());
                case "games" -> String.valueOf(stats.getGames());
                default -> null;
            };
        }

        if (realParams[0].equalsIgnoreCase("data")) {
            return switch (realParams[1].toLowerCase()) {
                case "games" -> String.valueOf(plugin.getGameManager().getGames().size());
                case "arenas" -> String.valueOf(plugin.getGameManager().getArenas());
                default -> null;
            };
        }

        return null;
    }
}
