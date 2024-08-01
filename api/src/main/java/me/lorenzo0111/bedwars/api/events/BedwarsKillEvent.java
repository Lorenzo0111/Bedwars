package me.lorenzo0111.bedwars.api.events;

import lombok.Getter;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@SuppressWarnings("unused")
public class BedwarsKillEvent extends GameEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    @Nullable private final Player killer;
    @NotNull private final Player killed;

    public BedwarsKillEvent(@NotNull AbstractGame game, @Nullable Player killer, @NotNull Player killed) {
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
