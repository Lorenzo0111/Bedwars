package me.lorenzo0111.bedwars.hooks.hologram.natives;

import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.hologram.WrappedHologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NativeHologram extends WrappedHologram {
    private final List<ArmorStand> stands = new ArrayList<>();

    public NativeHologram(HologramHook hook, Location location, List<String> stringLines, List<Material> materialLines) {
        super(hook, location, stringLines, materialLines);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void spawn() {
        for (Material material : materialLines) {
            ArmorStand stand = location.getWorld().spawn(location.clone().add(0, -0.25 * stands.size(), 0), ArmorStand.class);
            stand.getEquipment().setHelmet(new ItemStack(material));
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setSmall(true);
            stand.setInvulnerable(true);
            stands.add(stand);
        }

        for (String line : stringLines) {
            ArmorStand stand = location.getWorld().spawn(location.clone().add(0, -0.25 * stands.size(), 0), ArmorStand.class);
            stand.setCustomNameVisible(true);
            stand.setCustomName(line);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setSmall(true);
            stand.setInvulnerable(true);
            stands.add(stand);
        }
    }

    @Override
    public void remove() {
        stands.forEach(ArmorStand::remove);
        hook.remove(this);
    }

    @Override
    public void update() {
        for (int i = 0; i < stringLines.size(); i++) {
            ArmorStand stand = stands.get(i + materialLines.size());
            stand.setCustomName(stringLines.get(i));
        }
    }
}
