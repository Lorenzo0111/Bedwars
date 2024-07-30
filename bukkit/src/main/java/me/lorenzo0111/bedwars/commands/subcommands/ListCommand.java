package me.lorenzo0111.bedwars.commands.subcommands;

import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.commands.BedwarsCommand;
import me.lorenzo0111.bedwars.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static me.lorenzo0111.bedwars.utils.StringUtils.color;

public class ListCommand extends SubCommand {

    public ListCommand(@NotNull BedwarsCommand command) {
        super(command);
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        String prefix = plugin.getMessage("prefix");
        sender.sendMessage(color(prefix + "&c&m-------------------------------"));

        for (AbstractGame game : plugin.getGameManager().getGames()) {
            sender.sendMessage(color(String.format(
                    prefix + "&7- &e%s &7(%s) &7- &e%d/%d",
                    game.getUuid(),
                    game.getState().name(),
                    game.getPlayers().size(),
                    game.getMaxPlayers()
            )));
        }

        sender.sendMessage(color(prefix + "&c&m-------------------------------"));
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "List all current games";
    }

}
