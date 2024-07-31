package me.lorenzo0111.bedwars.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.items.SpecialItem;
import me.lorenzo0111.bedwars.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
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
        load("shop", config.getConfigurationSection("shop"));
        load("upgrades", config.getConfigurationSection("upgrades"));
    }

    private void load(String shop, @Nullable ConfigurationSection config) {
        if (config == null) return;

        for (String key : config.getKeys(false)) {
            String name = StringUtils.color(config.getString(key + ".name"));
            String material = config.getString(key + ".material");
            String priceData = config.getString(key + ".price", "0I");
            int price = Integer.parseInt(priceData.substring(0, priceData.length() - 1));
            int amount = config.getInt(key + ".amount", 1);

            Material priceType = switch (priceData.charAt(priceData.length() - 1)) {
                case 'G' -> Material.GOLD_INGOT;
                case 'D' -> Material.DIAMOND;
                case 'E' -> Material.EMERALD;
                default -> Material.IRON_INGOT;
            };

            shops.computeIfAbsent(shop, k -> new ArrayList<>())
                    .add(new ShopItem(name, material, amount, price, priceType));
        }
    }

    public List<ShopItem> getShop(String type) {
        return shops.getOrDefault(type, new ArrayList<>());
    }
}
