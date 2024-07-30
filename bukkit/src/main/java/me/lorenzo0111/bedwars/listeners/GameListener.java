package me.lorenzo0111.bedwars.listeners;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameListener implements Listener {
    private final BedwarsPlugin plugin;

    public GameListener(BedwarsPlugin plugin) {
        this.plugin = plugin;
    }

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

}
