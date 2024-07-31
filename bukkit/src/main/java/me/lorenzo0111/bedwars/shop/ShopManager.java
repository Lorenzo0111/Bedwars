package me.lorenzo0111.bedwars.shop;

import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ShopManager {
    private final BedwarsPlugin plugin;
    private final Map<String, List<ShopItem>> shops = new HashMap<>();

    public void reload() {
        File file = new File(plugin.getDataFolder(), "shop.yml");
        if (!file.exists()) plugin.saveResource("shop.yml", false);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    }

}
