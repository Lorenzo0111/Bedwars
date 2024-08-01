package me.lorenzo0111.bedwars.api.events;

import lombok.Getter;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@SuppressWarnings("unused")
public class BedwarsBedDestroyEvent extends GameEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final ChatColor team;

    public BedwarsBedDestroyEvent(@NotNull AbstractGame game, Player player, ChatColor team) {
        super(game);
        this.player = player;
        this.team = team;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
