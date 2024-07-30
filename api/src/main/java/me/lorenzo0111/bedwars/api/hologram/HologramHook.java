package me.lorenzo0111.bedwars.api.hologram;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class HologramHook {
    protected final List<WrappedHologram> holograms = new ArrayList<>();

    public abstract String getId();

    public void unload() {
        holograms.forEach(WrappedHologram::remove);
        holograms.clear();
    }

    public abstract WrappedHologram create(Location location, List<String> lines);
}
