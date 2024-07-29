package me.lorenzo0111.bedwars.commands.subcommands;

import me.lorenzo0111.bedwars.commands.SubCommand;
import me.lorenzo0111.bedwars.commands.BedwarsCommand;
import org.bukkit.command.CommandSender;

public class NotFoundCommand extends SubCommand {

    public NotFoundCommand(BedwarsCommand command) {
        super(command);
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        sender.sendMessage(plugin.getPrefixed("not-found"));
    }

    @Override
    public String getName() {
        return "!notfound";
    }

    @Override
    public String getDescription() {
        return null;
    }

}
