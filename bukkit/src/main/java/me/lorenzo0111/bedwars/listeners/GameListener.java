package me.lorenzo0111.bedwars.listeners;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

        // TODO: Handle death messages
        game.onDeath(event);
    }

}
