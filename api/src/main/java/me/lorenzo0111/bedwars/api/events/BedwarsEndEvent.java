package me.lorenzo0111.bedwars.api.events;

import lombok.Getter;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@SuppressWarnings("unused")
public class BedwarsEndEvent extends GameEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final List<Player> winners;

    public BedwarsEndEvent(@NotNull AbstractGame game, List<Player> winners) {
        super(game);
        this.winners = winners;
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
