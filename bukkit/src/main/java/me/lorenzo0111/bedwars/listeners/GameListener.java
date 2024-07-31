package me.lorenzo0111.bedwars.listeners;

import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.gui.menus.ShopMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

@RequiredArgsConstructor
public class GameListener implements Listener {
    private final BedwarsPlugin plugin;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        AbstractGame game = plugin.getGameManager().getGame(event.getEntity());
        if (game == null) return;

        game.onDeath(event);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        AbstractGame game = plugin.getGameManager().getGame(event.getBlock().getWorld());
        if (game == null) return;

        Material type = event.getBlock().getType();
        if (type.name().contains("_WOOL")) return;

        if (type.name().endsWith("_BED")) {
            game.onBedBreak(event);
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onShop(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager villager)) return;
        if (!villager.hasMetadata("shop")) return;

        AbstractGame game = plugin.getGameManager().getGame(event.getPlayer());
        if (game == null) return;

        event.setCancelled(true);

        List<MetadataValue> data = villager.getMetadata("shop");
        for (MetadataValue value : data) {
            if (value.asString().startsWith("shop")) {
                new ShopMenu(plugin.getShopManager().getShop("shop")).open(event.getPlayer());
                return;
            }

            if (value.asString().startsWith("upgrades-")) {
                String shopTeam = value.asString().replace("upgrades-", "");
                ChatColor playerTeam = game.getTeam(event.getPlayer());

                if (playerTeam == null || !playerTeam.name().equalsIgnoreCase(shopTeam)) return;

                new ShopMenu(plugin.getShopManager().getShop("upgrades")).open(event.getPlayer());
                return;
            }
        }

    }

}
