package me.lorenzo0111.bedwars.hooks.hologram.decent;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.hologram.WrappedHologram;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class DecentHologram extends WrappedHologram {
    private Hologram hologram;

    public DecentHologram(HologramHook hook, Location location, List<String> stringLines, List<Material> materialLines) {
        super(hook, location, stringLines, materialLines);
    }

    @Override
    public void spawn() {
        hologram = DHAPI.createHologram(uuid.toString(), location, false);

        for (Material material : materialLines) {
            DHAPI.addHologramLine(hologram, material);
        }

        for (String line : stringLines) {
            DHAPI.addHologramLine(hologram, line);
        }
    }

    @Override
    public void remove() {
        hologram.delete();
        hook.remove(this);
    }

    @Override
    public void update() {
        for (int i = 0; i < stringLines.size(); i++) {
            DHAPI.setHologramLine(hologram, i + materialLines.size(), stringLines.get(i));
        }
    }
}
