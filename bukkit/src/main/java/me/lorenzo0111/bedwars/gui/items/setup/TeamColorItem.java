package me.lorenzo0111.bedwars.gui.items.setup;

import me.lorenzo0111.bedwars.game.setup.SetupSession;
import me.lorenzo0111.bedwars.gui.items.ConfiguredItem;
import me.lorenzo0111.bedwars.gui.menus.setup.TeamSetupMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class TeamColorItem extends ConfiguredItem {
    private final SetupSession session;
    private final ChatColor color;

    public TeamColorItem(SetupSession session, ChatColor color) {
        super("setup.team-color");
        this.session = session;
        this.color = color;
    }

    @Override
    public ItemBuilder overrideBase() {
        try {
            return new ItemBuilder(Material.valueOf(color.name() + "_WOOL"));
        } catch (IllegalArgumentException e) {
            return new ItemBuilder(Material.WHITE_WOOL);
        }
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.closeInventory();
        new TeamSetupMenu(session, color).open(player);
    }

    @Override
    public String replacePlaceholders(String origin) {
        return origin.replace("%color%", color.name())
                .replace("%color_display%", color.toString());
    }
}
