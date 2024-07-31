package me.lorenzo0111.bedwars.api.hologram;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public abstract class HologramHook {
    protected final List<WrappedHologram> holograms = new ArrayList<>();

    public abstract String getId();

    public void unload() {
        new ArrayList<>(holograms).forEach(WrappedHologram::remove);
    }

    public abstract WrappedHologram create(Location location, List<String> lines, List<Material> materialLines);

    public void remove(WrappedHologram hologram) {
        holograms.remove(hologram);
    }
}
