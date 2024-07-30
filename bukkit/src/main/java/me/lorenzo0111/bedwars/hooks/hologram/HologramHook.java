package me.lorenzo0111.bedwars.hooks.hologram;

import org.bukkit.Location;

import java.util.List;

public abstract class HologramHook {

    public abstract String getId();
    public abstract void unload();

    public abstract WrappedHologram create(Location location, List<String> lines);
}
