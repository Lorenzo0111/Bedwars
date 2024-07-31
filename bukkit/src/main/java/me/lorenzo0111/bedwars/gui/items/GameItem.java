package me.lorenzo0111.bedwars.gui.items;

import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class GameItem extends ConfiguredItem {
    private final AbstractGame game;

    public GameItem(AbstractGame game) {
        super("game");
        this.game = game;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if (game.canJoin())
            game.join(player);
        else
            game.spectate(player);
    }

    @Override
    public String replacePlaceholders(String origin) {
        return origin.replace("%players%", String.valueOf(game.getPlayers().size()))
                .replace("%max_players%", String.valueOf(game.getMaxPlayers()))
                .replace("%id", game.getUuid().toString())
                .replace("%status%", game.getState().name());
    }
}
