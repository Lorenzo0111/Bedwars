package me.lorenzo0111.bedwars.hooks.hologram.natives;

import me.lorenzo0111.bedwars.hooks.hologram.WrappedHologram;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public class NativeHologram extends WrappedHologram {
    private final List<ArmorStand> stands = new ArrayList<>();

    public NativeHologram(Location location, List<String> lines) {
        super(location, lines);
    }

    @Override
    public void spawn() {
        for (String line : lines) {
            ArmorStand stand = location.getWorld().spawn(location.clone().add(0, -0.25 * stands.size(), 0), ArmorStand.class);
            stand.setCustomNameVisible(true);
            stand.setCustomName(line);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setSmall(true);
            stands.add(stand);
        }
    }

    @Override
    public void remove() {
        stands.forEach(ArmorStand::remove);
    }

    @Override
    public void update() {
        for (int i = 0; i < lines.size(); i++) {
            ArmorStand stand = stands.get(i);
            stand.setCustomName(lines.get(i));
        }
    }
}
