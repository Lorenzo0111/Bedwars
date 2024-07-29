package me.lorenzo0111.bedwars.hooks;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class WorldsHook {
    private static BedwarsPlugin plugin;
    private static SlimePlugin hook;
    private static SlimeLoader loader;

    public static void init() {
        plugin = BedwarsPlugin.getInstance();
        hook = (SlimePlugin) Bukkit.getPluginManager()
                .getPlugin("SlimeWorldManager");
        loader = hook.getLoader("mysql");
    }

    public static CompletableFuture<Void> createWorld(UUID uuid, String template) {
        return plugin.getScheduler().runAsync(() -> {
            if (hook.getWorld(uuid.toString()) != null) return;

            File worldDir = new File(plugin.getDataFolder(), template);

            try {
                hook.importWorld(worldDir, uuid.toString(), loader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static CompletableFuture<SlimeWorld> loadWorld(UUID uuid) {
        SlimeWorld loaded = hook.getWorld(uuid.toString());
        if (loaded != null) return CompletableFuture.completedFuture(loaded);

        return plugin.getScheduler().runAsync(() -> {
            try {
                SlimeWorld world = hook.loadWorld(loader, uuid.toString(), false, new SlimePropertyMap());
                return plugin.getScheduler().runSync(() -> {
                    try {
                        hook.loadWorld(world);
                        return world;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        });
    }

    public static boolean unloadWorld(UUID uuid, boolean save) {
        return Bukkit.unloadWorld(uuid.toString(), save);
    }

    public static CompletableFuture<Void> deleteWorld(UUID uuid) {
        plugin.getScheduler().ensureSync(() -> unloadWorld(uuid, false)).join();

        return plugin.getScheduler().runAsync(() -> {
            try {
                loader.deleteWorld(uuid.toString());
            } catch (UnknownWorldException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}
