package me.lorenzo0111.bedwars.api.game.config;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Data
public class ConfigLocation implements ConfigurationSerializable {
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public ConfigLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    @SuppressWarnings("unused")
    public ConfigLocation(@NotNull Map<String, Object> data) {
        this.x = (double) data.get("x");
        this.y = (double) data.get("y");
        this.z = (double) data.get("z");
        this.yaw = (float) data.get("yaw");
        this.pitch = (float) data.get("pitch");
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "x", this.x,
                "y", this.y,
                "z", this.z,
                "yaw", this.yaw,
                "pitch", this.pitch
        );
    }
}
