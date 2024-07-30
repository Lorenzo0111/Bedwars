package me.lorenzo0111.bedwars.hooks.hologram.decent;

import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.hologram.WrappedHologram;
import org.bukkit.Location;

import java.util.List;

public class DecentHologramHook extends HologramHook {

    @Override
    public String getId() {
        return "DecentHolograms";
    }

    @Override
    public WrappedHologram create(Location location, List<String> lines) {
        DecentHologram hologram = new DecentHologram(location, lines);
        holograms.add(hologram);
        hologram.spawn();

        return hologram;
    }

}
