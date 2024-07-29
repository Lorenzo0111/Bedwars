package me.lorenzo0111.bedwars.game.setup;

import lombok.Data;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import org.bukkit.World;

public record SetupSession(World world, GameConfiguration config) {
}
