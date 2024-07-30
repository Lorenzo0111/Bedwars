package me.lorenzo0111.bedwars.hooks;

import lombok.Getter;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.hooks.hologram.HologramHook;
import me.lorenzo0111.bedwars.hooks.hologram.natives.NativeHologramHook;

public class HologramHookWrapper {
    @Getter
    private static HologramHook hook;

    public static void init() {
        unload();

        hook = new NativeHologramHook();

        BedwarsPlugin.getInstance().log("Using hologram hook: &c" + hook.getId());
    }

    public static void unload() {
        if (hook != null) hook.unload();
    }

}