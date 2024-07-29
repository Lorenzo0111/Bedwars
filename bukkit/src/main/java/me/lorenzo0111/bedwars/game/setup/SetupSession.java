package me.lorenzo0111.bedwars.game.setup;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import org.bukkit.World;

import java.io.File;
import java.util.UUID;

public record SetupSession(World world, GameConfiguration config) {

    public void save() {
        config.save(new File(BedwarsPlugin.getInstance().getDataFolder(), "games"));
        WorldsHook.deleteWorld(UUID.fromString(world.getName()));
    }
}
