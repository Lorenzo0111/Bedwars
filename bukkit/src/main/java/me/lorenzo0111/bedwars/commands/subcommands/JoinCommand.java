package me.lorenzo0111.bedwars.commands.subcommands;

import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.commands.BedwarsCommand;
import me.lorenzo0111.bedwars.commands.SubCommand;
import me.lorenzo0111.bedwars.commands.exceptions.OnlyPlayersException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JoinCommand extends SubCommand {

    public JoinCommand(@NotNull BedwarsCommand command) {
        super(command);
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) throw new OnlyPlayersException();

        AbstractGame game = null;

        if (args.length > 0) {
            try {
                game = plugin.getGameManager().getGame(UUID.fromString(args[0]));
            } catch (IllegalArgumentException ignored) {}
        }

        if (game == null) {
            game = plugin.getGameManager().findGame();
        }

        if (game == null) {
            sender.sendMessage(plugin.getPrefixed("game-not-found"));
            return;
        }

        game.join(player);
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join a game";
    }
}
