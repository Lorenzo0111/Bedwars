package me.lorenzo0111.bedwars.api.events;

import lombok.Getter;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@SuppressWarnings("unused")
public class BedwarsKillEvent extends GameEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player killer;
    private final Player killed;

    public BedwarsKillEvent(@NotNull AbstractGame game, Player killer, Player killed) {
        super(game);
        this.killer = killer;
        this.killed = killed;
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
