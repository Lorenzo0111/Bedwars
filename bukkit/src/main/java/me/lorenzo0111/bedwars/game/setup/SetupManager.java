package me.lorenzo0111.bedwars.game.setup;

import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;
import me.lorenzo0111.bedwars.hooks.WorldsHook;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SetupManager {
    private final BedwarsPlugin plugin;
    private final Map<Player, SetupSession> sessions = new HashMap<>();

    public void startSetup(Player player, String id) {
        WorldsHook.createWorld(player.getUniqueId(), id)
                .thenAccept(success -> {
                    if (!success) {
                        player.sendMessage(plugin.getPrefixed("world-not-found"));
                        return;
                    }

                    WorldsHook.loadWorld(player.getUniqueId())
                            .thenAccept(world -> {
                                World bukkitWorld = Bukkit.getWorld(world.getName());

                                sessions.put(player, new SetupSession(
                                        bukkitWorld,
                                        new GameConfiguration(id)
                                ));

                                player.teleport(bukkitWorld.getSpawnLocation());
                                player.sendMessage(plugin.getPrefixed("setup-started"));
                            });
                });
    }

    public boolean inSetup(Player player) {
        return sessions.containsKey(player);
    }

    public SetupSession getSession(Player player) {
        return sessions.get(player);
    }

    public void endSetup(Player player) {
        if (inSetup(player)) getSession(player).save();

        sessions.remove(player);
    }
}
