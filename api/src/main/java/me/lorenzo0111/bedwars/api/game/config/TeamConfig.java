package me.lorenzo0111.bedwars.api.game.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Data
@NoArgsConstructor
public class TeamConfig implements ConfigurationSerializable {
    private ConfigLocation spawn;
    private ConfigLocation bed;
    private ConfigLocation shop;
    private ConfigLocation upgrades;

    @SuppressWarnings("unused")
    public TeamConfig(Map<String, Object> data) {
        this.spawn = (ConfigLocation) data.get("spawn");
        this.bed = (ConfigLocation) data.get("bed");
        this.shop = (ConfigLocation) data.get("shop");
        this.upgrades = (ConfigLocation) data.get("upgrades");
    }

    public boolean isComplete() {
        return this.spawn != null &&
                this.bed != null &&
                this.shop != null &&
                this.upgrades != null;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "spawn", this.spawn,
                "bed", this.bed,
                "shop", this.shop,
                "upgrades", this.upgrades
        );
    }
}
