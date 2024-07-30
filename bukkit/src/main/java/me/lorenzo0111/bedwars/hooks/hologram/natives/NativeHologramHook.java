package me.lorenzo0111.bedwars.hooks.hologram.natives;

import me.lorenzo0111.bedwars.hooks.hologram.HologramHook;
import me.lorenzo0111.bedwars.hooks.hologram.WrappedHologram;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeHologramHook extends HologramHook {
    private Map<Location, NativeHologram> holograms = new HashMap<>();

    @Override
    public String getId() {
        return "native";
    }

    @Override
    public void unload() {
        holograms.values().forEach(NativeHologram::remove);
        holograms.clear();
    }

    @Override
    public WrappedHologram create(Location location, List<String> lines) {
        NativeHologram hologram = new NativeHologram(location, lines);
        holograms.put(location, hologram);
        hologram.spawn();

        return hologram;
    }

}
