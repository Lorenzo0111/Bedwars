package me.lorenzo0111.bedwars.hooks.hologram.decent;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.lorenzo0111.bedwars.api.hologram.WrappedHologram;
import org.bukkit.Location;

import java.util.List;

public class DecentHologram extends WrappedHologram {
    private Hologram hologram;

    public DecentHologram(Location location, List<String> lines) {
        super(location, lines);
    }

    @Override
    public void spawn() {
        hologram = DHAPI.createHologram(uuid.toString(), location, false);

        for (String line : lines) {
            DHAPI.addHologramLine(hologram, line);
        }
    }

    @Override
    public void remove() {
        hologram.delete();
    }

    @Override
    public void update() {
        for (int i = 0; i < lines.size(); i++) {
            DHAPI.setHologramLine(hologram, i, lines.get(i));
        }
    }
}
