package me.lorenzo0111.bedwars.hooks.hologram;

import lombok.Getter;
import lombok.Setter;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.hooks.hologram.decent.DecentHologramHook;
import me.lorenzo0111.bedwars.hooks.hologram.natives.NativeHologramHook;
import org.bukkit.Bukkit;

public class HologramHookWrapper {
    @Getter @Setter
    private static HologramHook hook;

    public static void init() {
        unload();

        if (Bukkit.getPluginManager().isPluginEnabled("DecentHolograms"))
            hook = new DecentHologramHook();
        else hook = new NativeHologramHook();

        BedwarsPlugin.getInstance().log("Using hologram hook: &c" + hook.getId());
    }

    public static void unload() {
        if (hook != null) hook.unload();
    }

}