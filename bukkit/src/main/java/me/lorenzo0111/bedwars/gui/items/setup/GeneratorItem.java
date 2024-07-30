package me.lorenzo0111.bedwars.gui.items.setup;

import me.lorenzo0111.bedwars.api.game.config.ConfigLocation;
import me.lorenzo0111.bedwars.game.setup.SetupSession;
import me.lorenzo0111.bedwars.gui.items.ConfiguredItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class GeneratorItem extends ConfiguredItem {
    private final SetupSession session;
    private final Material material;

    public GeneratorItem(SetupSession session, Material material) {
        super("setup.generator");
        this.session = session;
        this.material = material;
    }

    @Override
    public ItemBuilder overrideBase() {
        return new ItemBuilder(material);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        session.config().getGenerators(material).add(new ConfigLocation(player.getLocation()));
        player.sendMessage(plugin.getPrefixed("setup.generator-added"));
    }

    @Override
    public String replacePlaceholders(String origin) {
        return origin.replace("%material%", material.name());
    }
}
